package java_base.guava;/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2018 All Rights Reserved.
 */

import com.google.common.base.CaseFormat;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;
import com.google.common.collect.Table;
import com.google.common.math.IntMath;
import com.google.common.primitives.Ints;
import com.sun.tools.javac.util.StringUtils;

import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author chenpc
 * @version $Id: java_base.guava.TestGuava.java, v 0.1 2018-04-06 10:45:36 chenpc Exp $
 */
public class TestGuava {

    public static void main(String[] args) throws Exception {

        System.out.println(getUriTemplateVariablePrefix("SITE"));

        String toSplitString = "a=b;c=d,e=f";
        Map<String, String> kvs = Splitter.onPattern("[,;]{1,}").withKeyValueSeparator('=').split(toSplitString);
        for (Map.Entry<String, String> entry : kvs.entrySet()) {
            System.out.println(String.format("%s=%s", entry.getKey(), entry.getValue())); //而二次拆分
        }

        Map<String, String> map = new HashMap<String, String>();
        map.put("a", "b");
        map.put("c", "d");
        String mapJoinResult = Joiner.on(",").withKeyValueSeparator("=").join(map);
        System.out.println(mapJoinResult); //二次合并

        List<String> list = Lists.newArrayList();
        Preconditions.checkArgument(list.isEmpty() == true);

        //Optional<String> possibleNull = Optional.of(null);  //ofNullable 可配合map、flatMap、filter使用
        Optional<String> possibleNull = null;
        try {
            possibleNull = Optional.absent();
            String jim = possibleNull.get(); //orElse()、orElseGet()、orElseThrow()
        } catch (Exception e) {
            e.printStackTrace();
        }

        //不可变集合无法add和remove,否则抛异常
        Set<String> immutableNamedColors = ImmutableSet.<String>builder().add("red", "green", "black", "white", "grey").build();

        immutableNamedColors = ImmutableSet.of("red", "green", "black", "white", "grey");

        ImmutableSet.copyOf(new String[] {"red", "green", "black", "white", "grey"});

        //Multiset没有继承Set接口，它继承的是Collection<E>接口，你可以向Multiset中添加重复的元素，Multiset会对添加的元素做一个计数。
        Multiset multiset = HashMultiset.create();
        String sentences = "this is a story, there is a good girl in the story.";
        Iterable<String> words = Splitter.onPattern("[^a-z]{1,}").omitEmptyStrings().trimResults().split(sentences);
        for (String word : words) {
            multiset.add(word);
        }

        for (Object element : multiset.elementSet()) {
            System.out.println((String) element + ":" + multiset.count(element));
        }

        //BiMap--双向Map，键值互换
        //MultiMap，一键多值  Stream->groupBy

        //Table。使用Table可以实现二维矩阵的数据结构，可以是稀溜矩阵。
        Table<Integer, Integer, String> table = HashBasedTable.create();
        for (int row = 0; row < 10; row++) {
            for (int column = 0; column < 5; column++) {
                table.put(row, column, "value of cell (" + row + "," + column + ")");
            }
        }
        for (int row = 0; row < table.rowMap().size(); row++) {
            Map<Integer, String> rowData = table.row(row);
            for (int column = 0; column < rowData.size(); column++) {
                System.out.println("cell(" + row + "," + column + ") value is:" + rowData.get(column));
            }
        }

        //Ints  int数组的操作类
        //8种基本数据类型均有对应的操作类
        //asList、toArray、join
        System.out.println(Ints.compare(1, 2));

        //IntMath、LongMath等数字计算类
        System.out.println(IntMath.divide(100, 5, RoundingMode.UNNECESSARY));


        //CharMatcher.WHITESPACE, CharMatcher.JAVA_DIGIT 或者CharMatcher.ASCII
        //removeFrom、matchesAllOf、matchesAnyOf、matchesNoneOf、indexIn, lastIndexIn and countIn。。。。
//        String string = CharMatcher.ASCII.retainFrom("some text 89983 and more"); //print 89983
//        System.out.println(string);

        //Iterators是Guava中对Iterator迭代器操作的帮助类，包括any/all/get/find/filter/transform等功能，需要Predicate接口辅助

        //Map usdPriceMap = Maps.transformValues(ImmutableMap.of("key1", new Double(1), "key2", new Double(1)), new Function<>() {
        //    double eurToUsd = 1.4888;
        //    public Double apply(final Double from) {
        //        return from * eurToUsd;
        //    }
        //});

        //CaseFormat 变量命名规则转化
        //LOWER_CAMEL(lowerCamel) LOWER_HYPHEN(hyphen) LOWER_UNDERSCORE(lower_underscore) UPPER_CAMEL(UpperCamel) UPPER_UNDERSCORE(UPPER_UNDERSCORE)
        System.out.println(CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_CAMEL, "test-data"));
    }

    private static String getUriTemplateVariablePrefix(String userOpsLogType){
        StringBuffer prefix = new StringBuffer();
        if(userOpsLogType != null){
            List<String> list = Splitter.onPattern("_").omitEmptyStrings().trimResults().splitToList(StringUtils.toLowerCase(userOpsLogType));
            prefix.append(list.get(0));
            for (int i = 0; i < list.size(); i++) {
                //将字符串转化成驼峰形式
                if(i == 0){
                    continue;
                }
                prefix.append(Character.toUpperCase(list.get(i).charAt(0))).append(list.get(i).substring(1));
            }
        }

        return prefix.toString();
    }
}
