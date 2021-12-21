package java_base.leetcode.dp;

/**
 * @author Kled
 * @date 2021/11/23 7:33 下午
 */
public class MaximalSquare {
    public int maximalSquare(char[][] matrix) {
        int[][] dp = new int[matrix.length][matrix[0].length];
        int max = 0;
        for (int i = 0; i < matrix.length; i++) {
            dp[i][0] = matrix[i][0] == '1' ? 1 : 0;
            max = Math.max(max, dp[i][0]);
        }
        for (int i = 0; i < matrix[0].length; i++) {
            dp[0][i] = matrix[0][i] == '1' ? 1 : 0;
            max = Math.max(max, dp[0][i]);
        }

        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[0].length; j++) {
                dp[i][j] = matrix[i][j] == '1' ? 1 : 0;;
                if (dp[i][j] == 0) {
                    continue;
                }
                for (int k = 1; k <= dp[i - 1][j - 1]; k++) {
                    if (matrix[i - k][j] == '1' && matrix[i][j - k] == '1') {
                        dp[i][j] = k + 1;
                    } else {
                        break;
                    }
                }
                max = Math.max(max, dp[i][j]);
            }
        }
        return max * max;
    }

    public static void main(String[] args) {
//        System.out.println(new MaximalSquare().maximalSquare(new char[][]{{'1','0','1','0','0'},
//                {'1','0','1','1','1'},
//                {'1','1','1','1','1'},
//                {'1','0','0','1','0'}}));

        System.out.println(new MaximalSquare().maximalSquare(new char[][]{{'1','1','1','1','0'},
                {'1','1','1','1','0'},
                {'1','1','1','1','1'},
                {'1','1','1','1','1'},
                {'0','0','1','1','1'}}));
    }
}
