package leecode._17_19_tree;

import leecode.utils.TreeNode;

/**
 * https://leetcode-cn.com/problems/lowest-common-ancestor-of-a-binary-search-tree/
 */

public class _19_TwoThreeFive {
    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) {
            return root;
        }

        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        if (left != null && right != null) {
            return root;
        } else if (left != null) {
            return left;
        } else if (right != null) {
            return right;
        }
        return null;

    }


    public static void main(String[] args) {
        TreeNode root = TreeNode.createInstanceTreeNode();


        System.out.println(lowestCommonAncestor(root,root.left,root.right));

       int[] arr = new int[]{3,5,-2,-3,-6,-6};




    }
}