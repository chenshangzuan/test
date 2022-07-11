package java_base.leetcode.tp;

/**
 * @author Kled
 * @date 2022/4/19 10:46 PM
 */
public class RemoveDuplicates {
    public int removeDuplicates(int[] nums) {
        int p1=0;
        for(int i=1; i<nums.length;i++){
            if(nums[i] != nums[i-1]){
                nums[++p1] = nums[i];
            }
        }
        return p1 + 1;
    }
}
