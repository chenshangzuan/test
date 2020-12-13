package kled.test.config;

import api.HelloWorldGrpc;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import net.devh.boot.grpc.client.channelfactory.GrpcChannelFactory;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Kled
 * @version: GRpcConfig.java, v0.1 2020-12-12 18:47 Kled
 */
@Configuration
public class GRpcConfig {

    //方式一:
    @GrpcClient("local-grpc-server")
    private Channel channel;


    @Autowired
    private GrpcChannelFactory grpcChannelFactory;

//    方式二：Channel工厂模式
//    @Bean
//    public HelloWorldGrpc.HelloWorldBlockingStub helloWorldBlockingStub() {
//        Channel channel = grpcChannelFactory.createChannel("local-grpc-server");
//        return HelloWorldGrpc.newBlockingStub(channel);
//    }

//    方式三: 手动指定host + port, 创建channel
//    @Bean
//    public HelloWorldGrpc.HelloWorldBlockingStub helloWorldBlockingStub() {
//        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 9092).usePlaintext().build();
//        return HelloWorldGrpc.newBlockingStub(channel);
//    }

    @Bean
    public HelloWorldGrpc.HelloWorldBlockingStub helloWorldBlockingStub() {
        return HelloWorldGrpc.newBlockingStub(channel);
    }

    @Bean
    public HelloWorldGrpc.HelloWorldFutureStub helloWorldFutureStub(){
        return HelloWorldGrpc.newFutureStub(channel);
    }

    @Bean
    public HelloWorldGrpc.HelloWorldStub helloWorldStub(){
        return HelloWorldGrpc.newStub(channel);
    }
}
