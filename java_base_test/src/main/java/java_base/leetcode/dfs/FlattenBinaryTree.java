package java_base.leetcode.dfs;

import java_base.leetcode.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kled
 * @date 2021/12/2 8:41 下午
 */
public class FlattenBinaryTree {
    List<Integer> values = new ArrayList<>();

    public void flatten(TreeNode root) {
        if (root == null) {
            return;
        }
        dfs(root);
        TreeNode treeNode = null;
        for (int i = 1; i < values.size(); i++) {
            if (treeNode == null) {
                treeNode = new TreeNode(values.get(i));
                root.right = treeNode;
            } else {
                treeNode.right = new TreeNode(values.get(i));
                treeNode = treeNode.right;
            }
        }
        root.left = null;
    }

    public void dfs(TreeNode node) {
        if (node == null) {
            return;
        }
        values.add(node.val);
        dfs(node.left);
        dfs(node.right);
    }

    public static void main(String[] args) {
        new FlattenBinaryTree().flatten(new TreeNode(1, new TreeNode(2, new TreeNode(3), new TreeNode(4)), new TreeNode(5, null, new TreeNode(6))));
    }
}
