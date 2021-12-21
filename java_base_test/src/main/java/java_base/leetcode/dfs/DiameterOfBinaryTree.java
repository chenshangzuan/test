package java_base.leetcode.dfs;

import java_base.leetcode.TreeNode;

/**
 * @author Kled
 * @date 2021/11/29 6:07 下午
 */
public class DiameterOfBinaryTree {
    int maxDiameter;

    public int diameterOfBinaryTree(TreeNode root) {
        dfs(root, 1);
        return maxDiameter;
    }

    public int dfs(TreeNode node, int depth) {
        if (node == null) {
            return depth - 1;
        }

        int leftDepth = dfs(node.left, depth + 1);
        int rightDepth = dfs(node.right, depth + 1);
        maxDiameter = Math.max(maxDiameter, leftDepth + rightDepth - 2 * depth);
        return Math.max(leftDepth, rightDepth);
    }

}
