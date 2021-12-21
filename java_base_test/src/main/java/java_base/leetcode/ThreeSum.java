package java_base.leetcode;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Kled
 * @date 2021/11/26 3:44 下午
 */
public class ThreeSum {

    //回溯法，超时
    public List<List<Integer>> ret = new ArrayList<>();

    public Set<List<Integer>> ret2 = new HashSet<>();

    public List<List<Integer>> threeSum(int[] nums) {
        dfs(nums, new ArrayList<>(), 0);
        return ret.stream().distinct().collect(Collectors.toList());
    }

    public boolean dfs(int[] nums, List<Integer> selectedNums, int startIndex) {
        if (selectedNums.size() == 3) {
            if (selectedNums.get(0) + selectedNums.get(1) + selectedNums.get(2) == 0) {
                ArrayList<Integer> list = new ArrayList<>(selectedNums);
                list.sort(Integer::compareTo);
                ret.add(list);
                return true;
            }
            return false;
        }

        for (int i = startIndex; i < nums.length; i++) {
            selectedNums.add(nums[i]);
            boolean b = dfs(nums, selectedNums, i + 1);
            selectedNums.remove(selectedNums.size() - 1);
            if (b) {
                break;
            }
        }
        return false;
    }

    public List<List<Integer>> threeSum2(int[] nums) {
        if (nums.length < 3) {
            return new ArrayList<>();
        }
        Arrays.sort(nums);
        for (int i = 0; nums[i] <= 0 && i < nums.length - 2; i++) {
            for (int j = nums.length - 1; nums[j] >= 0 && j > i + 1; j--) {
                int targetIndex = Arrays.binarySearch(nums, i + 1, j - 1, -nums[i] - nums[j]);
                // -(low+1)代表找不到
                if (targetIndex != -(i+2)) {
                    ret2.add(Arrays.asList(nums[i], -nums[i] - nums[j], nums[j]));
                }
//                boolean b = binarySearch(nums, -nums[i] - nums[j], i + 1, j - 1);
//                if (b) {
//                    ret2.add(Arrays.asList(nums[i], -nums[i] - nums[j], nums[j]));
//                }
            }
        }
        return new ArrayList<>(ret2);
    }

    public boolean binarySearch(int[] nums, int target, int startIndex, int endIndex) {
        int left = startIndex;
        int right = endIndex;
        while (left <= right) {
            int middle = (left + right) / 2;
            if (nums[middle] == target) {
                return true;
            }
            if (nums[middle] < target) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }
        return false;
    }

    public static void main(String[] args) {
//        System.out.println(new ThreeSum().threeSum2(new int[]{-1, 0, 1, 2, -1, -4}));
        System.out.println(new ThreeSum().threeSum2(new int[]{-2, 0, 1, 1, 2}));
    }
}
