package java_base.leetcode.bfs;

import java_base.leetcode.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Kled
 * @date 2021/12/7 5:25 下午
 */
public class SumBinaryLeftLeaves {

    //广度优先
    public int sumOfLeftLeaves(TreeNode root) {
        int sum = 0;
        if (root == null) {
            return sum;
        }
        Queue<TreeNode> leftNodeQ = new LinkedList<>();
        leftNodeQ.offer(root.left);
        Queue<TreeNode> rightNodeQ = new LinkedList<>();
        rightNodeQ.offer(root.right);
        while (!leftNodeQ.isEmpty() || !rightNodeQ.isEmpty()) {
            TreeNode leftNode = leftNodeQ.poll();
            TreeNode rightNode = rightNodeQ.poll();
            if (leftNode != null) {
                if (leftNode.left == null && leftNode.right == null) {
                    sum += leftNode.val;
                }
                if (leftNode.left != null) {
                    leftNodeQ.offer(leftNode.left);
                }
                if (leftNode.right != null) {
                    rightNodeQ.offer(leftNode.right);
                }
            }
            if (rightNode != null) {
                if (rightNode.left != null) {
                    leftNodeQ.offer(rightNode.left);
                }
                if (rightNode.right != null) {
                    rightNodeQ.offer(rightNode.right);
                }
            }
        }
        return sum;
    }

    //最优广度优先
    public int sumOfLeftLeaves3(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);
        int ans = 0;
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node.left != null) {
                if (isLeafNode(node.left)) {
                    ans += node.left.val;
                } else {
                    queue.offer(node.left);
                }
            }
            if (node.right != null) {
                if (!isLeafNode(node.right)) {
                    queue.offer(node.right);
                }
            }
        }
        return ans;
    }

    public boolean isLeafNode(TreeNode node) {
        return node.left == null && node.right == null;
    }

    //深度优先
    int sumOfLeftLeaves;
    public int sumOfLeftLeaves2(TreeNode root) {
        dfs(root);
        return sumOfLeftLeaves;
    }

    public int dfs(TreeNode node){
        if(node == null){
            return 0;
        }
        if(node.left == null && node.right == null){
            return node.val;
        }

        int leftLeaveVal = dfs(node.left);
        sumOfLeftLeaves += leftLeaveVal;
        dfs(node.right);
        return 0;
    }
}
