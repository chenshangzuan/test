package java_base.leetcode.dfs.backtrack;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kled
 * @date 2021/12/1 6:42 下午
 */
public class ArrayPermute {
    List<List<Integer>> rets = new ArrayList<>();
    public List<List<Integer>> permute(int[] nums) {
        backtrack(nums, new ArrayList<>());
        return rets;
    }

    public void backtrack(int[] nums, List<Integer> ret){
        if(ret.size() == nums.length){
            rets.add(new ArrayList<>(ret));
            return;
        }

        for (int num : nums) {
            if (ret.contains(num)) {
                continue;
            }
            ret.add(num);
            backtrack(nums, ret);
            ret.remove(ret.size() - 1);
        }
    }



}
