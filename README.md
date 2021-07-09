# Halo-RPC
 > 一个学习性质的基于Netty的Java RPC框架的实现

预计完成的功能如下
+ 支持三种序列化算法: json,protostuff,hessian ✅
+ 支持同步调用和异步调用两种方式 ✅
+ 支持与Spring做集成 (进行中)
+ 支持注册到注册中心 
+ 实现常见的负载均衡策略，如 轮询，权重轮询，一致性哈希
+ 实现心跳机制

# 开始使用
## 基本使用
### 服务端
```java
interface UserService{
    String getId();
}
class UserServiceImpl implements UserService{
    //...
}
class DemoServer{
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        HaloServerConfig.builder()
                .port(18082)//服务端启动端口
                .ioThreadNum(16)//NettyIO线程的数量
                .workerThreadNum(8)//工作线程的数量
                .codec(CodecTypeEnum.HESSIAN)//使用Hessian作为序列化方式
                .export(UserService.class,userService) //暴露服务
                .startServer();//启动服务
    }
}
```
### 客户端
```java
interface UserService{
    String getId();
}
class DemoClient{
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
```
## 异步调用
异步调用需要在`HaloRpcClient`中`getReference` 设置async参数为`true`这个时候调用会立即返回`null`但是可以通过`RpcContextHolder`获取`HaloRpcFuture`对象。
```java
/**
 * 异步客户端代码
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
```
# 扩展模块
## 集成Spring

## 集成注册中心

### 使用Zookeeper作为注册中心

### 使用Nacos作为注册中心

### 使用Euruka作为注册中心

# 模块设计
## 通信协议
```text
+---------------------------------------------------------------+
| 魔数 2byte | 协议版本号 1byte | 序列化算法 1byte | 报文类型 1byte  |
+---------------------------------------------------------------+
| 状态 1byte |        消息 ID 8byte     |      数据长度 4byte     |
+---------------------------------------------------------------+
|                   数据内容 （长度不定）                          |
+---------------------------------------------------------------+
```



