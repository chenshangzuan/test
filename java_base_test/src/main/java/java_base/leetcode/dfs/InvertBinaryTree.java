package java_base.leetcode.dfs;

import java_base.leetcode.TreeNode;

/**
 * @author Kled
 * @date 2021/11/29 6:41 下午
 */
public class InvertBinaryTree {
    public TreeNode invertTree(TreeNode root) {
        if(root == null){
            return null;
        }
        TreeNode invertNode = new TreeNode(root.val);
        dfs(root, invertNode);
        return invertNode;
    }

    public void dfs(TreeNode node, TreeNode invertNode){
        if(node == null){
            return;
        }
        invertNode.right = node.left == null ? null : new TreeNode(node.left.val);
        dfs(node.left, invertNode.right);
        invertNode.left = node.right == null ? null : new TreeNode(node.right.val);
        dfs(node.right, invertNode.left);
    }

    //最优解
    public TreeNode invertTree2(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);
        root.left = right;
        root.right = left;
        return root;
    }

    public static void main(String[] args) {
        TreeNode treeNode = new TreeNode(4, new TreeNode(2, new TreeNode(1), new TreeNode(3)), new TreeNode(7, new TreeNode(6), new TreeNode(9)));
        System.out.println(new InvertBinaryTree().invertTree(treeNode));
    }
}
