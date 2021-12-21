package java_base.leetcode;

import java.util.Arrays;

/**
 * @author Kled
 * @date 2021/12/2 6:26 下午
 */
public class NextPermutation {
//    public void nextPermutation(int[] nums) {
//        boolean b = false;
//        for (int i = nums.length - 1; i >= 0; i--) {
//            if (nums[i] > nums[i - 1]) {
//                int temp = nums[i - 1];
//                nums[i - 1] = nums[i];
//                nums[i] = temp;
//                b = true;
//                break;
//            }
//        }
//
//        if (!b) {
//            Arrays.sort(nums);
//        }
//    }

    //官方思路
    //1.我们需要将一个左边的「较小数」与一个右边的「较大数」交换，以能够让当前排列变大，从而得到下一个排列。
    //2.同时我们要让这个「较小数」尽量靠右，而「较大数」尽可能小。当交换完成后，「较大数」右边的数需要按照升序重新排列。这样可以在保证新排列大于原来排列的情况下，使变大的幅度尽可能小。
    public void nextPermutation(int[] nums) {
        int len = nums.length;
        int smallNumIndex = -1;
        for (int i = len - 1; i > 0; i--) {
            if (nums[i] > nums[i - 1]) {
                smallNumIndex = i - 1;
                break;
            }
        }
        if (smallNumIndex == -1) {
            Arrays.sort(nums);
            return;
        }

        int bigNumIndex = 0;
        for (int i = len - 1; i > smallNumIndex; i--) {
            if (nums[i] > nums[smallNumIndex]) {
                bigNumIndex = i;
                swap(nums, smallNumIndex, bigNumIndex);
                break;
            }
        }

        for (int i = smallNumIndex + 1; i < len; i++) {
            for (int j = len - 1; j >= i + 1; j--) {
                if (nums[j] < nums[j - 1]) {
                    swap(nums, j, j - 1);
                }
            }
        }

    }

    public void swap(int[] nums, int index1, int index2) {
        int temp = nums[index1];
        nums[index1] = nums[index2];
        nums[index2] = temp;
    }

    public static void main(String[] args) {
        new NextPermutation().nextPermutation(new int[]{5, 4, 7, 5, 3, 2});
    }
}
