package basic;

import com.github.halo.client.HaloRpcClient;
import com.github.halo.codec.CodecTypeEnum;
import com.github.halo.common.RpcCallback;
import com.github.halo.common.RpcContext;
import com.github.halo.config.HaloClientConfig;
import common.UserService;

import java.net.InetSocketAddress;

/**
 * @author mason.lu 2021/7/9
 */
public class DemoAsyncClient {
    public static void main(String[] args) {
        //创建RPC客户端
        HaloRpcClient haloRpcClient = HaloClientConfig.builder()
                .serverAddress(new InetSocketAddress("localhost",18082))
                .codec(CodecTypeEnum.HESSIAN)
                .connectTimeout(5000L)
                .start();
        //创建代理对象
        UserService reference = haloRpcClient.getReference(UserService.class,true);
        //执行RPC方法
        String id = reference.getId();
        //这时返回值为null
        //从上下问中拿到CallBack
        RpcContext.getFuture().addCallBack(new RpcCallback<String>() {
            public void handleSuccess(String result) {
                System.out.println("异步接收到结果: "+result);
            }
            public void handleFailure(Throwable cause) {
                cause.printStackTrace();
            }
        });

    }
}
