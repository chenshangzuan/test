package java_base.leetcode.bit;

/**
 * @author Kled
 * @date 2021/12/23 5:41 PM
 */
public class HammingDistance {
    public int hammingDistance(int x, int y) {
        int z = x ^ y;
        int hammingDistance = 0;
        while (z != 0){
            hammingDistance++;
            z = z & (z - 1);
        }
        return hammingDistance;
    }
}
