package flink;

import org.apache.flink.api.common.JobID;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.queryablestate.client.QueryableStateClient;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.CompletableFuture;

/**
 * @author Kled
 * @date 2021/10/29 4:14 下午
 */
public class QueryStateClient {
    public static void main(String[] args) throws UnknownHostException {
//        QueryableStateClient queryableStateClient = new QueryableStateClient("172.16.5.56", 9067);
//        CompletableFuture<ValueState<String>> kvState = queryableStateClient.getKvState(JobID.generate(),
//                "queryableStateName",
//                "key",
//                Types.STRING,
//                new ValueStateDescriptor<String>("123", Types.STRING));
//        kvState.thenAccept(response -> {
//            try {
//                System.out.println(response.value());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });

    }
}
