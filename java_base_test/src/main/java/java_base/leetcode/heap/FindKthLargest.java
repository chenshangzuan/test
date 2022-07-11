package java_base.leetcode.heap;

/**
 * @author Kled
 * @date 2022/4/18 11:00 PM
 */
public class FindKthLargest {
    public int findKthLargest(int[] nums, int k) {
        int len = nums.length;
        if (k > len) {
            return -1;
        }
        for (int i = 0; i < k; i++) {
            int last = len - i;
            //从非叶子节点开始向下调整
            for (int j = last / 2 - 1; j >= 0; j--) {
                adjustDown(nums, j, last);
            }
            // System.out.print(nums[0]);
            int tmp = nums[last - 1];
            nums[last - 1] = nums[0];
            nums[0] = tmp;
        }

        return nums[len - k];
    }

    public void adjustDown(int[] nums, int parentPos, int len) {
        int parent = nums[parentPos];
        int childPos = 2 * parentPos + 1;
        while (childPos < len) {
            if (childPos + 1 < len && nums[childPos + 1] > nums[childPos]) {
                childPos++;
            }
            if (parent >= nums[childPos]) {
                break;
            }
            nums[parentPos] = nums[childPos];
            parentPos = childPos;
            childPos = 2 * parentPos + 1;
        }
        nums[parentPos] = parent;
    }
}
