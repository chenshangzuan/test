package java_base.leetcode.dp;

/**
 * @author Kled
 * @date 2021/11/24 5:18 下午
 */
public class LengthOfLIS {
    //最长连续自增子序列
//    public int lengthOfLIS(int[] nums) {
//        int len = nums.length;
//        //dp[x][y]代表数组下标x,y之间是否为增长序列
//        boolean[][] dp = new boolean[len][len];
//        for (int i = 0; i < len; i++) {
//            dp[i][i] = true;
//        }
//
//        int maxLengthOfLIS = 1;
//        for (int i = 1; i < len; i++) {
//            for (int j = i + 1; j < len; j++) {
//                dp[i][j] = dp[i][j - 1] && nums[j] > nums[j - 1];
//                if (dp[i][j]) {
//                    maxLengthOfLIS = Math.max(maxLengthOfLIS, j - i + 1);
//                }
//            }
//        }
//        return maxLengthOfLIS;
//    }

    public int lengthOfLIS(int[] nums) {
        int len = nums.length;
        int[] dp = new int[len];
        dp[0] = 1;
        int max = 1;
        for (int i = 1; i < len; i++) {
            int maxSub = 0;
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    maxSub = Math.max(maxSub, dp[j]);
                }
            }
            dp[i] = maxSub + 1;
            max = Math.max(max, dp[i]);
        }
        return max;
    }
}
