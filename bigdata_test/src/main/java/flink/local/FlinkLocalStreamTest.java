package flink.local;

import flink.partitioner.MyFlinkPartitioner;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.contrib.streaming.state.EmbeddedRocksDBStateBackend;
import org.apache.flink.contrib.streaming.state.RocksDBStateBackend;
import org.apache.flink.runtime.state.filesystem.FsStateBackend;
import org.apache.flink.runtime.state.filesystem.FsStateBackendFactory;
import org.apache.flink.runtime.state.hashmap.HashMapStateBackend;
import org.apache.flink.runtime.state.memory.MemoryStateBackend;
import org.apache.flink.runtime.state.memory.MemoryStateBackendFactory;
import org.apache.flink.runtime.state.storage.FileSystemCheckpointStorage;
import org.apache.flink.runtime.state.storage.JobManagerCheckpointStorage;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.*;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.streaming.api.functions.co.CoMapFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.api.windowing.assigners.EventTimeSessionWindows;
import org.apache.flink.streaming.api.windowing.assigners.GlobalWindows;
import org.apache.flink.streaming.api.windowing.assigners.SlidingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.evictors.CountEvictor;
import org.apache.flink.streaming.api.windowing.triggers.EventTimeTrigger;
import org.apache.flink.streaming.api.windowing.windows.GlobalWindow;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.runtime.operators.util.AssignerWithPeriodicWatermarksAdapter;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;
import org.apache.hbase.thirdparty.com.google.common.collect.Lists;
import flink.flatMap.keyedState.MyValueStateFlatMapFunction;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author Kled
 * @date 2021/10/25 5:17 下午
 */
public class FlinkLocalStreamTest {

    public static void main(String[] args) throws Exception {
        //Flink的环境变量、系统变量、配置等工具类
        //ParameterTool.fromArgs()
//        ParameterTool.fromPropertiesFile()
//        ParameterTool.fromSystemProperties()

//        socketTextStream();
          //kafkaStream();
//        fileTextStream();
//        collectionStream();
//        userDefinedSourceStream();

          //transformStream();
          //stateStream();
          windowWithWatermarkStream();
    }

    private static void windowWithWatermarkStream() throws Exception {
        StreamExecutionEnvironment streamExecutionEnvironment = StreamExecutionEnvironment.createLocalEnvironment();

        //使用Event Time的优势是结果的可预测性，缺点是缓存较大，增加了延迟，且调试和定位问题更复杂
        streamExecutionEnvironment.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        DataStreamSource<String> stringDataStreamSource = streamExecutionEnvironment.socketTextStream("127.0.0.1", 9999);

        SingleOutputStreamOperator<Tuple2<Long, String>> tuple2SingleOutputStreamOperator = stringDataStreamSource.flatMap(new FlatMapFunction<String, Tuple2<Long, String>>() {
            Random random = new Random();

            @Override
            public void flatMap(String line, Collector<Tuple2<Long, String>> collector) throws Exception {
                String[] words = line.split(" ");
                for (String word : words) {
                    collector.collect(Tuple2.of(random.nextLong(), word));
                }
            }
        });

        //ps：窗口定义流数据的范围，触发器触发计算
        tuple2SingleOutputStreamOperator
                .assignTimestampsAndWatermarks(WatermarkStrategy
                        .<Tuple2<Long, String>>forBoundedOutOfOrderness(Duration.ofSeconds(10)) //watermark ->
                        .withTimestampAssigner((event, timestamp) -> event.f0)) //指定时间字段
                .keyBy(0)
                //.countWindow(100,50) //数据个数驱动
                //时间区间驱动
                //.windowAll() //windowAll的任务并行度是1，不可修改
                .window(TumblingEventTimeWindows.of(org.apache.flink.streaming.api.windowing.time.Time.seconds(10))) //滚动窗口
//                .window(SlidingEventTimeWindows.of(Time.seconds(10), Time.seconds(10))) //滑动窗口
//                .window(EventTimeSessionWindows.withGap(Time.seconds(10))) //session窗口, 时间间隙触发
//                .window(GlobalWindows.create()) //全局窗口, 必须配置触发器
                .trigger(EventTimeTrigger.create()) //窗口计算的触发器，可自定义
                .evictor(CountEvictor.of(100)) //窗口数据剔除
                .allowedLateness(org.apache.flink.streaming.api.windowing.time.Time.seconds(2)) //max(event time) - threshold -  allowedLateness >= window end time触发窗口关闭(计算)。一般不使用
                .sideOutputLateData(new OutputTag<>("late data")) //late data的数据标记
                //.reduce()     //支持ReduceFunction + ProcessWindowFunction
                //.aggregate() //支持AggregateFunction + ProcessWindowFunction
                //.apply() //WindowFunction
                .process(new MySumProcessWindowFunction()).print();

    }

    private static void stateStream() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironment();

