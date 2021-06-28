package basic;

import com.github.halo.client.HaloRpcClient;
import com.github.halo.config.HaloClientConfig;
import common.UserService;

/**
 * @author mason.lu 2021/6/27
 */
public class DemoClient {
    public static void main(String[] args) {
        HaloRpcClient haloRpcClient = HaloClientConfig.builder()
                .serverAddress("localhost:18001")
                .start();
        UserService reference = haloRpcClient.getReference(UserService.class);
        reference.getId();
    }
}
