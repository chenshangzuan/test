package java_base.leetcode.dfs;

import java_base.leetcode.TreeNode;

/**
 * @author Kled
 * @date 2021/11/26 6:23 下午
 */
public class SymmetricBinaryTree {
    //先序遍历之后的结果，是否为回文数组，不支持node的val重复
//    public boolean isSymmetric(TreeNode root) {
//        List<Integer> ret = new ArrayList<>();
//        doTraversal(root, ret);
//        int left = 0;
//        int right = ret.size() - 1;
//        while (left < right) {
//            if (ret.get(left) != ret.get(right)) {
//                return false;
//            }
//            left++;
//            right--;
//        }
//        return true;
//    }
//
//    public void doTraversal(TreeNode node, List<Integer> ret) {
//        if (node == null) {
//            return;
//        }
//
//        doTraversal(node.left, ret);
//        ret.add(node.val);
//        doTraversal(node.right, ret);
//    }

    //最优解
    public boolean isSymmetric(TreeNode root) {
        return check(root, root);
    }

    public boolean check(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }
        return p.val == q.val && check(p.left, q.right) && check(p.right, q.left);
    }
}
