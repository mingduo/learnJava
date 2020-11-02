package leecode._26_30_dfs_bfs;

import leecode.utils.TreeNode;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * https://leetcode-cn.com/problems/binary-tree-level-order-traversal/
 */
public class _28_OneZeroTwo {
    //bfs
    public static List<List<Integer>> levelOrderInBFS(TreeNode root) {
        List<List<Integer>> results = new ArrayList<>();

        if (root == null) {
            return results;
        }
        Queue<List<TreeNode>> q = new LinkedList<>();
        q.add(Collections.singletonList(root));


        while (!q.isEmpty() && q.element().size() > 0) {
            //visited
            List<TreeNode> nodes = q.poll();
            //process data
            results.add(nodes.stream().mapToInt(t -> t.val).boxed().collect(Collectors.toList()));
            //generate next
            List<TreeNode> nextNodes = nodes.stream().flatMap(node -> Stream.of(node.left, node.right))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            q.add(nextNodes);
        }

        return results;
    }

    public static List<List<Integer>> levelOrderInDFS(TreeNode root) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        dfsLevelOrder(root,0,map);
        return new ArrayList<>(map.values());
    }

    static void dfsLevelOrder(TreeNode root, int level, Map<Integer, List<Integer>> map) {
        if (root == null) {
            return;
        }
        //process node
        map.computeIfAbsent(level, key -> new LinkedList<>()).add(root.val);
        //recur
        Stream.of(root.left, root.right)
                .forEach(n -> dfsLevelOrder(n, level + 1, map));
    }

    public static void main(String[] args) {
        System.out.println(levelOrderInBFS(TreeNode.createInstanceTreeNode()));

        System.out.println(levelOrderInDFS(TreeNode.createInstanceTreeNode()));

    }
}