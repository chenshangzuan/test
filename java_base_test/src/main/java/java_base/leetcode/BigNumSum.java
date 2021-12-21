package java_base.leetcode;

/**
 * @author Kled
 * @date 2021/12/21 4:07 PM
 */
public class BigNumSum {
    public String bigNumSum(String num1, String num2) {
        if (num1.isEmpty() || num2.isEmpty()) {
            return "";
        }
        char[] num1Chars = num1.toCharArray();
        char[] num2Chars = num2.toCharArray();
        char[] bigNumChars = num1Chars.length >= num2Chars.length ? num1Chars : num2Chars;
        char[] smallNumChars = num1Chars.length >= num2Chars.length ? num2Chars : num1Chars;

        int bigNumLen = bigNumChars.length;
        int smallNumLen = smallNumChars.length;
        StringBuilder ans = new StringBuilder();
        int fullTag = 0;
        for (int i = 0; i < bigNumLen; i++) {
            char smallNumChar = i > smallNumLen - 1 ? '0' : smallNumChars[smallNumLen - 1 -i];
            char bigNumChar = bigNumChars[bigNumLen - 1 -i];

            int num = smallNumChar + bigNumChar - 2 * '0' + fullTag;
            fullTag = num / 10;
            ans.append(num % 10);
        }
        if(fullTag == 1){
            ans.append("1");
        }
        return ans.reverse().toString();
    }

    public static void main(String[] args) {
        System.out.println(new BigNumSum().bigNumSum("426709752318", "95481253129"));
    }
}
