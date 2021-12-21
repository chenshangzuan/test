package flink;

import akka.actor.ActorSystem;
import org.apache.flink.runtime.akka.AkkaUtils;
import org.apache.flink.runtime.rpc.RpcEndpoint;
import org.apache.flink.runtime.rpc.RpcGateway;
import org.apache.flink.runtime.rpc.RpcService;
import org.apache.flink.runtime.rpc.akka.AkkaRpcService;
import org.apache.flink.runtime.rpc.akka.AkkaRpcServiceConfiguration;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author Kled
 * @date 2021/11/10 10:58 上午
 */
public class FlinkRPCTest {
    //ClassNotFoundException: org.apache.flink.shaded.netty4.io.netty.handler.ssl.OpenSslX509KeyManagerFactory
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //ActorSystem
        ActorSystem defaultActorSystem = AkkaUtils.createDefaultActorSystem();
        AkkaRpcService akkaRpcService = new AkkaRpcService(defaultActorSystem, AkkaRpcServiceConfiguration.defaultConfiguration());

        //Actor
        GetNowRpcEndpoint getNowRpcEndpoint = new GetNowRpcEndpoint(akkaRpcService);
        getNowRpcEndpoint.start();

        //ActorRef
        GetNowGateway selfGateway = getNowRpcEndpoint.getSelfGateway(GetNowGateway.class);
        System.out.println(selfGateway.getNow());

        //ActorRef
        GetNowGateway getNowGateway = akkaRpcService.connect(getNowRpcEndpoint.getAddress(), GetNowGateway.class).get();
        System.out.println(getNowGateway.getNow());
    }

    static class GetNowRpcEndpoint extends RpcEndpoint implements GetNowGateway{

        protected GetNowRpcEndpoint(RpcService rpcService) {
            super(rpcService);
        }

        @Override
        public String getNow() {
            return Instant.now().toString();
        }

        @Override
        protected void onStart() throws Exception {
            super.onStart();
            System.out.println("start");
        }

        @Override
        protected CompletableFuture<Void> onStop() {
            System.out.println("end");
            return super.onStop();
        }
    }

    //服务接口协议
    interface GetNowGateway extends RpcGateway {
        String getNow();
    }
}
