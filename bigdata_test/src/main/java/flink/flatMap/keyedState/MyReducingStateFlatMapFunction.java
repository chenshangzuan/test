package flink.flatMap.keyedState;

import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.state.ReducingState;
import org.apache.flink.api.common.state.ReducingStateDescriptor;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.util.Collector;

/**
 * @author Kled
 * @date 2021/10/27 5:54 下午
 */
public class MyReducingStateFlatMapFunction extends RichFlatMapFunction<Tuple2<Long, Long>, Tuple2<Long, Double>> {

    //记录每个key的出现次数及累加值
    private ReducingState<Long> reduceState;

    @Override
    public void open(Configuration parameters) throws Exception {
        //初始化
        super.open(parameters);

        ReducingStateDescriptor<Long> reducingStateDescriptor = new ReducingStateDescriptor<Long>(
                "123",
                Long::sum,
                Types.LONG
        );
        reduceState = getRuntimeContext().getReducingState(reducingStateDescriptor);
    }

    @Override
    public void flatMap(Tuple2<Long, Long> element, Collector<Tuple2<Long, Double>> collector) throws Exception {
        reduceState.add(element.f1);

        //每次计算累加值
        collector.collect(Tuple2.of(element.f0, reduceState.get().doubleValue()));
    }
}
