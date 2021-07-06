package basic;

import com.github.halo.client.HaloRpcClient;
import com.github.halo.codec.CodecTypeEnum;
import com.github.halo.config.HaloClientConfig;
import common.UserService;

import java.net.InetSocketAddress;

/**
 * @author mason.lu 2021/6/27
 */
public class DemoClient {
    public static void main(String[] args) {
        //创建RPC客户端
        HaloRpcClient haloRpcClient = HaloClientConfig.builder()
                .serverAddress(new InetSocketAddress("localhost",18082))
                .codec(CodecTypeEnum.HESSIAN)
                .connectTimeout(5000L)
                .start();
        //创建代理对象
        UserService reference = haloRpcClient.getReference(UserService.class);
        //执行RPC方法
        System.out.println(reference.getId());
    }
}
