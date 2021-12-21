package java_base.leetcode;

/**
 * @author Kled
 * @date 2021/11/14 7:02 下午
 */
public class Jump2 {
    //穷举法，超时
    int minJump = Integer.MAX_VALUE;
    public int jump(int[] nums) {
        doJump(0, nums, 1);
        return minJump;
    }

    private void doJump(int index, int[] nums, int jumpStep) {
        if(nums.length == 1){
            minJump = 0;
            return;
        }

        int num = nums[index];
        if(num == 0 && index != 0){
            return;
        }

        if(jumpStep >= minJump){
            return;
        }

        if (index + num >= nums.length - 1) {
            minJump = jumpStep;
            System.out.println(minJump);
            return;
        }

        while (num > 0){
            System.out.println(index + "," + num + "," + jumpStep);
            doJump(index + num, nums, jumpStep + 1);
            num--;
        }
    }

    //最优解
    public int jump2(int[] nums) {
        int length = nums.length;
        int end = 0;
        int maxPosition = 0;
        int steps = 0;
        for (int i = 0; i < length - 1; i++) {
            maxPosition = Math.max(maxPosition, i + nums[i]);
            if (i == end) {
                end = maxPosition;
                steps++;
            }
        }
        return steps;
    }


    public static void main(String[] args) {
//        System.out.println(new Jump().jump(new int[]{2,3,1,1,4}));
//        System.out.println(new Jump().jump(new int[]{2,3,0,1,4}));
//        System.out.println(new Jump().jump(new int[]{0}));
//        System.out.println(new Jump().jump(new int[]{5,6,4,4,6,9,4,4,7,4,4,8,2,6,8,1,5,9,6,5,2,7,9,7,9,6,9,4,1,6,8,8,4,4,2,0,3,8,5}));
        System.out.println(new Jump2().jump2(new int[]{5,8,1,8,9,8,7,1,7,5,8,6,5,4,7,3,9,9,0,6,6,3,4,8,0,5,8,9,5,3,7,2,1,8,2,3,8,9,4,7,6,2,5,2,8,2,7,9,3,7,6,9,2,0,8,2,7,8,4,4,1,1,6,4,1,0,7,2,0,3,9,8,7,7,0,6,9,9,7,3,6,3,4,8,6,4,3,3,2,7,8,5,8,6,0}));
    }

}
