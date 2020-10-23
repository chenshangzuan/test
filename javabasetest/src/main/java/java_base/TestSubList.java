package java_base;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TestSubList {

    public static void main(String[] args) {
        
        List<Integer> l = new ArrayList<>();
        l.add(10);
        l.add(1);
        l.add(300);
        l.add(2);
        l.add(-1);
        l.add(17);
        l.add(16);
        l.add(10);
        System.out.println(l.stream().filter(x->x.equals(1)).collect(Collectors.toList()));
        System.out.println(l.contains(300));
        //List<Integer> sublist = l.subList(0, 5);
        //for (int i = 0; i < sublist.size(); i++) {
        //    //System.out.println(lists.get(i));
        //    System.out.println(sublist.get(i));
        //}

        List<Integer> list = Lists.newArrayList(l);
        list.clear();
        l.stream().sorted().collect(Collectors.toList());
        System.out.println(list);
        System.out.println(l);

    }

}
