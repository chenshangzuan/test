package flink.flatMap.keyedState;

import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.state.StateTtlConfig;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.util.Collector;

/**
 * @author Kled
 * @date 2021/10/27 5:54 下午
 */
public class MyValueStateFlatMapFunction extends RichFlatMapFunction<Tuple2<Long, Long>, Tuple2<Long, Double>> {

    //记录每个key的出现次数及累加值
    private ValueState<Tuple2<Long, Long>> valueState;

    @Override
    public void open(Configuration parameters) throws Exception {
        //初始化
        super.open(parameters);

        //配置状态的生命周期
        StateTtlConfig stateTtlConfig = StateTtlConfig.newBuilder(Time.seconds(10))
                .setUpdateType(StateTtlConfig.UpdateType.OnCreateAndWrite)
                .setStateVisibility(StateTtlConfig.StateVisibility.NeverReturnExpired)
                .build();
        ValueStateDescriptor<Tuple2<Long, Long>> valueStateDescriptor = new ValueStateDescriptor<Tuple2<Long, Long>>(
                "valueState1",
                Types.TUPLE(Types.LONG, Types.LONG)
        );
        valueStateDescriptor.enableTimeToLive(stateTtlConfig);
        valueStateDescriptor.setQueryable("valueState1"); //打开可查询功能

        valueState = getRuntimeContext().getState(valueStateDescriptor);
    }

    @Override
    public void flatMap(Tuple2<Long, Long> element, Collector<Tuple2<Long, Double>> collector) throws Exception {
        Tuple2<Long, Long> currentValue = valueState.value();
        if (currentValue == null) {
            currentValue = Tuple2.of(0L, 0L);
        }
        currentValue.f0 += 1;
        currentValue.f1 += element.f1;
        valueState.update(currentValue);

        if (currentValue.f0 == 3) {
            collector.collect(Tuple2.of(element.f0, (double) currentValue.f1 / 3));
            //情况当前key的valueState
            valueState.clear();
        }
    }
}
