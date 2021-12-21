package java_base.leetcode;

/**
 * @author Kled
 * @date 2021/12/10 5:05 下午
 */
public class LengthOfLastWord {
    public int lengthOfLastWord(String s) {
        String[] split = s.split(" +");
        return split[split.length - 1].length();
    }

    public int lengthOfLastWord2(String s) {
        int lengthOfLastWord = 0;
        boolean findLastWord = false;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) != ' ' && !findLastWord) {
                findLastWord = true;
            }
            if (s.charAt(i) == ' ' && findLastWord) {
                break;
            }
            if(findLastWord){
                lengthOfLastWord++;
            }
        }
        return lengthOfLastWord;
    }


    public static void main(String[] args) {
//        System.out.println("121 ".split(" +").length);
        System.out.println(new LengthOfLastWord().lengthOfLastWord("   fly me   to   the moon  "));
    }
}
