package leecode.tree;

/**
 * 
 *  
 * @since 2020/8/26
 * @author : weizc 
 */
public class BalancedTreeDemo {

    /**
     * 给定一个二叉树，判断它是否是高度平衡的二叉树。
     *
     * 本题中，一棵高度平衡二叉树定义为：
     *
     * 一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过1。
     * @param args
     */
    public static void main(String[] args) {

        System.out.println(isBalanced(TreeNodeUtils.createInstanceTreeNode()));
    }

    public static boolean isBalanced(TreeNode root) {

        int count1 = level(root.left, 1,0);
        int count2 = level(root.right, 1,0);

        return Math.abs(count1-count2)<=1;

    }


    public static int level(TreeNode root,int level,int max) {


        if(root==null){
            return level;
        }
        level++;
        max= Math.max(max, level);

        int left = level(root.left, level,max);
        int right = level(root.right, level,max);

        return Math.max(Math.max(left,right),max);
    }
}
