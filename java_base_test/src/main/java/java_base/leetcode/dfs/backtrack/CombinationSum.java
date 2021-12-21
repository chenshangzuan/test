package java_base.leetcode.dfs.backtrack;

import java.util.*;

/**
 * @author Kled
 * @date 2021/12/10 7:54 下午
 */
public class CombinationSum {

    //回溯+剪枝
    Set<List<Integer>> rets = new HashSet<>();
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        Arrays.sort(candidates);
        backtrack(candidates, new ArrayList<>(), 0, target, 0);
        return new ArrayList<>(rets);
    }

    public void backtrack(int[] candidates, List<Integer> used, int sum, int target, int startIndex) {
        if (sum > target) {
            return;
        }

        if (sum == target) {
            ArrayList<Integer> ret = new ArrayList<>(used);
            ret.sort(Integer::compareTo);
            rets.add(ret);
            return;
        }

        for (int i = startIndex; i < candidates.length; i++) {
            if(candidates[i] > target){
                break;
            }
            used.add(candidates[i]);
            sum += candidates[i];
            backtrack(candidates, used, sum, target, i);
            used.remove(used.size() - 1);
            sum -= candidates[i];
        }
    }

    //参考答案
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        List<Integer> combine = new ArrayList<Integer>();
        dfs(candidates, target, ans, combine, 0);
        return ans;
    }

    public void dfs(int[] candidates, int target, List<List<Integer>> ans, List<Integer> combine, int idx) {
        if (idx == candidates.length) {
            return;
        }
        if (target == 0) {
            ans.add(new ArrayList<Integer>(combine));
            return;
        }
        // 直接跳过
        dfs(candidates, target, ans, combine, idx + 1);
        // 选择当前数
        if (target - candidates[idx] >= 0) {
            combine.add(candidates[idx]);
            dfs(candidates, target - candidates[idx], ans, combine, idx);
            combine.remove(combine.size() - 1);
        }
    }

    public static void main(String[] args) {
        System.out.println(new CombinationSum().combinationSum(new int[]{2, 3, 8, 4}, 6));
    }
}
