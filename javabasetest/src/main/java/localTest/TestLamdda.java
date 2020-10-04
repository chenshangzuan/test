package localTest;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestLamdda {

    public static void main(String[] args) {
        int y = 1;
        new Thread(() -> {
            System.out.println(y);
        }).start();

        List<String> of = Arrays.asList("a", "b", "c", "d");
        List<String> list = Lists.newArrayList();
        of.forEach(x -> {
            list.add(x);
            System.out.println(x + "test");
        });

        List<String> off = of.stream().filter(x -> x.equals("a")).collect(Collectors.toList()); //过滤
        off.forEach(System.out::println);

        String s = of.stream().max(String::compareTo).get();  //最大值(极值)
        System.out.println("*********" + s);

        List<String> offf = of.stream().map(x -> x + "^^^^").collect(Collectors.toList()); //转化
        offf.forEach(System.out::println);

        //BufferedReader br = new BufferedReader(new FileReader("c:\\SUService.log"));     //IO stream
        //int longest = br.lines().
        //        mapToInt(String::length).
        //        max().
        //        getAsInt();  //intStream操作
        //br.close();
        //System.out.println(longest);

        List<String> o = Arrays.asList("a", "a", "c", "d", "e", "e");   //等式分组  List<String> -> Map<String, List<String>>
        Map<String, List<String>> offff = o.stream().collect(Collectors.groupingBy(String::toString));
        for (Map.Entry m : offff.entrySet()) {
            System.out.println("key:" + m.getKey() + " value:" + m.getValue());
        }
        Map<Boolean, List<String>> offfff = o.stream().collect(Collectors.partitioningBy(x -> x.getBytes()[0] > 'c'));
        for (Map.Entry m : offfff.entrySet()) {
            System.out.println("key:" + m.getKey() + " value:" + m.getValue());  //不等式分组  List<String> -> Map<Boolean, List<String>>
        }

        int[] a = o.stream().mapToInt(x -> x.getBytes()[0]).toArray();   // Collection -> int[]
        for (int i : a) {
            System.out.println(i);
        }

        List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 5);
        integerList.stream().flatMap(new Function<Integer, Stream<String>>() {
            @Override
            public Stream<String> apply(Integer integer) {
                return Arrays.asList(integer + "a", integer + "b").stream();
            }
        }).forEach(item -> System.out.print(item + " "));

        List<String> stringListlist = Arrays.asList("one", "two", "three", "four");
        stringListlist.stream().filter(e -> e.length() > 3).peek(e -> {
            //两个filter 遍历时需满足二者条件
            System.out.println("第一次符合条件的值为: " + e);
            for (int i = 0; i < 3; i++) {
                System.out.println("第一次符合条件的值为: " + e + i);
            }
        }).filter(e -> e.length() > 4).peek(e -> System.out.println("第二次符合条件的值为: " + e)).collect(Collectors.toList());

        String concat = Stream.of("A", "B", "C", "D").reduce("", String::concat); // reduce 聚合

        //并行数组处理，排序和插入
        long[] arrayOfLong = new long[20000];

        Arrays.parallelSetAll(arrayOfLong, index -> ThreadLocalRandom.current().nextInt(1000000));
        Arrays.stream(arrayOfLong).limit(10).forEach(i -> System.out.print(i + " "));
        System.out.println();

        Arrays.parallelSort(arrayOfLong);
        Arrays.stream(arrayOfLong).limit(10).forEach(i -> System.out.print(i + " "));
        System.out.println();

    }

}
