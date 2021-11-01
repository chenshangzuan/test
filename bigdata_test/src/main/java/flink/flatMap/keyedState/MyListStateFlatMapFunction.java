package flink.flatMap.keyedState;

import com.google.common.collect.Lists;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.util.Collector;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Kled
 * @date 2021/10/27 5:54 下午
 */
public class MyListStateFlatMapFunction extends RichFlatMapFunction<Tuple2<Long, Long>, Tuple2<Long, Double>> {

    //记录每个key的出现次数及累加值
    private ListState<Long> listState;

    @Override
    public void open(Configuration parameters) throws Exception {
        //初始化
        super.open(parameters);

        ListStateDescriptor<Long> listStateDescriptor = new ListStateDescriptor<Long>(
                "123",
                Types.LONG
        );
        listState = getRuntimeContext().getListState(listStateDescriptor);
    }

    @Override
    public void flatMap(Tuple2<Long, Long> element, Collector<Tuple2<Long, Double>> collector) throws Exception {
        Iterable<Long> currentValue = listState.get();
        if (currentValue == null) {
            listState.addAll(Collections.emptyList());
        }
        listState.add(element.f1);

        ArrayList<Long> tuple2s = Lists.newArrayList(listState.get());
        if (tuple2s.size() == 3) {
            double sum = tuple2s.stream().mapToDouble(Long::doubleValue).sum();
            collector.collect(Tuple2.of(element.f0, (double) sum / 3));
            //情况当前key的listState
            listState.clear();
        }
    }
}
