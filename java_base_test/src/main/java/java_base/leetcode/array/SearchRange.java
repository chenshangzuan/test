package java_base.leetcode.array;

/**
 * @author Kled
 * @date 2021/12/19 3:54 PM
 */
public class SearchRange {
    public int[] searchRange(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int[] indexRange = {-1, -1};
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > target) {
                right = mid - 1;
            }
            if (nums[mid] < target) {
                left = mid + 1;
            }
            if (nums[mid] == target) {
                int l = mid;
                int r = mid;
                for (int i = mid; i >= 0; i--) {
                    if (nums[i] == target) {
                        l = i;
                    }else {
                        break;
                    }
                }
                for (int i = mid; i < nums.length; i++) {
                    if (nums[i] == target) {
                        r = i;
                    }else {
                        break;
                    }
                }
                return new int[]{l, r};
            }
        }
        return indexRange;
    }

    public static void main(String[] args) {
//        new SearchRange().searchRange(new int[]{5, 7, 7, 8, 8, 10}, 8);
        new SearchRange().searchRange(new int[]{1}, 8);
    }
}
