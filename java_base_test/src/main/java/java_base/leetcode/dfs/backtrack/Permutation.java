package java_base.leetcode.dfs.backtrack;

import com.google.common.collect.Lists;

import java.util.*;

/**
 * @author Kled
 * @date 2021/11/14 5:02 下午
 */
public class Permutation {
    // 排列组合算法
    private static List<List<Integer>> output = new LinkedList();

    //穷举法
    public static List<List<Integer>> permute(List<Integer> nums, // 待排列数组
                                              int start //起始位置
    ) {
        if (start == nums.size()) {
            output.add(new ArrayList<>(nums));
        }
        for (int i = start; i < nums.size(); i++) {
            // 做选择，交换元素位置
            Collections.swap(nums, start, i);
            // 递归，缩小规模
            permute(nums, start + 1);
            // 撤销选择，回溯，即恢复到原状态,
            Collections.swap(nums, start, i);
        }
        return output;
    }

    //回溯法
    private static List<List<Integer>> backtrack(int[] nums,List<Integer> track){
        // 结束条件
        if (track.size() == nums.length){
            output.add(new LinkedList<>(track));
            return null;
        }
        for (int i = 0; i < nums.length; i++) {
            if (track.contains(nums[i]))
                continue;
            // 做选择
            track.add(nums[i]);
            backtrack(nums,track);
            // 撤销选择
            track.remove(track.size()-1);
        }
        return output;
    }

    // 测试
    public static void main(String[] args) {
//        List<Integer> nums = Arrays.asList(1, 2, 3, 4);
//        List<List<Integer>> lists = permute(nums, 0);

        List<List<Integer>> lists = backtrack(new int[]{1, 2, 3, 4}, Lists.newArrayList());
        lists.forEach(System.out::println);
    }
}
