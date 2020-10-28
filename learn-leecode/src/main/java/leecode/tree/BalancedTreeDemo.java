package leecode.tree;

import leecode.utils.TreeNode;

import java.util.Arrays;
import java.util.List;

/**
 * @author : weizc
 * @since 2020/8/26
 */
public class BalancedTreeDemo {

    /**
     * 给定一个二叉树，判断它是否是高度平衡的二叉树。
     * <p>
     * 本题中，一棵高度平衡二叉树定义为：
     * <p>
     * 一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过1。
     *
     * @param args
     */
    public static void main(String[] args) {

        TreeNode node = TreeNode.createInstanceTreeNode();
        System.out.println(isBalanced(node));

        System.out.println(depth(node));
    }

    public static boolean isBalanced(TreeNode root) {

        return Math.abs(depth(root.left)-depth(root.right))<1;
    }


    public static int depth(TreeNode root) {


        if (root == null) {
            return 0;
        }

        return children(root).stream()
                .map(BalancedTreeDemo::depth)
                .max(Integer::compare).orElse(0) + 1;
    }


    private static List<TreeNode> children(TreeNode node) {
        return Arrays.asList(node.left, node.right);
    }


}
