package java_base.leetcode.dfs.backtrack;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kled
 * @date 2021/11/12 8:04 下午
 */
public class GenerateParenthesis {

//    public List<String> generateParenthesis(int n) {
//        List<String> combinations = new ArrayList<String>();
//        generateAll(new char[2 * n], 0, combinations);
//        return combinations;
//    }
//
//    public void generateAll(char[] current, int pos, List<String> result) {
//        if (pos == current.length) {
//            if (valid(current)) {
//                result.add(new String(current));
//            }
//        } else {
//            current[pos] = '(';
//            generateAll(current, pos + 1, result);
//            current[pos] = ')';
//            generateAll(current, pos + 1, result);
//        }
//    }
//
//    public boolean valid(char[] current) {
//        int balance = 0;
//        for (char c: current) {
//            if (c == '(') {
//                ++balance;
//            } else {
//                --balance;
//            }
//            if (balance < 0) {
//                return false;
//            }
//        }
//        return balance == 0;
//    }

//        List<String> res = new ArrayList<>();
//        public List<String> generateParenthesis(int n) {
//            dfs(n, n, "");
//            return res;
//        }
//
//        private void dfs(int left, int right, String curStr) {
//            if (left == 0 && right == 0) { // 左右括号都不剩余了，递归终止
//                res.add(curStr);
//                return;
//            }
//
//            if (left > 0) { // 如果左括号还剩余的话，可以拼接左括号
//                dfs(left - 1, right, curStr + "(");
//            }
//            if (right > left) { // 如果右括号剩余多于左括号剩余的话，可以拼接右括号
//                dfs(left, right - 1, curStr + ")");
//            }
//        }

    public List<String> generateParenthesis(int n){
        List<String> result = new ArrayList<>();
        backtrack("", n, n , result);
        return result;
    }

    public void backtrack(String track, int left, int right, List<String> result){
        if(left == 0 && right == 0){
            result.add(track);
        }

        if(left > 0){
            track += "(";
            backtrack(track, left -1, right, result);
            track = track.substring(0, track.length() -1);
        }
        if(right > left){
            track += ")";
            backtrack(track, left, right -1, result);
        }
    }

    public static void main(String[] args) {
        System.out.println(new GenerateParenthesis().generateParenthesis(3));
    }
}
