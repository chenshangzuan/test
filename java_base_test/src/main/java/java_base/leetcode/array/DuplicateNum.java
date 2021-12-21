package java_base.leetcode.array;

/**
 * @author Kled
 * @date 2021/12/17 7:41 PM
 */
public class DuplicateNum {
    //将数组看成一个图，且图成环的入口，即是重复的数字，利用快慢指针
    public int findDuplicate(int[] nums) {
        int slow = 0, fast = 0;
        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while (slow != fast);
        slow = 0;
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }
        return slow;
    }

    //bit map
    public int findDuplicate2(int[] nums) {
        int[] marks = new int[nums.length / 32 + 1];
        for (int i = 0; i < nums.length; i++) {
            int m = nums[i] / 32;
            int n = nums[i] % 32;
            if ((marks[m] & 1L << n) != 0) {
                return nums[i];
            } else {
                marks[m] |= 1L << n;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int duplicate2 = new DuplicateNum().findDuplicate2(new int[]{34, 7, 47, 5, 8, 72, 18, 94, 49, 21, 65, 22, 81, 30, 79, 37, 27, 38, 12, 53, 6, 57, 4, 59, 60, 23, 74, 25, 83, 99, 2, 3, 9, 82, 48, 73, 28, 18, 56, 10, 84, 33, 88, 67, 61, 18, 18, 18, 13, 77, 97, 76, 95, 90, 17, 31, 52, 36, 91, 64, 29, 18, 18, 43, 18, 89, 86, 51, 18, 66, 78, 70, 45, 55, 98, 18, 71, 80, 26, 75, 41, 58, 85, 20, 18, 96, 32, 63, 1, 54, 14, 92, 35, 16, 11, 15, 39, 40, 93, 62});
        System.out.println(duplicate2);
    }
}
