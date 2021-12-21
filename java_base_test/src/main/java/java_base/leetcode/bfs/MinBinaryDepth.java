package java_base.leetcode.bfs;

import java_base.leetcode.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Kled
 * @date 2021/12/7 5:12 下午
 */
public class MinBinaryDepth {
    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int minDepth = 0;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            minDepth++;
            int size = queue.size();
            while (size > 0) {
                TreeNode node = queue.poll();
                if(node.left == null && node.right == null){
                    return minDepth;
                }
                if(node.left != null){
                    queue.offer(node.left);
                }
                if(node.right != null){
                    queue.offer(node.right);
                }
                size--;
            }
        }
        return minDepth;
    }
}
