package leecode.tree;

/**
 * 
 *  
 * @since 2020/8/26
 * @author : weizc 
 */
public class ForEachTreeDemo {

    public static void main(String[] args) {
        TreeNodeUtils.createInstanceTreeNode();

    }


    //前序遍历
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }

        // 左子树和右子树交换，即使左右子树都空也不影响正确性
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;

        // 递归翻转左右子树
        invertTree(root.left);
        invertTree(root.right);
        return root;
    }


    //中序遍历
    public TreeNode invertTree2(TreeNode root) {
        if (root == null) {
            return null;
        }

        invertTree(root.left);

        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;

        // 注意：因为左右子树已经交换了，因此这里不能写 invertTree(root.right);
        // 即：这里的 root.left 就是交换之前的 root.right
        invertTree(root.left);
        return root;
    }

    //方法三：后序遍历
    public TreeNode invertTree3(TreeNode root) {
        if (root == null) {
            return null;
        }

        invertTree(root.left);
        invertTree(root.right);

        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        return root;
    }

}
