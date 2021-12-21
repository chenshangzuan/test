package java_base.leetcode.dp;

/**
 * @author Kled
 * @date 2021/11/12 4:49 下午
 */
public class MaxSubArray {
    //不需要获取子序列
    public static int maxSubArray(int[] nums) {
        if(nums.length <2){
            return nums[0];
        }
        int len = nums.length;
        int[][] bp = new int[len][len];
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < len; i++) {
            bp[i][i] = nums[i];
            max = Math.max(max, nums[i]);
        }

        for (int j = 0; j < len; j++) {
            for (int i = 0; i < j; i++) {
                bp[i][j] = bp[i][j - 1] + nums[j];
                max = Math.max(max, bp[i][j]);
            }
        }
        return max;
    }

    public static int maxSubArray2(int[] nums) {
        int len = nums.length;
        //初始值，状态dp[i]代表以num[i]结尾的子序列最大值
        int[] dp = new int[len];
        dp[0] = nums[0];
        int max = nums[0];
        for (int i = 1; i < len; i++) {
            dp[i] = Math.max(dp[i-1] + nums[i], nums[i]);
            max = Math.max(max, dp[i]);
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println(MaxSubArray.maxSubArray2(new int[]{-57,9,-72,-72,-62,45,-97,24,-39,35,-82,-4,-63,1,-93,42,44,1,-75,-25,-87,-16,9,-59,20,5,-95,-41,4,-30,47,46,78,52,74,93,-3,53,17,34,-34,34,-69,-21,-87,-86,-79,56,-9,-55,-69,3,5,16,21,-75,-79,2,-39,25,72,84,-52,27,36,98,20,-90,52,-85,44,94,25,51,-27,37,41,-6,-30,-68,15,-23,11,-79,93,-68,-78,90,11,-41,-8,-17,-56,17,86,56,15,7,66,-56,-2,-13,-62,-77,-62,-12,37,55,81,-93,86,-27,-39,-3,-30,-46,6,-8,-79,-83,50,-10,-24,70,-93,-38,27,-2,45,-7,42,-57,79,56,-57,93,-56,79,48,-98,62,11,-48,-77,84,21,-47,-10,-87,-49,-17,40,40,35,10,23,97,-63,-79,19,6,39,62,-38,-27,81,-68,-7,60,79,-28,-1,-33,23,22,-48,-79,51,18,-66,-98,-98,50,41,13,-63,-59,10,-49,-38,-70,56,77,68,95,-73,26,-73,20,-14,83,91,61,-50,-9,-40,1,11,-88,-80,21,89,97,-29,8,10,-15,48,97,35,86,-96,-9,64,48,-37,90,-26,-10,-13,36,-27,-45,-3,-1,45,34,77,-66,22,73,54,11,70,-97,-81,-43,-13,44,-69,-78,30,-66,-11,-29,58,52,-61,-68,-81,25,44,-32,57,-81,66,2,52,43,35,-26,16,-33,61,-37,-54,80,-3,32,24,27,30,-69,38,-81,2,-4,47,17,5,42,-58,-51,-90,98,-33,7}));
        System.out.println(MaxSubArray.maxSubArray2(new int[]{-2,1,-3,4,-1,2,1,-5,4}));
//        System.out.println(MaxSubArray.maxSubArray(new int[]{-1}));
//        System.out.println(MaxSubArray.maxSubArray(new int[]{0}));
        System.out.println(MaxSubArray.maxSubArray2(new int[]{1}));
    }
}
