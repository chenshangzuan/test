package java_base.leetcode;



import java.util.*;

/**
 * @author Kled
 * @date 2021/11/9 5:18 下午
 * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
 */
public class LengthOfLongestSubstring {
    public int lengthOfLongestSubstring(String s) {
        if(s == null || s.isEmpty()){
            return 0;
        }
        int maxLength = 1;
        HashSet<Character> charSet = new HashSet<>();
        char[] chars = s.toCharArray();
        for(int i=0; i< chars.length - 1; i++){
            boolean b = false;
            for (int j = i; j < chars.length; j++) {
                Character c = chars[j];
                if(charSet.contains(c)){
                    maxLength = Math.max(maxLength, j - i);
                    break;
                }else {
                    charSet.add(c);
                }
                if(j == chars.length - 1){
                    //不出现字符重复时，即遍历到最后一个字符
                    maxLength = Math.max(maxLength, chars.length - i);
                    b = true;
                }
            }
            if(b){
                break;
            }
            charSet.clear();
        }

        return maxLength;

        //优解
//        HashMap<Character, Integer> map = new HashMap<>();
//        int max = 0, start = 0;
//        for (int end = 0; end < s.length(); end++) {
//            char ch = s.charAt(end);
//            if (map.containsKey(ch)){
//                start = Math.max(map.get(ch)+1,start);
//            }
//            max = Math.max(max,end - start + 1);
//            map.put(ch,end);
//        }
//        return max;
    }

    public static void main(String[] args) {
        System.out.println(new LengthOfLongestSubstring().lengthOfLongestSubstring("abcdafghemnca"));
    }
}
