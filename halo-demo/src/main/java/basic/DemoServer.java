package basic;

import com.github.halo.codec.CodecTypeEnum;
import com.github.halo.config.HaloServerConfig;
import common.UserService;
import common.UserServiceImpl;

/**
 * @author mason.lu 2021/6/27
 */
public class DemoServer {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        HaloServerConfig.builder()
                .port(18082)
                .ioThreadNum(8)
                .workerThreadNum(16)
                .codec(CodecTypeEnum.HESSIAN)
                .export(UserService.class,userService)
                .startServer();
    }
}
