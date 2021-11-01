package flink.local;

import com.google.common.collect.Lists;
import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.io.LocalCollectionOutputFormat;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.operators.FilterOperator;
import org.apache.flink.api.java.operators.IterativeDataSet;
import org.apache.flink.api.java.operators.UnionOperator;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.spark.sql.catalyst.expressions.Rand;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

/**
 * @author Kled
 * @date 2021/9/10 2:53 下午
 */
public class FlinkLocalDataSetTest {

    public static void main(String[] args) throws Exception {
        //collectionDs();
        //fileTextDs();
        transformDs();
    }

    private static void transformDs() throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.createLocalEnvironment();
        DataSet<Integer> ds1 = env.fromElements(1, 2);
        DataSet<Integer> ds2 = env.fromCollection(Lists.newArrayList(3, 4));
        UnionOperator<Integer> union = ds1.union(ds2);
        union.print();
//        env.execute();
    }

    private static void collectionDs() throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.createLocalEnvironment();
        DataSet<String> userDs = env.fromCollection(Lists.newArrayList("kled", "lee"));
        Collection<String> result = new ArrayList<>();
        userDs.output(new LocalCollectionOutputFormat<>(result));
        env.execute();
        // Do some work with the resulting ArrayList (=Collection).
        for(String t : result) {
            System.err.println("Result = "+t);
        }
    }

    private static void fileTextDs() throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.createLocalEnvironment();
        DataSet<String> data = env.readTextFile("/Users/kled/git/test/bigdata_test/src/main/resources/flink/input/users");
        data
                .filter(new FilterFunction<String>() {
                    @Override
                    public boolean filter(String value) {
                        return value.startsWith("k");
                    }
                })
                .writeAsText("/Users/kled/git/test/bigdata_test/src/main/resources/flink/output/users");
        JobExecutionResult res = env.execute();
    }
}
