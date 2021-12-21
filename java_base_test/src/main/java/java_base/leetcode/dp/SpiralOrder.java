package java_base.leetcode.dp;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kled
 * @date 2021/11/17 6:28 下午
 * 螺旋矩阵
 */
public class SpiralOrder {
    public List<Integer> spiralOrder(int[][] matrix) {
        int m = matrix.length - 1;
        int n = matrix[0].length - 1;
        List<Integer> spiralOrder = new ArrayList<>();
        while (true) {
            List<Integer> subSpiralOrder = new ArrayList<>();
            for (int i = matrix[0].length - n - 1; i <= n; i++) {
                subSpiralOrder.add(matrix[matrix.length - m - 1][i]);
            }
            for (int i = matrix.length - m; i <= m - 1; i++) {
                subSpiralOrder.add(matrix[i][n]);
            }
            if ((matrix.length %2 == 0 && m >= matrix.length / 2) || (matrix.length %2 == 1 && m != matrix.length / 2)) {
                for (int i = n; i >= matrix[0].length - n - 1; i--) {
                    subSpiralOrder.add(matrix[m][i]);
                }
            }
            if ((matrix[0].length %2 == 0 && n >= matrix[0].length / 2) || (matrix[0].length %2 == 1 && n != matrix[0].length / 2)) {
                for (int i = m - 1; i >= matrix.length - m; i--) {
                    subSpiralOrder.add(matrix[i][matrix[0].length - n - 1]);
                }
            }
            spiralOrder.addAll(subSpiralOrder);
            if (m == matrix.length / 2 || n == matrix[0].length / 2) {
                break;
            }
            m--;
            n--;
        }
        return spiralOrder;
    }

    public static void main(String[] args) {
        System.out.println(new SpiralOrder().spiralOrder(new int[][]{{3}, {2}}));
        System.out.println(new SpiralOrder().spiralOrder(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}));
        System.out.println(new SpiralOrder().spiralOrder(new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}}));
    }
}
