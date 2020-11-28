package leecode.utils;

import lombok.ToString;

/**
 * 
 * @apiNode:
 * @since 2020/4/3
 * @author : weizc 
 */
@ToString
public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(int x) {
        val = x;
    }

    //输入: root = [3,5,1,6,2,0,8,null,null,7,4]
    public static TreeNode createInstanceTreeNode(){
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(5);
        root.right = new TreeNode(1);

        root.left.left = new TreeNode(6);
        root.left.right = new TreeNode(2);

        root.right.right = new TreeNode(8);

        root.left.right.left = new TreeNode(7);
        root.left.right.right = new TreeNode(4);
        return root;
    }


    public static TreeNode createLineInstanceTreeNode(){
        TreeNode root = new TreeNode(2);
        root.right = new TreeNode(3);


        root.right.right = new TreeNode(4);

        root.right.right.right = new TreeNode(5);
        return root;
    }
}
