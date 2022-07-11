package java_base.leetcode.bit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Kled
 * @date 2021/12/23 5:50 PM
 */
public class SubSets {
    //二进制位代表数组下标对应的元素是否存在
    List<Integer> t = new ArrayList<Integer>();
    public List<List<Integer>> subsets2(int[] nums) {
        int n = nums.length;
        for (int mask = 0; mask < (1 << n); ++mask) {
            t.clear();
            for (int i = 0; i < n; ++i) {
                if ((mask & (1 << i)) != 0) {
                    t.add(nums[i]);
                }
            }
            ans.add(new ArrayList<Integer>(t));
        }
        return ans;
    }

    List<List<Integer>> ans = new ArrayList<>();
    public List<List<Integer>> subsets(int[] nums) {
        Arrays.sort(nums);
        ans.add(new ArrayList<>());
        backtrack(nums, new ArrayList<>(), 0);
        return ans;
    }

    private void backtrack(int[] nums, ArrayList<Integer> path, int startIndex) {
        for (int i = startIndex; i < nums.length; i++) {
            if(path.contains(nums[i])){
                continue;
            }
            path.add(nums[i]);
            ans.add(new ArrayList<>(path));
            backtrack(nums, path, i + 1);
            path.remove(path.size() - 1);
        }
    }

    public static void main(String[] args) {
        System.out.println(new SubSets().subsets(new int[]{1,2,3}));
    }
}
