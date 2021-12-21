package java_base.leetcode;

/**
 * @author Kled
 * @date 2021/12/21 2:57 PM
 */
public class NumBinaryCount {
    //计算数字二级制1的个数，(n - 1) & n 消除最右边的1
    public int NumberOf12(int n) {
        int count = 0;
        while (n != 0) {
            count++;
            n = (n - 1) & n;
        }
        return count;
    }

    //判断二是不是2次幂
    boolean judge(int n){
        return (n & (n - 1)) == 0;//
    }
}
