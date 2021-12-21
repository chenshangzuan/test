package java_base.leetcode;

/**
 * @author Kled
 * @date 2021/12/21 3:43 PM
 */
public class NumFactorialZero {
    //判断5整除的个数
    int f(int n) {
        int sum = 0;
        while (n != 0) {
            sum += n / 5;
            n = n / 5;
        }
        return sum;
    }

    public static void main(String[] args) {
        System.out.println(Long.MAX_VALUE);
    }
}
