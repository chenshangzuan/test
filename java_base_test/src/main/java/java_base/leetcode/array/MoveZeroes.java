package java_base.leetcode.array;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author Kled
 * @date 2021/12/17 10:19 AM
 */
public class MoveZeroes {
    public void moveZeroes(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            for (int j = i; j > 0; j--) {
                if(nums[j - 1] != 0){
                    break;
                }
                if (nums[j - 1] == 0 && nums[j] != 0) {
                    nums[j - 1] = nums[j];
                    nums[j] = 0;
                }
            }
        }
    }

    public void moveZeroes2(int[] nums) {
        int n = nums.length, left = 0, right = 0;
        while (right < n) {
            if (nums[right] != 0) {
                int temp = nums[left];
                nums[left] = nums[right];
                nums[right] = temp;
                left++;
            }
            right++;
        }
    }

    public static void main(String[] args) {
        int[] ints = {0,1,0,3,12};
        new MoveZeroes().moveZeroes2(ints);
        System.out.println(Arrays.stream(ints).mapToObj(String::valueOf).collect(Collectors.toList()));
    }
}
