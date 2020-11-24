package java_base.data_container;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestCollections {

    public static void main(String[] args) {

        //Map<String, String> map111 = ImmutableMap.<String, String>builder().put("2","1").build();
        //map111.put("1","1");
        //map111.remove("1");

        List<String> list = Arrays.asList("aaa");
        String ssss = list.stream().filter(x -> x.equals("aaa")).findFirst().orElse(null);
        System.out.println(ssss);

        Map<String,String> map = new HashMap<>();
        map.forEach((k,v)->{  //Biconsumer    java 8 新增
            System.out.println("v="+k+",v="+v);
        });
        //putIfAbsent、compute（修改）、

        //System.out.println("aaan"+ new HashMap<>().get("test").toString());

        //Integer a =null ;
        //System.out.println(a -1);

        //       List<String> testList = new ArrayList<>();
        //       List<String> listMirror = Collections.unmodifiableList(testList);
        //       try {
        //           //listMirror.add("test");
        //           testList.add("test");
        //        } catch (Exception e) {
        //            // TODO: handle exception
        //            e.printStackTrace();
        //        } finally {
        //            listMirror.forEach(System.out::println);
        //        }
        List<Integer> testList = new ArrayList<>();
        List<Integer> testList2 = new ArrayList<>();
        Set<Integer> testSet = new HashSet<>();
        testList.add(1);
        testList.add(2);
        testList.add(2);
        testList.add(3);
        testList.add(3);

        for (Integer integer : testList2) {
            System.out.println(integer);
        }

        //        testList2.add(1);
        //        testList2.add(1);
        //        testList2.add(2);
        //        testList2.add(2);
        //        System.out.println(testList.removeAll(testList2));
        //
        //        testSet.add(2);
        //        testSet.add(4);
        //        System.out.println(testList.removeAll(testSet));
        //        for (Integer integer : testList) {
        //            System.out.println(integer);
        //        }

        //        for (Iterator iterator = new HashSet<>(testList).iterator(); iterator.hasNext();) {
        //            System.out.println(iterator.next());
        //        }

        List l = Arrays.asList("a","b");
        System.out.println(l);


        for(int i=0; i<10; i++){
            System.out.println("test:"+i);
            if(i==3){
                return;
            }
        }



    }

}
