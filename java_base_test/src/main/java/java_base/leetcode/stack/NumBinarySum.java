package java_base.leetcode.stack;

/**
 * @author Kled
 * @date 2021/12/21 5:29 PM
 */
public class NumBinarySum {
    //a + b = a ^ b + (a & b) << 1
    public int sum(int num1,int num2) {
        int tmp = 0;
        while(num1 != 0){
            tmp = num1 ^ num2;
            num1 = (num1 & num2) << 1;
            num2 = tmp;
        }
        return num2;
    }

    public static void main(String[] args) {
        System.out.println((2 ^ 3) + ((2 & 3) << 1));
    }
}
