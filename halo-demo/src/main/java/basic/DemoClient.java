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
        HaloRpcClient haloRpcClient = HaloClientConfig.builder()
                .serverAddress(new InetSocketAddress("localhost",18301))
                .codec(CodecTypeEnum.HESSIAN)
                .start();
        UserService reference = haloRpcClient.getReference(UserService.class);
        reference.getId();
    }
}
