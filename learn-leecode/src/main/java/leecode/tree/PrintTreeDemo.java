package leecode.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author : weizc
 * @since 2020/8/25
 */
public class PrintTreeDemo {

    /**
     * 剑指 Offer 32 - II. 从上到下打印二叉树 II
     * <p>
     * 从上到下按层打印二叉树，同一层的节点按从左到右的顺序打印，每一层打印到一行。
     * <p>
     * <p>
     * <p>
     * 例如:
     * 给定二叉树: [3,9,20,null,null,15,7],
     * 返回其层次遍历结果：
     * <p>
     * [
     * [3],
     * [9,20],
     * [15,7]
     * ]
     *
     * @param args
     */
    public static void main(String[] args) {
        TreeNode root = TreeNodeUtils.createInstanceTreeNode();

        System.out.println(root);


        System.out.println(levelOrder(root));
    }


    static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> levels = new ArrayList<>();
        levels.add(Collections.singletonList(root.val));
        levelOrder(Collections.singletonList(root), levels);

        return levels;
    }

    private static void levelOrder(List<TreeNode> roots, List<List<Integer>> levels) {
        List<Integer> list = new ArrayList<>();
        List<TreeNode> children = new ArrayList<>();


        roots.forEach(root -> {
            TreeNode left = root.left;
            TreeNode right = root.right;
            if (left != null) {
                list.add(left.val);
                children.add(left);

            }
            if (right != null) {
                list.add(right.val);
                children.add(right);
            }


        });
        if (list.size() > 0) {
            levels.add(list);
        }
        if (children.size() > 0) {
            levelOrder(children, levels);
        }

    }
}
