package java_base.leetcode;

/**
 * @author Kled
 * @date 2021/11/23 6:13 下午
 */
public class WaterMaxArea {
    public int maxArea(int[] height) {
        int max = 0;
        for (int i = 1; i < height.length; i++) {
            for (int j = 0; j < i; j++) {
                max = Math.max(Math.min(height[i], height[j]) * (i - j), max);
            }
        }
        return max;
    }

    //双指针法
    public int maxArea2(int[] height) {
        int i = 0;
        int j = height.length - 1;
        int max = Math.min(height[i], height[j]) * (j - i);
        while (i < j) {
            if (height[i] <= height[j]){
                i++;
            }else {
                j--;
            }
            max = Math.max(Math.min(height[i], height[j]) * (j - i), max);
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println(new WaterMaxArea().maxArea2(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}));
        System.out.println(new WaterMaxArea().maxArea2(new int[]{1, 1}));
    }
}
