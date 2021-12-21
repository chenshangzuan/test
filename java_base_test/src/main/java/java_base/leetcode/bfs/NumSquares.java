package java_base.leetcode.bfs;

/**
 * @author Kled
 * @date 2021/12/6 5:39 下午
 */
public class NumSquares {
    int minNumSquares = Integer.MAX_VALUE;

    public int numSquares(int n) {
        bfs(n, 0);
        return minNumSquares;
    }

    public void bfs(int n, int numSquares) {
        if (numSquares > minNumSquares) {
            return;
        }
        if (n < 0) {
            return;
        }
        if (n == 0) {
            minNumSquares = numSquares;
            return;
        }
        for (int i = (int) Math.sqrt(n); i > 0; i--) {
            bfs((int) (n - Math.pow(i, 2)), numSquares + 1);
        }
    }

    //动态规划解法
    public int numSquares2(int n) {
        int[] f = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            int minn = Integer.MAX_VALUE;
            for (int j = 1; j * j <= i; j++) {
                minn = Math.min(minn, f[i - j * j]);
            }
            f[i] = minn + 1;
        }
        return f[n];
    }

    public static void main(String[] args) {
        System.out.println(new NumSquares().numSquares(12));
//        System.out.println((int) Math.sqrt(12));
    }
}
