package spark.local;


import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.graphx.Edge;
import org.apache.spark.graphx.Graph;
import org.apache.spark.graphx.GraphOps;
import org.apache.spark.graphx.VertexRDD;
import org.apache.spark.graphx.impl.VertexRDDImpl;
import org.apache.spark.rdd.RDD;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.storage.StorageLevel;
import scala.Tuple2;
import scala.reflect.ClassTag;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kled
 * @date 2021/11/9 10:58 上午
 */
public class SparkLocalGraphTest {
    //TODO: PageRank、Connected Components、Triangle Counting
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("graphx").setMaster("local[2]");
        JavaSparkContext sparkContext = new JavaSparkContext(conf);
        connectedComponent(sparkContext);
    }

    public static void connectedComponent(JavaSparkContext sparkContext){
        List<Tuple2<Long, String>> vertices = new ArrayList<>();
        vertices.add(new Tuple2<>(1L, "张三"));
        vertices.add(new Tuple2<>(2L, "李四"));
        vertices.add(new Tuple2<>(3L, "王五"));
        vertices.add(new Tuple2<>(4L, "赵六"));
        vertices.add(new Tuple2<>(5L, "黄七"));

        List<Edge<String>> edges = new ArrayList<>();
        edges.add(new Edge<>(1L, 2L, "朋友"));
        edges.add(new Edge<>(2L, 3L, "朋友"));
        edges.add(new Edge<>(4L, 5L, "母子"));

        JavaRDD<Tuple2<Long, String>> verticesJavaRDD = sparkContext.parallelize(vertices);
        // Long需要转成Object
        RDD<Tuple2<Object, String>> verticesRDD = verticesJavaRDD.map(new Function<Tuple2<Long, String>, Tuple2<Object, String>>() {
            @Override
            public Tuple2<Object, String> call(Tuple2<Long, String> vertice) throws Exception {
                return new Tuple2<Object, String>(vertice._1, vertice._2);
            }
        }).rdd();

        RDD<Edge<String>> edgesRDD = sparkContext.parallelize(edges).rdd();

        ClassTag<String> stringTag = scala.reflect.ClassTag$.MODULE$.apply(String.class);
        Graph<String, String> graph = Graph.apply(verticesRDD, edgesRDD, "", StorageLevel.MEMORY_ONLY(), StorageLevel.MEMORY_ONLY(), stringTag, stringTag);
        // 此处与scala版本不一样
        GraphOps<String, String> ops = Graph.graphToGraphOps(graph, stringTag, stringTag);

        ops.inDegrees().toJavaRDD().foreach(new VoidFunction<Tuple2<Object, Object>>() {
            @Override
            public void call(Tuple2<Object, Object> t) throws Exception {
                System.out.println("indegress:" + t);
            }
        });

        // 连通图需要构造GraphOps
        Graph<Object, String> connectedComponentsGraph = ops.connectedComponents();
        connectedComponentsGraph.vertices().toJavaRDD().foreach(new VoidFunction<Tuple2<Object, Object>>() {
            @Override
            public void call(Tuple2<Object, Object> t) throws Exception {
                //连通子图的guid取最小主键的uuid
                System.out.println(t);
            }
        });


    }
}