        //开启检查点
        //数据量大时，建议5min左右
        env.enableCheckpointing(10000);
        env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);//默认
        env.getCheckpointConfig().setMinPauseBetweenCheckpoints(500);//检查点最小间隔, 防止checkpoint积压
        env.getCheckpointConfig().setCheckpointTimeout(60000);
        env.getCheckpointConfig().enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);//取消时保存
        env.setRestartStrategy(RestartStrategies.fixedDelayRestart(3, Time.of(10, TimeUnit.SECONDS))); //重启策略

//        new MemoryStateBackend();
//        new FsStateBackend();
//        new RocksDBStateBackend();
//        new EmbeddedRocksDBStateBackend();
        env.setStateBackend(new HashMapStateBackend());
        env.getCheckpointConfig().setCheckpointStorage(new FileSystemCheckpointStorage("hdfs://172.16.5.56:9000/flink/checkpoints"));
        //env.getCheckpointConfig().setCheckpointStorage(new JobManagerCheckpointStorage());

        DataStreamSource<Tuple2<Long, Long>> tuple2DataSource = env.fromElements(Tuple2.of(1L, 3L), Tuple2.of(2L, 3L), Tuple2.of(2L, 3L), Tuple2.of(2L, 6L), Tuple2.of(1L, 5L), Tuple2.of(1L, 2L));
        tuple2DataSource.keyBy(new KeySelector<Tuple2<Long, Long>, Long>() {
            @Override
            public Long getKey(Tuple2<Long, Long> longLongTuple2) throws Exception {
                return longLongTuple2.f0;
            }
        }).flatMap(new MyValueStateFlatMapFunction()).print();
