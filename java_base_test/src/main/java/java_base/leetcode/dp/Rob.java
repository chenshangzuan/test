package java_base.leetcode.dp;

/**
 * @author Kled
 * @date 2021/11/22 8:15 下午
 */
public class Rob {
    public int rob(int[] nums) {
        int[] dp = new int[nums.length + 1];
        dp[0] = 0;
        dp[1] = nums[0];
        int max = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (i == 1) {
                dp[i + 1] = nums[i] + dp[i - 1];
            } else {
                dp[i + 1] = nums[i] + Math.max(dp[i - 1], dp[i - 2]);
            }
            max = Math.max(dp[i + 1], max);
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println(new Rob().rob(new int[]{1, 2, 3, 1}));
        System.out.println(new Rob().rob(new int[]{2, 7, 9, 3, 1}));
        System.out.println(new Rob().rob(new int[]{2, 1, 1, 2}));
    }


}
