package leecode.tree;

import leecode.utils.TreeNode;

/**
 * @author : weizc
 * @since 2020/8/25
 */
public class MergedTreeDemo {


    /**
     * 给定两个二叉树，想象当你将它们中的一个覆盖到另一个上时，两个二叉树的一些节点便会重叠。
     * <p>
     * 你需要将他们合并为一个新的二叉树。合并的规则是如果两个节点重叠，那么将他们的值相加作为节点合并后的新值，否则不为 NULL 的节点将直接作为新二叉树的节点。
     * <p>
     * 示例 1:
     * <p>
     * 输入:
     * Tree 1                     Tree 2
     * 1                         2
     * / \                       / \
     * 3   2                     1   3
     * /                           \   \
     * 5                             4   7
     * 输出:
     * 合并后的树:
     * 3
     * / \
     * 4   5
     * / \   \
     * 5   4   7
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/merge-two-binary-trees
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     * @param args
     */
    public static void main(String[] args) {
        TreeNode treeA = TreeNode.createInstanceTreeNode();
        TreeNode treeB = TreeNode.createInstanceTreeNode();

        System.out.println(mergeTree(treeA, treeB));



    }


    private static TreeNode mergeTree(TreeNode treeA, TreeNode treeB) {


        if(treeA==null){
            return treeB;
        }else if(treeB==null){
            return treeA;
        }
        treeA.val+=treeB.val;

        treeA.left=mergeTree(treeA.left,treeB.left);
        treeA.right=mergeTree(treeA.right,treeB.right);

        return treeA;

    }



}
