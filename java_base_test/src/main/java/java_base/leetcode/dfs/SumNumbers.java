package java_base.leetcode.dfs;

import java_base.leetcode.TreeNode;

/**
 * @author Kled
 * @date 2022/4/17 7:23 PM
 */
public class SumNumbers {
    public int sumNumbers(TreeNode root) {
        return dfs(root, 0);
    }

    public int dfs(TreeNode root, int prevSum) {
        if (root == null) {
            return 0;
        }
        int sum = prevSum * 10 + root.val;
        if (root.left == null && root.right == null) {
            return sum;
        } else {
            return dfs(root.left, sum) + dfs(root.right, sum);
        }
    }
}
