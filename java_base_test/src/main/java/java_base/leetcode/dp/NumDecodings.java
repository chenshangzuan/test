package java_base.leetcode.dp;

/**
 * @author Kled
 * @date 2021/11/22 6:07 下午
 */
public class NumDecodings {
    public int numDecodings(String s) {
        if (s.startsWith("0")) {
            return 0;
        }
        //可使用滚动数组
        int[] dp = new int[s.length() + 1];
        char charZero = '0';
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i <= s.length(); i++) {
            int preDp1 = s.charAt(i - 1) == charZero ? 0 : dp[i - 1];
            int preDp2 = 0;
            if (s.charAt(i - 2) != charZero) {
                //Substring比较耗时
                int code = Integer.parseInt(s.substring(i - 2, i));
                if (code >= 1 && code <= 26) {
                    preDp2 = dp[i - 2];
                }
            }
            dp[i] = preDp1 + preDp2;
        }
        return dp[dp.length - 1];
    }

    public int numDecodings2(String s) {
        if (s.startsWith("0")) {
            return 0;
        }
        //可使用滚动数组
        int[] dp = new int[s.length() + 1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i <= s.length(); i++) {
            int preDp1 = s.charAt(i - 1) == '0' ? 0 : dp[i - 1];
            int preDp2 = 0;
            if (s.charAt(i - 2) == '1' || (s.charAt(i - 2) == '2' && s.charAt(i - 1) <= '6')) {
                preDp2 = dp[i - 2];
            }
            dp[i] = preDp1 + preDp2;
        }
        return dp[dp.length - 1];
    }

    public static void main(String[] args) {
        System.out.println(new NumDecodings().numDecodings2("10"));
    }
}
