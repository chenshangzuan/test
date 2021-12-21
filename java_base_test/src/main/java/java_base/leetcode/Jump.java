package java_base.leetcode;

/**
 * @author Kled
 * @date 2021/11/14 9:08 下午
 */
public class Jump {
    public boolean canJump(int[] nums) {
        if(nums.length == 1){
            return true;
        }
        int end = nums[0];
        int oneStepMaxPosition = 0;
        for (int i = 0; i < nums.length; i++) {
            oneStepMaxPosition = Math.max(oneStepMaxPosition, i + nums[i]);
            if(i == end){
                end = oneStepMaxPosition;
                if(i != nums.length -1 && nums[i] == 0 && oneStepMaxPosition == i){
                    return false;
                }
            }
        }
        return oneStepMaxPosition >= nums.length - 1;
    }

    //最优解
    public boolean canJump2(int[] nums) {
        int n = nums.length;
        int rightmost = 0;
        for (int i = 0; i < n; ++i) {
            if (i <= rightmost) {
                rightmost = Math.max(rightmost, i + nums[i]);
                if (rightmost >= n - 1) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
//        System.out.println(new Jump().canJump(new int[]{3,2,1,0,4}));
//        System.out.println(new Jump().canJump(new int[]{2,5,0,0}));
        System.out.println(new Jump().canJump(new int[]{1,1,0,1}));
    }
}
