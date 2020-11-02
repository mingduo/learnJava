package leecode.tree;

import leecode.utils.TreeNode;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MaxDepthSolution {

    public static int bfsmaxDepth(TreeNode root) {
        int i = 0;
        if (root == null) {
            return i;
        }
        Queue<List<TreeNode>> q = new ArrayDeque<>();
        q.offer(Collections.singletonList(root));
        while (!q.isEmpty()) {
            List<TreeNode> nodes = q.poll();
            //process node

            //generate next
            List<TreeNode> nextnodes = nodes.stream().flatMap(node ->
                    Stream.of(node.left, node.right))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            if (!nextnodes.isEmpty()) {
                q.add(nextnodes);
            }
            i++;
        }
        return i;
    }

    public static int dfsmaxDepth(TreeNode root) {
        return dfsmaxDepth(root, 0);
    }

    private static int dfsmaxDepth(TreeNode root, int level) {
        if (root == null) {
            return level;
        }
        //#visited
        //process node
        level++;
        System.out.println("node:" + root.val + ",level:" + level);
        //for each
        int left = dfsmaxDepth(root.left, level);
        int right = dfsmaxDepth(root.right, level);

        return Math.max(left, right);
    }


    private static int dfsmaxDepth2(TreeNode root, int level) {
        if (root == null) {
            return level;
        }
        return Stream.of(root.left, root.right)
                .map(t -> dfsmaxDepth2(t, level + 1))
                .max(Integer::compareTo).get();

    }

    public static void main(String[] args) {
        TreeNode node = TreeNode.createInstanceTreeNode();
        System.out.println("maxDepth:" + bfsmaxDepth(node));

        System.out.println("maxDepth:" + dfsmaxDepth(node));

        System.out.println("maxDepth:" + dfsmaxDepth2(node, 0));

    }
}