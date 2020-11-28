package leecode.tree;

import leecode.utils.TreeNode;

import java.util.stream.Stream;

/**
 * @author : weizc
 * @since 2020/11/24
 */
public class WholeTreeSolution {

    int count = 0;

    public int countNodes(TreeNode root) {
        _countNodes(root);
        return count;
    }


    public void _countNodes(TreeNode root) {
        if (root == null) {
            return;
        }
        count++;
        countNodes(root.left);
        countNodes(root.right);
    }



    public int __countNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return Stream.of(root.left,root.right)
                .mapToInt(this::__countNodes)
                .reduce(1,Integer::sum);
    }

    public static void main(String[] args) {

        TreeNode nodes = TreeNode.createInstanceTreeNode();
        WholeTreeSolution solution = new WholeTreeSolution();
        solution.countNodes(nodes);
        System.out.println(solution.count);

        System.out.println(solution.__countNodes(nodes));

    }
}
