package java_base.leetcode;

/**
 * @author Kled
 * @date 2021/12/21 4:07 PM
 */
public class BigNumMul {
    BigNumSum bigNumSumUtil = new BigNumSum();
    public String bigNumMul(String num1, String num2) {
        if (num1.isEmpty() || num2.isEmpty()) {
            return "";
        }
        char[] num1Chars = num1.toCharArray();
        char[] num2Chars = num2.toCharArray();
        char[] bigNumChars = num1Chars.length >= num2Chars.length ? num1Chars : num2Chars;
        char[] smallNumChars = num1Chars.length >= num2Chars.length ? num2Chars : num1Chars;

        int bigNumLen = bigNumChars.length;
        int smallNumLen = smallNumChars.length;

        String ans = "0";
        for (int i = 0; i < smallNumLen; i++) {
            char smallNumChar = smallNumChars[smallNumLen - 1 -i];
            StringBuilder subAns = new StringBuilder();
            int fullTag = 0;
            for (int j = 0; j < bigNumLen; j++) {
                char bigChar = bigNumChars[bigNumLen - 1 -i];
                int num = (bigChar - '0') * (smallNumChar - '0') + fullTag;
                fullTag = num / 10;
                subAns.append(num % 10);
            }
            if(fullTag == 1){
                subAns.append("1");
            }
            StringBuilder reversedSubAns = subAns.reverse();
            for (int j = 0; j < i; j++) {
                reversedSubAns.append("0");
            }
            ans =  bigNumSumUtil.bigNumSum(ans, reversedSubAns.toString());
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(new BigNumMul().bigNumMul("11", "2"));
    }
}
