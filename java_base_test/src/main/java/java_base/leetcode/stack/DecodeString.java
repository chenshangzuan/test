package java_base.leetcode.stack;

import com.sun.xml.internal.xsom.impl.parser.SubstGroupBaseTypeRef;

import java.util.Stack;

/**
 * @author Kled
 * @date 2021/12/16 5:58 PM
 */
public class DecodeString {
    public String decodeString(String s) {
        StringBuilder sb = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        boolean decoding = false;
        StringBuilder decodeNum = new StringBuilder();
        StringBuilder codeSubStr = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if(c >= '0' && c <= '9' && !decoding){
                decodeNum.append(c);
            }else if(c == '['){
                if(decoding){
                    codeSubStr.append(c);
                }
                decoding = true;
                stack.push(c);
            }else if(c == ']'){
                stack.pop();
                //解析子串
                if(decoding && stack.isEmpty()){
                    String decodeSubStr = decodeString(codeSubStr.toString());
                    for (int j = 1; j <= Integer.parseInt(decodeNum.toString()); j++) {
                        sb.append(decodeSubStr);
                    }
                    decodeNum = new StringBuilder();
                    codeSubStr = new StringBuilder();
                    decoding = false;
                }
                if(decoding){
                    codeSubStr.append(c);
                }
            }else if(!decoding){
                sb.append(c);
            }else {
                codeSubStr.append(c);
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
//        System.out.println(new DecodeString().decodeString("3[a]2[bc]"));
//        System.out.println(new DecodeString().decodeString("3[a2[c]]"));
//        System.out.println(new DecodeString().decodeString("abc3[cd]xyz"));
        System.out.println(new DecodeString().decodeString("10[cd]"));
    }
}
