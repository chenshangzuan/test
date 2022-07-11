package java_base.leetcode.dp;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author Kled
 * @date 2021/11/19 4:52 下午
 */
public class WordBreak {
    //贪心算法，结果不准确
//    public boolean wordBreak(String s, List<String> wordDict) {
//        while (!s.isEmpty()){
//            for (int i = 0; i < wordDict.size(); i++) {
//                String word = wordDict.get(i);
//                if(s.indexOf(word) == 0){
//                    s = s.substring(word.length());
//                    break;
//                }
//                if(i == wordDict.size()-1){
//                    return false;
//                }
//            }
//        }
//        return true;
//    }

    //动态规划
    public boolean wordBreak(String s, List<String> wordDict) {
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            for (String word : wordDict) {
                if(dp[i]){
                    //如果已经匹配到，直接判断下一位
                    break;
                }
//                if (word.endsWith(String.valueOf(s.charAt(i - 1))) && i - word.length() >= 0) {
                if (s.substring(0, i).endsWith(word)) {
                    dp[i] = dp[i] | dp[i - word.length()];
                }
            }
        }
        return dp[s.length()];
    }


    public static void main(String[] args) {
        System.out.println(new WordBreak().wordBreak("leetcode", Lists.newArrayList("leet", "code")));
        System.out.println(new WordBreak().wordBreak("applepenapple", Lists.newArrayList("apple", "pen")));
        System.out.println(new WordBreak().wordBreak("ccbb", Lists.newArrayList("bc", "cb")));
    }
}
