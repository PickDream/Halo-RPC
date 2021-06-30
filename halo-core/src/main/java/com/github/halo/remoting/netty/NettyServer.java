package com.github.halo.remoting.netty;

import com.github.halo.codec.HaloCodecAdapter;
import com.github.halo.common.constant.ProtocolConstant;
import com.github.halo.config.HaloServerConfig;
import com.github.halo.remoting.netty.handler.HaloRpcServerHandler;
import com.github.halo.utils.NetUtils;
import com.github.halo.utils.NettyEventLoopFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @author mason.lu 2021/6/19
 */
public class NettyServer  {

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    private HaloServerConfig config;

    private String serverAddress;

    private final AtomicBoolean isRunning = new AtomicBoolean(false);

    private final ThreadPoolExecutor executor;


    public NettyServer(HaloServerConfig config){
        this.config = config;
        int workerThreadNum = config.getWorkerThreadNum();
        executor = new ThreadPoolExecutor(workerThreadNum,workerThreadNum , 60,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(10000));
    }


    public void startServer() throws Throwable {
        if (!isRunning.compareAndSet(false,true)){
            System.err.println("server is already running!");
        }
        this.serverAddress = NetUtils.getLocalAddress();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        bossGroup = NettyEventLoopFactory.eventLoopGroup(1,"NettyServerBoss");
        workerGroup = NettyEventLoopFactory.eventLoopGroup(config.getIoThreadNum(),"NettyServerWorker");
        try {
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NettyEventLoopFactory.serverSocketChannelClass())
                    //处于半连接的队列长度设置
                    .option(ChannelOption.SO_BACKLOG,1024)
                    .option(ChannelOption.SO_REUSEADDR,true)
                    .childOption(ChannelOption.TCP_NODELAY,true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            HaloCodecAdapter adapter = new HaloCodecAdapter(config.getCodecTypeEnum().getType());
                            pipeline.addLast(ProtocolConstant.DECODER_NAME,adapter.getDecodeHandler())
                                    .addLast(ProtocolConstant.ENCODER_NAME,adapter.getEncodeHandler())
                                    .addLast(ProtocolConstant.SERVER_HANDLER_NAME,new HaloRpcServerHandler(config.getServiceMap(),executor));

                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(config.getPort()).sync();
            System.out.println(String.format("server addr %s started on port %d",serverAddress, config.getPort()));
            channelFuture.syncUninterruptibly();
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            isRunning.set(false);
        }
    }

}
