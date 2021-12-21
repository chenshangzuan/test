package java_base.leetcode.dfs;

import java.util.*;

/**
 * @author Kled
 * @date 2021/11/9 7:49 下午
 */
public class ValidBracket {
    public boolean isValid(String s) {
        Map<Character, Character> map = new HashMap<>();
        map.put('(', ')');
        map.put('[', ']');
        map.put('{', '}');
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (map.containsKey(c)){
                //左括号对应的右括号入栈
                stack.push(map.get(c));
            }else {
                if (stack.isEmpty()){
                    return false;
                }
                Character right = stack.pop();
                if(!right.equals(c)){
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }
}
