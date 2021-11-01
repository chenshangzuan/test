package flink.flatMap.operatorState;

import com.google.common.collect.Lists;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.runtime.state.FunctionInitializationContext;
import org.apache.flink.runtime.state.FunctionSnapshotContext;
import org.apache.flink.streaming.api.checkpoint.CheckpointedFunction;
import org.apache.flink.util.Collector;

/**
 * @author Kled
 * @date 2021/10/29 2:04 下午
 */
public class MyCheckPointFlatMapFunction extends RichFlatMapFunction<Tuple2<Long, Long>, Tuple2<Long, Long>> implements CheckpointedFunction {

    private static ListState<Long> operatorState;

    private static ValueState<Long> keyedState;

    private long operatorCount;

    @Override
    public void flatMap(Tuple2<Long, Long> longLongTuple2, Collector<Tuple2<Long, Long>> collector) throws Exception {
        Long value = keyedState.value();
        if (value == null) {
            value = 0L;
        }
        value = value + 1;
        keyedState.update(value);
        operatorCount ++;
        collector.collect(longLongTuple2);
    }

    @Override
    public void snapshotState(FunctionSnapshotContext functionSnapshotContext) throws Exception {
        operatorState.clear();
        operatorState.add(operatorCount);
    }

    @Override
    public void initializeState(FunctionInitializationContext functionInitializationContext) throws Exception {
        keyedState = functionInitializationContext.getKeyedStateStore().getState(new ValueStateDescriptor<Long>(
                "key",
                Types.LONG
        ));

        operatorState = functionInitializationContext.getOperatorStateStore().getListState(new ListStateDescriptor<Long>(
                "operator",
                Types.LONG
        ));

        if (functionInitializationContext.isRestored()){
            operatorCount = Lists.newArrayList(operatorState.get().iterator()).stream().mapToInt(Long::intValue).sum();
        }
    }
}
