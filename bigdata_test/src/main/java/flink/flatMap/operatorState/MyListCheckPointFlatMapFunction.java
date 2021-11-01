package flink.flatMap.operatorState;

import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.checkpoint.ListCheckpointed;
import org.apache.flink.util.Collector;

import java.util.Collections;
import java.util.List;

/**
 * @author Kled
 * @date 2021/10/29 2:30 下午
 */
public class MyListCheckPointFlatMapFunction extends RichFlatMapFunction<Tuple2<Long, Long>, Tuple2<Long, Long>> implements ListCheckpointed<Long> {

    private long numberRecords;

    @Override
    public void flatMap(Tuple2<Long, Long> longLongTuple2, Collector<Tuple2<Long, Long>> collector) throws Exception {
        numberRecords ++;
        collector.collect(longLongTuple2);
    }

    @Override
    public List<Long> snapshotState(long l, long l1) throws Exception {
        //从snapshot中将numberRecords写入状态
        return Collections.singletonList(numberRecords);
    }

    @Override
    public void restoreState(List<Long> list) throws Exception {
        for (Long l : list){
            //从状态中恢复
            numberRecords += l;
        }
    }
}
