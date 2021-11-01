package flink.flatMap.keyedState;

import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.state.AggregatingState;
import org.apache.flink.api.common.state.AggregatingStateDescriptor;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.util.Collector;

/**
 * @author Kled
 * @date 2021/10/27 5:54 下午
 */
public class MyAggregatingStateFlatMapFunction extends RichFlatMapFunction<Tuple2<Long, Long>, Tuple2<Long, String>> {

    private AggregatingState<Long, String> aggregatingState;

    @Override
    public void open(Configuration parameters) throws Exception {
        //初始化
        super.open(parameters);

        AggregatingStateDescriptor<Long, String, String> aggregatingStateDescriptor = new AggregatingStateDescriptor<Long, String, String>(
                "123",
                new AggregateFunction() {
                    @Override
                    public Object createAccumulator() {
                        return "";
                    }

                    @Override
                    public Object add(Object o, Object o2) {
                        return o + " and " + o2;
                    }

                    @Override
                    public Object getResult(Object o) {
                        return o;
                    }

                    @Override
                    public Object merge(Object o, Object acc1) {
                        return o + " and " + acc1;
                    }
                },
                Types.STRING
        );
        aggregatingState = getRuntimeContext().getAggregatingState(aggregatingStateDescriptor);
    }

    @Override
    public void flatMap(Tuple2<Long, Long> element, Collector<Tuple2<Long, String>> collector) throws Exception {
        aggregatingState.add(element.f1);

        //每次计算累加值
        collector.collect(Tuple2.of(element.f0, aggregatingState.get()));
    }
}
