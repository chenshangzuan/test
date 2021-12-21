package java_base.leetcode.bit;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kled
 * @date 2021/12/16 10:24 AM
 */
public class SingleNumber {

    public int singleNumber(int[] nums) {
        Map<Integer, Integer> numCounter = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            Integer count = numCounter.get(num);
            if (count == null) {
                numCounter.put(num, 1);
            } else {
                numCounter.put(num, ++count);
            }
        }

        for (Map.Entry<Integer, Integer> e : numCounter.entrySet()) {
            if (e.getValue() == 1) {
                return e.getKey();
            }
        }
        return -1;
    }

    //位运算，异或
    public int singleNumber2(int[] nums) {
        return Arrays.stream(nums).reduce((a, b) -> a ^ b).getAsInt();
    }
}
