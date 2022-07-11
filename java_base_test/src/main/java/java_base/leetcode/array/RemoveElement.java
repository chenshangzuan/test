
package java_base.leetcode.array;

/**
 * @author Kled
 * @date 2022/1/27 5:41 PM
 */
public class RemoveElement {
    public int removeElement(int[] nums, int val) {
        int p1 = 0;
        int p2 = nums.length - 1;
        int valCount = 0;
        while (p1 <= p2) {
            if (nums[p2] == val) {
                valCount++;
                p2--;
                continue;
            }
            if (nums[p1] == val) {
                int tmp = nums[p2];
                nums[p2] = nums[p1];
                nums[p1] = tmp;
            }
            p1++;
        }
        return nums.length - valCount;
    }

    public static void main(String[] args) {
        new RemoveElement().removeElement(new int[]{3,2,2,3}, 3);
    }
}
