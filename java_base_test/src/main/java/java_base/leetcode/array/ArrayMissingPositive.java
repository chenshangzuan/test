package java_base.leetcode.array;

import kotlin.text.MatchGroup;

import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kled
 * @date 2021/12/22 3:09 PM
 */
public class ArrayMissingPositive {
    //NegativeArraySizeException
    public int firstMissingPositive(int[] nums) {
        int max = -1;
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if (num >= 1) {
                max = Math.max(max, num);
            }
        }

        int[] numRecorder = new int[max + 1];
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if (num >= 1) {
                numRecorder[num] = numRecorder[num] + 1;
            }
        }

        for (int i = 1; i < numRecorder.length; i++) {
            if (numRecorder[i] == 0) {
                return i;
            }
            if (i == numRecorder.length - 1) {
                return numRecorder.length;
            }
        }
        return 1;
    }

    public int firstMissingPositive2(int[] nums) {
        int max = -1;
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if (num >= 1) {
                max = Math.max(max, num);
            }
        }

        int[] bitMap = new int[max / 32 + 1];
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if (num >= 1) {
                int m = num / 32;
                int n = num % 32;
                bitMap[m] |= 1 << (n - 1);
            }
        }

        for (int i = 0; i < bitMap.length; i++) {
            int mask = bitMap[i];
            for (int j = 0; j < 32; j++) {
                if ((mask & (1 << j)) == 0) {
                    return i * 32 + j + 1;
                }
            }
        }
        return 1;
    }

    //超内存
    public int firstMissingPositive3(int[] nums) {
        BitSet bitSet = new BitSet();
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if (num >= 1 && num <= nums.length) {
                bitSet.set(num);
            }
        }

        return bitSet.nextClearBit(1);
    }

    //参考答案
    public int firstMissingPositive4(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; ++i) {
            while (nums[i] > 0 && nums[i] <= n && nums[nums[i] - 1] != nums[i]) {
                int temp = nums[nums[i] - 1];
                nums[nums[i] - 1] = nums[i];
                nums[i] = temp;
            }
        }
        for (int i = 0; i < n; ++i) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }
        return n + 1;
    }


    public static void main(String[] args) {
        System.out.println(new ArrayMissingPositive().firstMissingPositive3(new int[]{1, 2, 0}));
    }

//    public void adjustDown(int[] nums, int parentPos, int len) {
//        int parent = nums[parentPos];
//        int childPos = 2 + parentPos + 1;
//        while (childPos < len) {
//            if (childPos + 1 < len && nums[childPos] > nums[childPos + 1]) {
//                childPos++;
//            }
//            if (nums[childPos] > parent) break;
//
//            nums[parentPos] = nums[childPos];
//            childPos = 2 + parentPos + 1;
//        }
//        nums[childPos] = parent;
//    }
}
