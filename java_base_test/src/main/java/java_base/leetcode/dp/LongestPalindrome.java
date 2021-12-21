package java_base.leetcode.dp;

/**
 * @author Kled
 * @date 2021/11/11 5:28 下午
 */
public class LongestPalindrome {
    public String longestPalindrome(String s) {
        int maxPalindromeLength = 1;
        char[] chars = s.toCharArray();
        int length = s.length();
        int begin = 0;
        for (int i = 0; i < length - 1; i++) {
            for (int j = i + 1; j <length; j++) {
                boolean isPalindrome = isValid(chars, i, j);
                int subStrLength = j - i + 1;

                //截字符串比较耗时
//                String str = s.substring(i, j);
//                char[] chars = str.toCharArray();
//                for (int k = 0; k < chars.length / 2; k++) {
//                    if(chars[k] != chars[chars.length -1 -k]){
//                        isPalindrome = false;
//                        break;
//                    }
//                }
                if(isPalindrome && subStrLength > maxPalindromeLength){
                    maxPalindromeLength = subStrLength;
                    begin = i;
                }
            }
        }
        return s.substring(begin, begin + maxPalindromeLength);
    }

    private boolean isValid(char[] chars, int begin, int end){
        while (begin < end){
            if(chars[begin] != chars[end]){
                return false;
            }
            begin++;
            end--;
        }
        return true;
    }

    //使用动态规划求解
    public String longestPalindromeByBP(String s){
        char[] chars = s.toCharArray();
        int len = s.length();
        if(len < 2){
            return s;
        }
        //初始值
        boolean[][] bp = new boolean[len][len];
        for (int i = 0; i < len; i++) {
            bp[i][i] = true;
        }

        int maxLen = 1;
        int begin = 0;
        //回文字符串
        for (int j = 1; j < len; j++) {
            for (int i = 0; i < j; i++) {
                if(chars[i] != chars[j]){
                    bp[i][j] = false;
                }else {
                    if(j - 1 - (i + 1) + 1 < 2){
                        bp[i][j] = true;
                    }else {
                        bp[i][j] = bp[i + 1][j - 1];
                    }
                }
                if(bp[i][j] && j - i + 1 > maxLen){
                    maxLen = j - i + 1;
                    begin = i;
                }
            }
        }
        return s.substring(begin, begin + maxLen);
    }



    public static void main(String[] args) {
//        System.out.println(new LongestPalindrome().longestPalindrome("a"));
//        System.out.println(new LongestPalindrome().longestPalindrome("ac"));
//        System.out.println(new LongestPalindrome().longestPalindrome("bb"));
//        System.out.println(new LongestPalindrome().longestPalindrome("babad"));
//        System.out.println(new LongestPalindrome().longestPalindrome("cbbd"));
        System.out.println(new LongestPalindrome().longestPalindromeByBP("gphyvqruxjmwhonjjrgumxjhfyupajxbjgthzdvrdqmdouuukeaxhjumkmmhdglqrrohydrmbvtuwstgkobyzjjtdtjroqpyusfsbjlusekghtfbdctvgmqzeybnwzlhdnhwzptgkzmujfldoiejmvxnorvbiubfflygrkedyirienybosqzrkbpcfidvkkafftgzwrcitqizelhfsruwmtrgaocjcyxdkovtdennrkmxwpdsxpxuarhgusizmwakrmhdwcgvfljhzcskclgrvvbrkesojyhofwqiwhiupujmkcvlywjtmbncurxxmpdskupyvvweuhbsnanzfioirecfxvmgcpwrpmbhmkdtckhvbxnsbcifhqwjjczfokovpqyjmbywtpaqcfjowxnmtirdsfeujyogbzjnjcmqyzciwjqxxgrxblvqbutqittroqadqlsdzihngpfpjovbkpeveidjpfjktavvwurqrgqdomiibfgqxwybcyovysydxyyymmiuwovnevzsjisdwgkcbsookbarezbhnwyqthcvzyodbcwjptvigcphawzxouixhbpezzirbhvomqhxkfdbokblqmrhhioyqubpyqhjrnwhjxsrodtblqxkhezubprqftrqcyrzwywqrgockioqdmzuqjkpmsyohtlcnesbgzqhkalwixfcgyeqdzhnnlzawrdgskurcxfbekbspupbduxqxjeczpmdvssikbivjhinaopbabrmvscthvoqqbkgekcgyrelxkwoawpbrcbszelnxlyikbulgmlwyffurimlfxurjsbzgddxbgqpcdsuutfiivjbyqzhprdqhahpgenjkbiukurvdwapuewrbehczrtswubthodv"));
    }
}
