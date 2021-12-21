package java_base.leetcode.bfs;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Kled
 * @date 2021/12/7 7:55 下午
 */
public class MatrixLandPerimeter {
    //超时
    public int islandPerimeter(int[][] grid) {
        int perimeter = 0;
        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};
        Queue<int[]> queue = new LinkedList<>();

        int m = grid.length;
        int n = grid[0].length;
        for (int i = 0; i < n; i++) {
            if (grid[0][i] == 0) {
                queue.offer(new int[]{0, i});
                grid[0][i] = 2;
            } else {
                perimeter++;
            }
            if (grid[m - 1][i] == 0) {
                queue.offer(new int[]{m - 1, i});
                grid[m - 1][i] = 2;
            } else {
                perimeter++;
            }
        }

        for (int i = 0; i < m - 1; i++) {
            if (grid[i][0] == 0) {
                queue.offer(new int[]{i, 0});
                grid[i][0] = 2;
            } else {
                perimeter++;
            }
            if (grid[i][n - 1] == 0) {
                queue.offer(new int[]{i, n - 1});
                grid[i][n - 1] = 2;
            } else {
                perimeter++;
            }
        }

        while (!queue.isEmpty()){
            int[] cell = queue.poll();
            int x = cell[0];
            int y = cell[1];
            for (int i = 0; i < 4; i++) {
                int mx = x + dx[i];
                int my = y + dy[i];
                if (mx < 0 || my < 0 || mx >= n || my >= m || grid[mx][my] != 2) {
                    continue;
                }
                queue.offer(new int[]{mx, my});
                grid[mx][my] = 2;
                perimeter++;
            }
        }
        return perimeter;
    }

    //深度优先
    //*******************************************
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};

    public int islandPerimeter2(int[][] grid) {
        int n = grid.length, m = grid[0].length;
        int ans = 0;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                if (grid[i][j] == 1) {
                    ans += dfs(i, j, grid, n, m);
                }
            }
        }
        return ans;
    }

    public int dfs(int x, int y, int[][] grid, int n, int m) {
        if (x < 0 || x >= n || y < 0 || y >= m || grid[x][y] == 0) {
            return 1;
        }
        if (grid[x][y] == 2) {
            return 0;
        }
        grid[x][y] = 2;
        int res = 0;
        for (int i = 0; i < 4; ++i) {
            int tx = x + dx[i];
            int ty = y + dy[i];
            res += dfs(tx, ty, grid, n, m);
        }
        return res;
    }
}
