package leecode.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author : weizc
 * @apiNode:
 * @since 2020/4/3
 */
public class BuildTree {

    /*    根据一棵树的前序遍历与中序遍历构造二叉树。

        注意:
        你可以假设树中没有重复的元素。

        例如，给出

        前序遍历 preorder = [3,9,20,15,7]
        中序遍历 inorder = [9,3,15,20,7]
        返回如下的二叉树：

                  3
                / \
              9  20
                /  \
             15   7
     */
    public static TreeNode buildTree(int[] preorder, int[] inorder) {
        if(preorder.length==0||inorder.length==0){
            return null;
        }
        List<Integer> inorderList = toList(inorder);

        TreeNode root = new TreeNode(preorder[0]);// 创建根
        int index = inorderList.indexOf(preorder[0]);// 找到中序的根
        root.left=buildTree(Arrays.copyOfRange(preorder,1,index+1),Arrays.copyOfRange(inorder,0,index));
        root.right=buildTree(Arrays.copyOfRange(preorder,index+1,preorder.length),Arrays.copyOfRange(inorder,index+1,inorder.length));

        return root;
    }

    public static void main(String[] args) {
        int[]preorder ={3,9,20,15,7};
        int[]inorder ={9,3,15,20,7};
        TreeNode treeNode = buildTree(preorder, inorder);
        System.out.println("treeNode :"+treeNode);
    }

    static List<Integer>toList(int[] arr){
        List<Integer> list = new ArrayList<>(arr.length);
        for (int val:arr){
            list.add(val);
        }
        return list;
    }


}
