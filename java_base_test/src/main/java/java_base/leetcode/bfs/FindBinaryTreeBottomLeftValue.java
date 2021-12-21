package java_base.leetcode.bfs;

import java_base.leetcode.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Kled
 * @date 2021/12/9 4:43 下午
 */
public class FindBinaryTreeBottomLeftValue {
    public int findBottomLeftValue(TreeNode root) {
        int bottomLeftValue = 0;
        if(root ==null){
            return bottomLeftValue;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()){
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if(i == 0){
                    bottomLeftValue = node.val;
                }

                if (node.left != null) {
                    queue.offer(node.left);
                }

                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
        return bottomLeftValue;
    }
}
