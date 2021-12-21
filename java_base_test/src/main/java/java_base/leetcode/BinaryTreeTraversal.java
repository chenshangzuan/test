package java_base.leetcode;

import java.util.*;

/**
 * @author Kled
 * @date 2021/12/13 10:29 上午
 */
public class BinaryTreeTraversal {
    //非递归先序遍历
    public void preOrderTraversal(TreeNode root) {
        if (root == null) {
            return;
        }
        List<Integer> ret = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            ret.add(root.val);

            if (node.right != null) {
                stack.push(node.right);
            }

            if (node.left != null) {
                stack.push(node.left);
            }
        }
        System.out.println(ret);
    }

    //非递归中序遍历
    public void inOderTraversal(TreeNode root) {
        if (root == null) {
            return;
        }
        List<Integer> ret = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        while (root != null || !stack.isEmpty()) {
            if (root != null) {
                stack.push(root);
                root = root.left;
            } else {
                root = stack.pop();
                ret.add(root.val);
                root = root.right;
            }
        }
        System.out.println(ret);
    }

    //非递归后序遍历
    public void proOrderTraversal(TreeNode root) {
        if (root == null) {
            return;
        }
        LinkedList<Integer> ret = new LinkedList<>();
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            ret.addFirst(node.val);

            if (node.left != null) {
                stack.add(node.left);
            }

            if (node.right != null) {
                stack.add(node.right);
            }
        }
        System.out.println(ret);
    }

    //递归遍历
    public void recursionTraversal(TreeNode root) {
        if (root == null) {
            return;
        }

        //先序遍历
        System.out.println(root.val);
        recursionTraversal(root.left);
        recursionTraversal(root.right);

        //中序遍历
//        recursionTraversal(root.left);
//        System.out.println(root.val);
//        recursionTraversal(root.right);

        //后序遍历
//        recursionTraversal(root.left);
//        recursionTraversal(root.right);
//        System.out.println(root.val);

    }
}
