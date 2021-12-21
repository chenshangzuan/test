package flink.local;

import com.google.common.collect.Lists;
import org.apache.flink.cep.*;
import org.apache.flink.cep.functions.PatternProcessFunction;
import org.apache.flink.cep.nfa.aftermatch.AfterMatchSkipStrategy;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.cep.pattern.Quantifier;
import org.apache.flink.cep.pattern.conditions.SimpleCondition;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;

import java.util.List;
import java.util.Map;

/**
 * @author Kled
 * @date 2021/11/18 10:32 上午
 */
public class FlinkCEPTest {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment streamExecutionEnvironment = StreamExecutionEnvironment.createLocalEnvironment();
        DataStreamSource<Event> eventDataStreamSource = streamExecutionEnvironment.fromCollection(Lists.newArrayList(new Event("fail", 1), new Event("fail", 2), new Event("success", 2), new Event("fail", 2)));

        Pattern<Event, Event> start = Pattern.<Event>begin("success", AfterMatchSkipStrategy.skipPastLastEvent());
        Pattern<Event, Event> pattern = start.where(new SimpleCondition<Event>() {
            @Override
            public boolean filter(Event event) throws Exception {
                return "success".equals(event.type);
            }
        })
                .next("fail").where(new SimpleCondition<Event>() {
                    @Override
                    public boolean filter(Event event) throws Exception {
                        return "fail".equals(event.type);
                    }
                }).within(Time.seconds(10));

        PatternStream<Event> eventPatternStream = CEP.pattern(eventDataStreamSource, pattern, new EventComparator<Event>() {
            @Override
            public int compare(Event o1, Event o2) {
                return o1.cost - o2.cost;
            }
        });

        SingleOutputStreamOperator<String> select = eventPatternStream.select(new PatternSelectFunction<Event, String>() {
            @Override
            public String select(Map<String, List<Event>> map) throws Exception {
                Event success = map.get("success").iterator().next();
                Event fail = map.get("fail").iterator().next();
                return "catch";
            }
        });

//        SingleOutputStreamOperator<String> select = eventPatternStream.select(new OutputTag<String>("time-out"), new PatternTimeoutFunction<Event, String>() {
//            @Override
//            public String timeout(Map<String, List<Event>> map, long l) throws Exception {
//                //超时时间出处理
//                return null;
//            }
//        }, new PatternSelectFunction<Event, String>() {
//            @Override
//            public String select(Map<String, List<Event>> map) throws Exception {
//                Event success = map.get("success").iterator().next();
//                Event fail = map.get("fail").iterator().next();
//                return "catch";
//            }
//        });

//        eventPatternStream.process(new PatternProcessFunction<Event, String>() {
//            @Override
//            public void processMatch(Map<String, List<Event>> map, Context context, Collector<String> collector) throws Exception {
//                //处理匹配事件
//            }
//        }).print();

        select.print();
        streamExecutionEnvironment.execute();
    }

    static class Event {
        private String type;

        private int cost;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getCost() {
            return cost;
        }

        public void setCost(int cost) {
            this.cost = cost;
        }

        public Event(String type, int cost) {
            this.type = type;
            this.cost = cost;
        }
    }
}
