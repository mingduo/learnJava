package leecode.tree;

import leecode.utils.TreeNode;

/**
 * @author : weizc
 * @since 2020/8/26
 */
public class InvertTreeDemo {

    public static void main(String[] args) {

        //root = [3,5,1,6,2,0,8,null,null,7,4]
        TreeNode treeNode = TreeNode.createInstanceTreeNode();


        System.out.println(treeNode);

        System.out.println(invertTree(treeNode));

    }

    public static TreeNode invertTree(TreeNode root) {

        TreeNode left = root.left;
        TreeNode right = root.right;
        if(right!=null||left!=null){
            swap(root);
        } else {
           return null;
        }
        invertTree(root.left);
        invertTree(root.right);

        return root;
    }


    private static void swap(TreeNode root){
        TreeNode tmp=root.left;
        root.left=root.right;
        root.right=tmp;
    }
}
