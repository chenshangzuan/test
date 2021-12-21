package java_base.leetcode.dfs.backtrack;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kled
 * @date 2021/12/19 4:40 PM
 */
public class MatrixWordSearch {
    public boolean exist(char[][] board, String word) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == word.charAt(0)) {
                    boolean b = backtrack(board, i, j, 0, word, new ArrayList<>());
                    if (b) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean backtrack(char[][] board, int m, int n, int charIndex, String word, List<Integer> paths) {
        Integer path = m * board[0].length + n;
        if (n < 0 || m < 0 || m > board.length - 1 || n > board[0].length - 1 || paths.contains(path)) {
            return false;
        }
        if (charIndex > (word.length() - 1)) {
            return false;
        }
        if (board[m][n] != word.charAt(charIndex)) {
            return false;
        }
        if (charIndex == (word.length() - 1) && board[m][n] == word.charAt(charIndex)) {
            return true;
        }
        paths.add(path);
        boolean down = backtrack(board, m + 1, n, charIndex + 1, word, paths);
        boolean right = backtrack(board, m, n + 1, charIndex + 1, word, paths);
        boolean up = backtrack(board, m - 1, n, charIndex + 1, word, paths);
        boolean left = backtrack(board, m, n - 1, charIndex + 1, word, paths);
        paths.remove(path);
        return down || right || up || left;
    }

    int[] dx = {0, 0, 1, -1};
    int[] dy = {1, -1, 0, 0};
    public boolean backtrack2(char[][] board, int m, int n, int charIndex, String word, List<Integer> paths) {
        Integer path = m * board[0].length + n;
        if (n < 0 || m < 0 || m > board.length - 1 || n > board[0].length - 1 || paths.contains(path)) {
            return false;
        }
        if (charIndex > (word.length() - 1)) {
            return false;
        }
        if (board[m][n] != word.charAt(charIndex)) {
            return false;
        }
        if (charIndex == (word.length() - 1) && board[m][n] == word.charAt(charIndex)) {
            return true;
        }
        paths.add(path);
        for (int i = 0; i < 4; i++) {
            boolean flag = backtrack(board, m + dx[i], n + dy[i], charIndex + 1, word, paths);
            if(flag){
                return true;
            }
        }
        paths.remove(path);
        return false;
    }

    public static void main(String[] args) {
        System.out.println(new MatrixWordSearch().exist(new char[][]{
                {'A', 'B', 'C', 'E'},
                {'S', 'F', 'C', 'S'},
                {'A', 'D', 'E', 'E'}
        }, "ABCCED"));

//        System.out.println(new MatrixWordSearch().exist(new char[][]{
//                {'A','B','C','E'},
//                {'S','F','C','S'},
//                {'A','D','E','E'}
//        }, "ABCB"));
    }
}
