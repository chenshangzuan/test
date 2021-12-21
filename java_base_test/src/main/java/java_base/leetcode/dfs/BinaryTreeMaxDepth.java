package java_base.leetcode.dfs;

import java_base.leetcode.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Kled
 * @date 2021/11/26 7:06 下午
 */
public class BinaryTreeMaxDepth {

    //深度优先解法
    int maxDepth;

    public int maxDepth(TreeNode root) {
        dfs(root, 1);
        return maxDepth;
    }

    public void dfs(TreeNode node, int depth) {
        if (node == null) {
            maxDepth = Math.max(maxDepth, depth - 1);
            return;
        }
        dfs(node.left, depth + 1);
        dfs(node.right, depth + 1);
    }

    //广度优先解法
    public int maxDepth2(TreeNode root) {
        if (root == null){
            return 0;
        }
        Queue<TreeNode> treeNodeQueue = new LinkedList<>();
        treeNodeQueue.offer(root);
        int maxDepth = 0;
        int treeLayerCount = 1;
        int nextLayerCount = 0;
        while (!treeNodeQueue.isEmpty()) {
            TreeNode node = treeNodeQueue.poll();
            treeLayerCount--;
            if (node.left != null) {
                treeNodeQueue.offer(node.left);
                nextLayerCount++;
            }
            if (node.right != null) {
                treeNodeQueue.offer(node.right);
                nextLayerCount++;
            }
            if (treeLayerCount == 0) {
                maxDepth++;
                treeLayerCount = nextLayerCount;
                nextLayerCount = 0;
            }
        }
        return maxDepth;
    }

    public static void main(String[] args) {
        TreeNode treeNode = new TreeNode(3);
        treeNode.left = new TreeNode(9);
        treeNode.right = new TreeNode(20, new TreeNode(15), new TreeNode(7));
        System.out.println(new BinaryTreeMaxDepth().maxDepth2(treeNode));
    }



}
