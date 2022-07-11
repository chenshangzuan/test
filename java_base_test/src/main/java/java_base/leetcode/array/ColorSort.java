package java_base.leetcode.array;

/**
 * @author Kled
 * @date 2021/12/23 7:42 PM
 */
public class ColorSort {
    public void sortColors(int[] nums) {
        int redIndex = 0;
        int blueIndex = nums.length - 1;
        for (int i = 0; i <= blueIndex; i++) {
            while (nums[i] == 0 || nums[i] == 2){
                int num = nums[i];
                if (num == 0) {
                    if(redIndex == i){
                        redIndex++;
                        break;
                    }
                    nums[i] = nums[redIndex];
                    nums[redIndex] = num;
                    redIndex++;
                }
                if (num == 2) {
                    if(blueIndex == i){
                        blueIndex--;
                        break;
                    }
                    nums[i] = nums[blueIndex];
                    nums[blueIndex] = num;
                    blueIndex--;
                }
            }
        }
    }

    //参考答案1
    public void sortColors2(int[] nums) {
        int n = nums.length;
        int ptr = 0;
        for (int i = 0; i < n; ++i) {
            if (nums[i] == 0) {
                int temp = nums[i];
                nums[i] = nums[ptr];
                nums[ptr] = temp;
                ++ptr;
            }
        }
        for (int i = ptr; i < n; ++i) {
            if (nums[i] == 1) {
                int temp = nums[i];
                nums[i] = nums[ptr];
                nums[ptr] = temp;
                ++ptr;
            }
        }
    }

    //参考答案2: 双指针
    public void sortColors3(int[] nums) {
        int n = nums.length;
        int p0 = 0, p2 = n - 1;
        for (int i = 0; i <= p2; ++i) {
            while (i <= p2 && nums[i] == 2) {
                int temp = nums[i];
                nums[i] = nums[p2];
                nums[p2] = temp;
                --p2;
            }
            if (nums[i] == 0) {
                int temp = nums[i];
                nums[i] = nums[p0];
                nums[p0] = temp;
                ++p0;
            }
        }
    }

    public static void main(String[] args) {
        new ColorSort().sortColors(new int[]{2,0,1});
    }
}
