package java_base.leetcode.dp;

/**
 * @author Kled
 * @date 2021/12/9 4:25 下午
 */
public class CoinChange {

    //动态规划
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount+1];
        dp[0] = 0;
        for (int i = 1; i <= amount; i++) {
            dp[i] = Integer.MAX_VALUE;
            for (int j = 0; j < coins.length; j++) {
                if(i - coins[j] >= 0 && dp[i - coins[j]] != -1){
                    dp[i] = Math.min(dp[i - coins[j]] + 1, dp[i]);
                }
            }
            dp[i] = dp[i] == Integer.MAX_VALUE ? -1 : dp[i];
        }
        return dp[amount];
    }


    public static void main(String[] args) {
        System.out.println(new CoinChange().coinChange(new int[]{1,2,5}, 11));
        System.out.println(new CoinChange().coinChange(new int[]{1}, 0));
    }
}
