package localTest;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

/**
 * @author kled
 * @version $Id: TestVapList.java, v 0.1 2018-11-22 17:38:03 kled Exp $
 */
public class TestVapList {
    public static void main(String[] args) {
        List<Integer> list1 = Lists.newArrayList(1, 2, 3);
        List<Integer> list2 = Lists.newArrayList(1, 2);
        List<Pair<Integer, Integer>> pairList = Lists.newArrayList();
        for (int i = 0; i < list1.size(); i++) {
            for (int j = i; j < list2.size(); j++) {
                pairList.add(MutablePair.of(list1.get(i), list2.get(j)));
            }

            for (int k = i+1; k < list1.size(); k++) {
                pairList.add(MutablePair.of(list1.get(k), list2.get(i)));
            }
        }
        System.out.println(pairList);
    }
}
