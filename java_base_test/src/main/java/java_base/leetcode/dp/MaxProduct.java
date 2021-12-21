package java_base.leetcode.dp;

/**
 * @author Kled
 * @date 2021/11/22 7:40 下午
 */
public class MaxProduct {
    public int maxProduct(int[] nums) {
        //滚动数组优化
        int[] maxDp = new int[nums.length];
        int[] minDp = new int[nums.length];
        maxDp[0] = nums[0];
        minDp[0] = nums[0];
        int max = maxDp[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] >= 0) {
                maxDp[i] = Math.max(nums[i], nums[i] * maxDp[i - 1]);
                minDp[i] = Math.min(nums[i], nums[i] * minDp[i - 1]);
            } else {
                maxDp[i] = Math.max(nums[i], nums[i] * minDp[i - 1]);
                minDp[i] = Math.min(nums[i], nums[i] * maxDp[i - 1]);
            }
            max = Math.max(maxDp[i], max);
        }
        return max;
    }

    public int maxProduct2(int[] nums) {
        //滚动数组优化
        int maxDp = nums[0];
        int minDp = nums[0];
        int max = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] >= 0) {
                maxDp = Math.max(nums[i], nums[i] * maxDp);
                minDp = Math.min(nums[i], nums[i] * minDp);
            } else {
                int lastMaxDp = maxDp;
                maxDp = Math.max(nums[i], nums[i] * minDp);
                minDp = Math.min(nums[i], nums[i] * lastMaxDp);
            }
            max = Math.max(maxDp, max);
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println(new MaxProduct().maxProduct2(new int[]{-4,-3,-2}));
//        System.out.println(new MaxProduct().maxProduct(new int[]{2, 3, -2, 4}));
//        System.out.println(new MaxProduct().maxProduct(new int[]{-2,0,-1}));
    }
}
