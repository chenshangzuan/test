package java_base.leetcode.dp;

/**
 * @author Kled
 * @date 2021/12/9 5:56 下午
 */
public class BinaryTreeNum {
    public int numTrees(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        for (int i = 1; i <= n; i++) {
            dp[i] = 0;
            for (int j = 1; j <= i; j++) {
                dp[i] += dp[j-1] * dp[i-j];
            }
        }
        return dp[n];
    }

    public static void main(String[] args) {
        System.out.println(new BinaryTreeNum().numTrees(3));
    }
}
