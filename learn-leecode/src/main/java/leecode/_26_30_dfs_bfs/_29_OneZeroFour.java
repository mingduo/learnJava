package leecode._26_30_dfs_bfs;

import leecode.utils.TreeNode;

import java.util.stream.Stream;

/**
 * https://leetcode-cn.com/problems/maximum-depth-of-binary-tree/
 */
public class _29_OneZeroFour {
    public static int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        return Stream.of(root.left,root.right)
                .mapToInt(t->maxDepth(t))
                .boxed()
                .reduce(0,Math::max)+1;
    }

    public static void main(String[] args) {
        TreeNode node = TreeNode.createInstanceTreeNode();
        System.out.println(maxDepth(node.left));
        System.out.println(maxDepth(node.right));
        System.out.println(maxDepth(node.right.right));

        System.out.println(maxDepth(node));


    }
}