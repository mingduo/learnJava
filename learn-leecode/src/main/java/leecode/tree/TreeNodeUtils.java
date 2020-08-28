package leecode.tree;

import java.util.stream.IntStream;

/**
 * 
 * @apiNode:
 * @since 2020/4/3
 * @author : weizc 
 */
public interface TreeNodeUtils {
    //输入: root = [3,5,1,6,2,0,8,null,null,7,4]
    static TreeNode createInstanceTreeNode(){
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(5);
        root.right = new TreeNode(1);

        root.left.left = new TreeNode(6);
        root.left.right = new TreeNode(2);

        root.right.left = new TreeNode(0);
        root.right.right = new TreeNode(8);

        root.left.right.left = new TreeNode(7);
        root.left.right.right = new TreeNode(4);
        return root;
    }



    public static void main(String[] args) {
        fontSearch(createInstanceTreeNode(),0);
    }


    // 前序遍历：根结点 ---> 左子树 ---> 右子树
    static void fontSearch(TreeNode node,int level){
        if(node!=null){
            IntStream.range(0,level).forEach(t->System.out.print("-"));
            System.out.println("当前节点值为:" + node.val);
            fontSearch(node.left,level+1);
            fontSearch(node.right,level+1);
        }
    }
    //中序遍历：左子树---> 根结点 ---> 右子树
    static void midSearch(TreeNode node){

    }
}
