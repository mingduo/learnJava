package leecode._17_19_tree;

import leecode.utils.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * https://leetcode-cn.com/problems/validate-binary-search-tree/
 */
public class _18_NineEight {
    /**
     * 给定一个二叉树，判断其是否是一个有效的二叉搜索树。
     * <p>
     * 假设一个二叉搜索树具有如下特征：
     * <p>
     * 节点的左子树只包含小于当前节点的数。
     * 节点的右子树只包含大于当前节点的数。
     * 所有左子树和右子树自身必须也是二叉搜索树
     * 示例 1:
     * <p>
     * 输入:
     * 2
     * / \
     * 1   3
     * 输出: true
     * 示例 2:
     * <p>
     * 输入:
     * 5
     * / \
     * 1   4
     *      / \
     *     3   6
     * 输出: false
     * 解释: 输入为: [5,1,4,null,null,3,6]。
     *      根节点的值为 5 ，但是其右子节点值为 4
     */
    /**
     * 中序遍历之后比较
     *
     * @param root
     * @return
     */
    public static boolean isValidBST(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        midSearch(root, list);
        List<Integer> distinctSortedList = list.stream().distinct().sorted().collect(Collectors.toList());
        return list.equals(distinctSortedList);
    }

    static void midSearch(TreeNode root, List<Integer> list) {
        if (root == null) {
            return;
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        midSearch(root.left, list);
        //root
        list.add(root.val);

        midSearch(root.right, list);

    }


    /**
     * 中序遍历之后比较
     * 但是可以只存一个中间数进行比较，不需要存储全部元素
     * <p>
     * * 1.About Complexity
     * *     1.1 Time Complexity is O(log n)
     * *     1.2 Space Complexity is O(1)
     * * 2.how I solve
     * *     2.1 this solution is base on inorder traversal
     * *     2.2 root's val is less than right children's val and greater than left children's val
     *
     * @param root
     * @return
     */
    static int last = Integer.MIN_VALUE;

    public static boolean isValidBST2(TreeNode root) {
        if (root == null) {
            return true;
        }
        if (isValidBST2(root.left)) {
            if (root.val > last) {
                last = root.val;
                return isValidBST2(root.right);
            }
        }
        return false;
    }


    /**
     * 前序遍历
     *
     * @param root
     * @return
     */
    public static boolean isValidBST3(TreeNode root) {
        if (root == null) {
            return true;
        }
        return fontSearchValid(root.left, Integer.MIN_VALUE, root.val);

    }

    private static boolean fontSearchValid(TreeNode root, int min, int max) {
        if (root == null) {
            return true;
        }
        if (root.val < min || root.val > max) {
            return false;
        }
        return fontSearchValid(root.left, min, root.val)
                &&
                fontSearchValid(root.right, root.val, max);
    }


    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        midSearch(createInstanceTreeNode(), list);
        System.out.println(list);
        System.out.println(isValidBST(createInstanceTreeNode()));
        System.out.println(isValidBST2(createInstanceTreeNode()));


        System.out.println(isValidBST3(createInstanceTreeNode()));


    }

    private static TreeNode createInstanceTreeNode() {
        TreeNode root = new TreeNode(2);
        root.left = new TreeNode(1);
        root.right = new TreeNode(3);

        return root;
    }
}