package java_base.leetcode.dp;

/**
 * @author Kled
 * @date 2021/11/15 7:59 下午
 */
public class StockMaxProfit {
    public int maxProfit(int[] prices) {
        if (prices.length == 1) {
            return 0;
        }

        int[] profits = new int[prices.length];
        for (int i = 0; i < prices.length; i++) {
            if (i == 0) {
                profits[i] = 0;
            } else {
                profits[i] = prices[i] - prices[i - 1];

            }
        }

        //dp[i]代表第i天最大的利润
        int[] dp = new int[prices.length];
        dp[0] = 0;
        int max = 0;
        for (int i = 1; i < prices.length; i++) {
            if (dp[i - 1] < 0 && profits[i] >= 0) {
                dp[i] = profits[i];
            }else {
                dp[i] = profits[i] + dp[i - 1];
            }
            max = Math.max(max, dp[i]);
        }

        return Math.max(0, max);
    }

    public int maxProfit2(int[] prices) {
        if (prices.length == 1) {
            return 0;
        }

        //dp[i]代表第i天为止最大的利润
        int[] dp = new int[prices.length];
        dp[0] = 0;
        int max = 0;
        for (int i = 1; i < prices.length; i++) {
            int profit = prices[i] - prices[i-1];
            if (dp[i - 1] < 0 && profit >= 0) {
                dp[i] = profit;
            }else {
                dp[i] = profit + dp[i - 1];
            }
            max = Math.max(max, dp[i]);
        }

        return Math.max(0, max);
    }

    //最优解
    public int maxProfit3(int prices[]) {
        int minprice = Integer.MAX_VALUE;
        int maxprofit = 0;
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] < minprice) {
                //在跌
                minprice = prices[i];
            } else if (prices[i] - minprice > maxprofit) {
                maxprofit = prices[i] - minprice;
            }
        }
        return maxprofit;
    }

}
