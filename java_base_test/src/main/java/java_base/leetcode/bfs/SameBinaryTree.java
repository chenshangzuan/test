package java_base.leetcode.bfs;

import java_base.leetcode.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Kled
 * @date 2021/12/7 11:20 上午
 */
public class SameBinaryTree {
    public boolean isSameTree(TreeNode p, TreeNode q) {
        Queue<TreeNode> q1 = new LinkedList<>();
        q1.offer(p);
        Queue<TreeNode> q2 = new LinkedList<>();
        q2.offer(q);
        while (!q1.isEmpty() || !q2.isEmpty()){
            int q1Size = q1.size();
            int q2Size = q2.size();
            if(q1Size != q2Size){
                return false;
            }
            for (int i = 0; i < q1Size; i++) {
                TreeNode q1Node = q1.poll();
                TreeNode q2Node = q2.poll();
                if (q1Node == null && q2Node == null) {
                    continue;
                }
                if (q1Node == null || q2Node == null) {
                    return false;
                }
                if(q1Node.val != q2Node.val){
                    return false;
                }
                q1.offer(q1Node.left);
                q1.offer(q1Node.right);
                q2.offer(q2Node.left);
                q2.offer(q2Node.right);
            }
        }
        return true;
    }
}