//        8> (2,4.0)
//        6> (1,3.3333333333333335)

        env.execute();
    }

    private static void transformStream() throws Exception {
        StreamExecutionEnvironment streamExecutionEnvironment = StreamExecutionEnvironment.createLocalEnvironment();

        DataStreamSource<Long> initialStream = streamExecutionEnvironment.fromElements(2L, 4L, 6L, 8L);
        IterativeStream<Long> iteration = initialStream.iterate();
        DataStream<Long> iterationBody = iteration.map (l -> l - 1);
        DataStream<Long> feedback = iterationBody.filter(new FilterFunction<Long>(){
            @Override
            public boolean filter(Long value) throws Exception {
                return value > 3;
            }
        }).setParallelism(1);
        //这里设置feedback这个数据流是被反馈的通道，只要是value>3的数据都会被重新迭代计算(l -> l-1)
        iteration.closeWith(feedback);
        //输出的流
        DataStream<Long> output = iterationBody.filter(new FilterFunction<Long>(){
            @Override
            public boolean filter(Long value) throws Exception {
                return value <= 3;
            }
        });

        //重分区
        //RoundRobin Partition：轮询重分区
        output.rebalance();
        //ReScaling Partition: 上下游继承的算子数据进行重分区
        output.rescale();
        //Random Partition：随机重分区
        output.shuffle();
        //BroadCast: 广播分区
        output.broadcast();
        //Customer Partition: 用户自定义重分区
        output.partitionCustom(new MyFlinkPartitioner(), new KeySelector<Long, Integer>() {
            @Override
            public Integer getKey(Long l) throws Exception {
                return l.intValue();
            }
        });

        output.print();
        streamExecutionEnvironment.execute();
    }

    private static void userDefinedSourceStream() throws Exception {
        StreamExecutionEnvironment streamExecutionEnvironment = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(new Configuration());

        DataStreamSource<Integer> integerDataStreamSource1 = streamExecutionEnvironment.addSource(new MyFlinkSource(10));
        DataStreamSource<Integer> integerDataStreamSource2 = streamExecutionEnvironment.addSource(new MyFlinkSource(50));

        //将相同类型的流，合成一个，进行处理
        DataStream<Integer> union = integerDataStreamSource1.union(integerDataStreamSource2);
        //每三秒记一次数
//        union.filter(i -> i % 2 == 0).windowAll(TumblingProcessingTimeWindows.of(Time.seconds(3))).sum(0).print();
        union.print();

        //不同类型的流同时用不同的逻辑处理好，形成一个流
        ConnectedStreams<Integer, Integer> connect = integerDataStreamSource1.connect(integerDataStreamSource2);
        connect.map(new CoMapFunction<Integer, Integer, Object>() {
            @Override
            public Object map1(Integer integer) throws Exception {
                return "接收数据源1:" + integer;
            }

            @Override
            public Object map2(Integer integer) throws Exception {
                return "接收数据源2:" + integer;
            }
        }).print();

        streamExecutionEnvironment.execute();
    }

    private static void collectionStream() throws Exception {
        StreamExecutionEnvironment streamExecutionEnvironment = StreamExecutionEnvironment.createLocalEnvironment();
        DataStreamSource<String> stringDataStreamSource = streamExecutionEnvironment.fromCollection(Lists.newArrayList("kled", "lee"));
        stringDataStreamSource.map(str -> str + " hello !! *******").print().setParallelism(1);

        stringDataStreamSource.keyBy(new KeySelector<String, String>() {
            @Override
            public String getKey(String s) throws Exception {
                return s;
            }
        }).asQueryableState("queryValueState"); //发布可查询状态
        streamExecutionEnvironment.execute();
    }

    private static void fileTextStream() throws Exception {
        StreamExecutionEnvironment streamExecutionEnvironment = StreamExecutionEnvironment.createLocalEnvironment();

        DataStreamSource<String> stringDataStreamSource = streamExecutionEnvironment.readTextFile("/Users/kled/git/test/bigdata_test/src/main/resources/flink/input/users");
        stringDataStreamSource.map(str -> str + " hello !! *******").print().setParallelism(1);

        streamExecutionEnvironment.execute();
    }

    private static void kafkaStream() throws Exception {
        StreamExecutionEnvironment streamExecutionEnvironment = StreamExecutionEnvironment.createLocalEnvironment();

        String topic="test123";
        Properties consumerProperties = new Properties();
        consumerProperties.setProperty("bootstrap.servers","172.16.5.56:9092");
        consumerProperties.setProperty("group.id","test123");

        FlinkKafkaConsumer<String> myConsumer =
                new FlinkKafkaConsumer<>(topic, new SimpleStringSchema(),
                        consumerProperties);

        DataStreamSource<String> stringDataStreamSource = streamExecutionEnvironment.addSource(myConsumer).setParallelism(3);
        stringDataStreamSource.print();

        streamExecutionEnvironment.execute();
    }

    private static void socketTextStream() throws Exception {
        //1.程序的入口，环境上下文准备
        StreamExecutionEnvironment streamExecutionEnvironment = StreamExecutionEnvironment.createLocalEnvironment();
//        streamExecutionEnvironment.setParallelism(2);
//        streamExecutionEnvironment.setMaxParallelism(4);

        //2.数据的输入
        DataStreamSource<String> stringDataStreamSource = streamExecutionEnvironment.socketTextStream("127.0.0.1", 9999);

        //3.数据的处理
        SingleOutputStreamOperator<Tuple2<String, Integer>> sum = stringDataStreamSource.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public void flatMap(String line, Collector<String> collector) throws Exception {
                String[] words = line.split(" ");
                for (String word : words) {
                    collector.collect(word);
                }
            }
        }).map(word -> Tuple2.of(word, 1)).keyBy(new KeySelector<Tuple2<String, Integer>, Object>() {
            @Override
            public Object getKey(Tuple2<String, Integer> stringIntegerTuple2) throws Exception {
                return stringIntegerTuple2.f0;
            }
        }).sum(2).setParallelism(1);

        //4.数据的输出
        sum.print();

        //5.程序的执行
        streamExecutionEnvironment.execute();
    }

    static class MyFlinkSource implements /*并行执行数据源：ParallelSourceFunction*/SourceFunction<Integer> {

        private static boolean running = true;

        private static final Random random = new Random();

        private Integer bound;

        public MyFlinkSource(Integer bound) {
            this.bound = bound;
        }

        @Override
        public void run(SourceContext<Integer> sourceContext) throws Exception {
            while (running){
                sourceContext.collect(random.nextInt(bound));
                Thread.sleep(1000);
            }
        }

        @Override
        public void cancel() {
            running = false;
        }
    }

    static class MySumProcessWindowFunction extends ProcessWindowFunction<Tuple2<Long, String>, Tuple2<String, Integer>, Tuple, TimeWindow>{
        FastDateFormat dateFormat = FastDateFormat.getInstance("HH:mm:ss");
        @Override
        public void process(Tuple key, Context context, Iterable<Tuple2<Long, String>> elements, Collector<Tuple2<String, Integer>> collector) throws Exception {
                System.out.println("当前系统时间: " + dateFormat.format(System.currentTimeMillis()));
                System.out.println("窗口处理时间: " + dateFormat.format(context.currentProcessingTime()));
                System.out.println("窗口开始时间: " + dateFormat.format(context.window().getStart()));

                int sum = 0;
                for (Tuple2<Long, String> element : elements) {
                    sum += 1;
                }
                collector.collect(Tuple2.of(key.getField(0), sum));

                System.out.println("窗口结束时间: " + dateFormat.format(context.window().getEnd()));
        }
    }

    static class MyKeyedProcessWindowFunction extends KeyedProcessFunction{

        @Override
        public void open(Configuration parameters) throws Exception {
            super.open(parameters);
        }

        @Override
        public void processElement(Object o, Context context, Collector collector) throws Exception {
            //注册定时器
            context.timerService().registerEventTimeTimer(100);
        }

        @Override
        //定时器逻辑
        public void onTimer(long timestamp, OnTimerContext ctx, Collector out) throws Exception {
            super.onTimer(timestamp, ctx, out);
        }
    }
}
