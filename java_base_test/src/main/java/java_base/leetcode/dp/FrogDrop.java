package java_base.leetcode.dp;

/**
 * @author Kled
 * @date 2021/11/12 11:45 上午
 * 一只青蛙一次可以跳上1级台阶，也可以跳上2级。求该青蛙跳上一个n级的台阶总共有多少种跳法。
 */
public class FrogDrop {

    public static int frogDrop(int n){
        if(n <= 1){
            return 1;
        }
        //状态：dp[i]标识第i阶总共的跳法
        int[] dp = new int[n + 1];
        //初始化值
        dp[0] = 1;
        dp[1] = 1;

        for (int i = 2; i <= n; i++) {
            //状态转移方程
            //dp[i] = Math.max(dp[i-1], dp[i-2]) + 1;
            dp[i] = dp[i-1] + dp[i-2];
        }
        return dp[n];
    }

    public static void main(String[] args) {
        System.out.println(FrogDrop.frogDrop(1));
        System.out.println(FrogDrop.frogDrop(2));
        System.out.println(FrogDrop.frogDrop(3));
        System.out.println(FrogDrop.frogDrop(10));
    }
}
