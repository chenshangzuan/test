package java_base.leetcode.dp;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author Kled
 * @date 2021/11/12 6:17 下午
 */
public class CountBits {
    public static int[] countBits(int n) {
        int[] bp = new int[n + 1];
        bp[0] = 0;
        for (int i = 1; i <= n; i++) {
            if (i % 2 == 0) {
                //如果 i 为偶数，因为 i/2 本质上是i的二进制左移一位，低位补零，所以1的数量不变。
                bp[i] = bp[i / 2];
            } else {
                bp[i] = bp[i - 1] + 1;
            }
        }
        return bp;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.stream(CountBits.countBits(16)).mapToObj(String::valueOf).collect(Collectors.toList()));
    }

}
