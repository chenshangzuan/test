package java_base.leetcode.dfs;

import java_base.leetcode.TreeNode;

/**
 * @author Kled
 * @date 2021/11/26 7:47 下午
 */
public class BalancedBinaryTree {
    boolean isBalanced = true;

    public boolean isBalanced(TreeNode root) {
        dfs(root, 1);
        return isBalanced;
    }

    public int dfs(TreeNode node, int depth) {
        if (node == null) {
            return depth - 1;
        }

        int leftNodeDepth = dfs(node.left, depth + 1);
        int rightNodeDepth = dfs(node.right, depth + 1);
        if (Math.abs(leftNodeDepth - rightNodeDepth) > 1) {
            isBalanced = false;
        }
        //返回当前节点最大的深度
        return Math.max(leftNodeDepth, rightNodeDepth);
    }
//    public boolean isBalanced(TreeNode root) {
//        List<Boolean> tags = new ArrayList<>();
//        dfs(root, 1, tags);
//        return tags.isEmpty();
//    }
//
//    public int dfs(TreeNode node, int depth, List<Boolean> tags) {
//        if (!tags.isEmpty()) {
//            return depth - 1;
//        }
//        if (node == null) {
//            return depth - 1;
//        }
//
//        int leftNodeDepth = dfs(node.left, depth + 1, tags);
//        int rightNodeDepth = dfs(node.right, depth + 1, tags);
//        if (Math.abs(leftNodeDepth - rightNodeDepth) > 1) {
//            tags.add(false);
//        }
//        //返回当前节点最大的深度
//        return Math.max(leftNodeDepth, rightNodeDepth);
//    }

    public static void main(String[] args) {
        TreeNode treeNode = new TreeNode(1);
        treeNode.left = new TreeNode(2, new TreeNode(3, new TreeNode(4), new TreeNode(4)), new TreeNode(3));
        treeNode.right = new TreeNode(2);
        System.out.println(new BalancedBinaryTree().isBalanced(treeNode));
    }
}
