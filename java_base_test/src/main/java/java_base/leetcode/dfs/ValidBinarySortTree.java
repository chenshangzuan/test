package java_base.leetcode.dfs;

import java_base.leetcode.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kled
 * @date 2021/12/29 3:19 PM
 */
public class ValidBinarySortTree {
    //优化，只存上一个值
    public boolean isValidBST(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        dfs(root, list);
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i + 1) <= list.get(i)) {
                return false;
            }
        }
        return true;
    }

    public void dfs(TreeNode node, List<Integer> list) {
        if (node == null) {
            return;
        }
        dfs(node.left, list);
        list.add(node.val);
        dfs(node.right, list);
    }

    Integer tmp = null;
    public boolean dfs2(TreeNode node) {
        if (node == null) {
            return true;
        }
        boolean left = dfs2(node.left);
        if(tmp != null && node.val <= tmp){
            return false;
        }
        tmp = node.val;
        boolean right = dfs2(node.right);
        return left && right;
    }

//参考答案
//    public boolean isValidBST(TreeNode root) {
//        return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
//    }
    public boolean isValidBST(TreeNode node, long lower, long upper) {
        if (node == null) {
            return true;
        }
        if (node.val <= lower || node.val >= upper) {
            return false;
        }
        return isValidBST(node.left, lower, node.val) && isValidBST(node.right, node.val, upper);
    }
}
