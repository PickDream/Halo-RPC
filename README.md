# Halo-RPC
 > 一个学习性质的基于Netty的Java RPC框架的实现

完成的功能如下
+ 支持三种序列化算法: json,protostuff,hessian
+ 支持同步调用和异步调用两种方式
+ 支持与Spring做集成
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
        HaloRpcServer rpcServer = 
                HaloServerConfig.builder().export(UserService.class,userService)
                .bind(18001).start();
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
        HaloRpcClient rpcClient = HaloClientConfig.builder()
                .servers("localhost:18001")
                .start();
        UserService ref = rpcClient.getReference(UserService.class);
        String s = ref.getId();
        System.out.println(s);
    }
}
```
## 异步调用
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



