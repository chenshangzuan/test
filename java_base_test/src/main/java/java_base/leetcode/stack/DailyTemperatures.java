package java_base.leetcode.stack;

import java.util.Stack;

/**
 * @author Kled
 * @date 2022/4/18 9:51 PM
 */
public class DailyTemperatures {
    public int[] dailyTemperatures(int[] temperatures) {
        // int[] ans = new int[temperatures.length];
        // for(int i= 0; i<temperatures.length;i++){
        //     for(int j=i+1;j<temperatures.length;j++){
        //         if(temperatures[j]>temperatures[i]){
        //             ans[i] = j-i;
        //             break;
        //         }
        //     }
        // }
        // return ans;

        int[] ans = new int[temperatures.length];
        Stack<Integer> s = new Stack<>();
        for(int i= 0; i<temperatures.length;i++){
            int temperature = temperatures[i];
            while(!s.isEmpty() && temperature > temperatures[s.peek()]){
                Integer index = s.pop();
                ans[index] = i - index;
            }
            s.push(i);
        }
        return ans;
    }
}
