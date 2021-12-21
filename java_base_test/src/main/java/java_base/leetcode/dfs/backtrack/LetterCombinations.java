package java_base.leetcode.dfs.backtrack;

import java.util.*;

/**
 * @author Kled
 * @date 2021/12/1 7:31 下午
 */
public class LetterCombinations {
    List<String> rets = new ArrayList<>();
    Map<Character, List<Character>> dict = new HashMap<Character, List<Character>>() {
        {
            put('2', Arrays.asList('a', 'b', 'c'));
            put('3', Arrays.asList('d', 'e', 'f'));
            put('4', Arrays.asList('g', 'h', 'i'));
            put('5', Arrays.asList('j', 'k', 'l'));
            put('6', Arrays.asList('m', 'n', 'o'));
            put('7', Arrays.asList('p', 'q', 'r', 's'));
            put('8', Arrays.asList('t', 'u', 'v'));
            put('9', Arrays.asList('w', 'x', 'y', 'z'));
        }
    };

    public List<String> letterCombinations(String digits) {
        if (digits.isEmpty()) {
            return rets;
        }
        backtrack(digits.toCharArray(), new StringBuilder());
        return rets;
    }

    public void backtrack(char[] sources, StringBuilder ret) {
        if (ret.length() == sources.length) {
            rets.add(ret.toString());
            return;
        }

//        for (int i = ret.length(); i == ret.length(); i++) {
//            char num = sources[i];
//            List<Character> characters = dict.get(num);
//            for (Character character : characters) {
//                ret.append(character);
//                backtrack(sources, ret);
//                ret.deleteCharAt(ret.length() - 1);
//            }
//        }

        char num = sources[ret.length()];
        List<Character> characters = dict.get(num);
        for (Character character : characters) {
            ret.append(character);
            backtrack(sources, ret);
            ret.deleteCharAt(ret.length() - 1);
        }
    }

    public static void main(String[] args) {
        System.out.println(new LetterCombinations().letterCombinations("22"));
    }
}
