package java_base.leetcode.array;

/**
 * @author Kled
 * @date 2021/12/27 5:40 PM
 */
public class RotateMatrix {
    int n;

    public void rotate(int[][] matrix) {
        n = matrix.length;
        for (int i = 0; i < n / 2; i++) {
            doRotate(matrix, i);
        }
    }

    public void doRotate(int[][] matrix, int round) {
        int[] tmp1 = new int[n];
        int[] tmp2 = new int[n];
        int[] tmp3 = new int[n];
        for (int i = round; i < n - round; i++) {
            tmp1[i] = matrix[n - i - 1][n - round - 1];
            tmp2[i] = matrix[n - round - 1][i];
            tmp3[i] = matrix[i][round];
        }
        //旋转上行
        for (int i = round; i < n - round; i++) {
            matrix[n - i - 1][n - round - 1] = matrix[round][n - i - 1];
        }
        //旋转右列
        for (int i = round; i < n - round; i++) {
            matrix[n - round - 1][i] = tmp1[i];
        }
        //旋转下行
        for (int i = round; i < n - round; i++) {
            matrix[i][round] = tmp2[i];
        }
        //旋转左列
        for (int i = round; i < n - round; i++) {
            matrix[round][n - i - 1] = tmp3[i];
        }
    }

    //参考答案：矩阵旋转
    public void rotate2(int[][] matrix) {
        int n = matrix.length;
        int[][] matrix_new = new int[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                matrix_new[j][n - i - 1] = matrix[i][j];
            }
        }
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                matrix[i][j] = matrix_new[i][j];
            }
        }
    }

    public static void main(String[] args) {
        new RotateMatrix().rotate(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
    }
}
