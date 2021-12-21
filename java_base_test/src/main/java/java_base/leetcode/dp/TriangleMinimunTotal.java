package java_base.leetcode.dp;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kled
 * @date 2021/11/16 7:50 下午
 */
public class TriangleMinimunTotal {
    public int minimumTotal(List<List<Integer>> triangle) {
        List<List<Integer>> dp = new ArrayList<>();
        dp.add(triangle.get(0));

        for (int i = 1; i < triangle.size(); i++) {
            List<Integer> lastDp = dp.get(i - 1);
            List<Integer> currentRow = triangle.get(i);
            List<Integer> dpi = new ArrayList<>();
            dpi.add(currentRow.get(0) + lastDp.get(0));
            for (int j = 1; j < currentRow.size(); j++) {
                if (j < lastDp.size()) {
                    dpi.add(currentRow.get(j) + Math.min(lastDp.get(j), lastDp.get(j - 1)));
                } else {
                    dpi.add(currentRow.get(j) + lastDp.get(j - 1));
                }
            }
            dp.add(dpi);
        }
        return dp.get(dp.size() - 1).stream().min(Integer::compare).orElse(0);
    }

    public static void main(String[] args) {
        System.out.println(new TriangleMinimunTotal().minimumTotal(Lists.newArrayList(Lists.newArrayList(2), Lists.newArrayList(3, 4), Lists.newArrayList(6, 5, 7), Lists.newArrayList(4, 1, 8, 3))));
    }
}
