package leecode._26_30_dfs_bfs;


import leecode.utils.TreeNode;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * https://leetcode-cn.com/problems/minimum-depth-of-binary-tree/
 */
public class _29_OneOneOne {

    /**
     * 递归
     *
     * @param root
     * @return
     */
    public static int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        return Stream.of(root.left,root.right)
                .filter(Objects::nonNull)
                .mapToInt(_29_OneOneOne::minDepth)
                .boxed()
                .min(Integer::compare)
                .orElse(0)+1;


    }

    public static void main(String[] args) {
        TreeNode node = TreeNode.createInstanceTreeNode();
        System.out.println(minDepth(node.left));
        System.out.println(minDepth(node.right));
        System.out.println(minDepth(node.right.right));

        System.out.println(minDepth(node));

        TreeNode lineNode = TreeNode.createLineInstanceTreeNode();
        System.out.println(minDepth(lineNode));

    }
}