package leecode.tree;

import leecode.utils.TreeNode;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author : weizc
 * @since 2020/8/28
 */
public class TreeSymmetricDemo {
    /**
     * 给定一个二叉树，检查它是否是镜像对称的。
     * <p>
     *  
     * <p>
     * 例如，二叉树 [1,2,2,3,4,4,3] 是对称的。
     * <p>
     * 1
     * / \
     * 2   2
     * / \ / \
     * 3  4 4  3
     *  
     * <p>
     * 但是下面这个 [1,2,2,null,3,null,3] 则不是镜像对称的:
     * <p>
     * 1
     * / \
     * 2   2
     * \   \
     * 3    3
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/symmetric-tree
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    //[1,2,2,3,4,4,3]
    static TreeNode createInstanceTreeNode2(){
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(2);

        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(4);

        root.right.left = new TreeNode(4);
        root.right.right = new TreeNode(3);


        return root;
    }

    static TreeNode createInstanceTreeNode3(){
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(2);

        root.left.right = new TreeNode(3);

        root.right.left = new TreeNode(3);


        return root;
    }

    public static void main(String[] args) {


        TreeNode treeNode = createInstanceTreeNode2();

        System.out.println(isSymmetric(treeNode));
        // [1,2,2,null,3,null,3]
         treeNode = createInstanceTreeNode3();
        //[1,2,2,null,3,3]
        System.out.println(isSymmetric(treeNode));

    }

    /**
     * 递归
     * @param root
     * @return
     */
    public boolean isSymmetricBest(TreeNode root) {
        return root == null ? true : recur(root.left, root.right);
    }
    boolean recur(TreeNode L, TreeNode R) {
        if(L == null && R == null) {
            return true;
        }
        if(L == null || R == null || L.val != R.val) {
            return false;
        }
        return recur(L.left, R.right) && recur(L.right, R.left);
    }




    public static boolean isSymmetric(TreeNode treeNode) {
        if(treeNode==null){
            return true;
        }
            return isSymmetric(Arrays.asList(treeNode.left, treeNode.right));

    }

        private static boolean isSymmetric(List<TreeNode> treeNodeList) {


        while (!treeNodeList.isEmpty()) {


            //偶数
            int size = treeNodeList.size();
            if (size % 2 == 0) {
                for (int i = 0; i < size / 2; i++) {
                    TreeNode first = treeNodeList.get(i);
                    TreeNode last = treeNodeList.get(size - i-1);
                    if(Objects.isNull(first)&&Objects.isNull(last)){
                        continue;
                    }else if (Objects.nonNull(first)&&Objects.nonNull(last)) {
                        if(first.val==last.val){
                            continue;
                        }
                        return false;
                    }
                    return false;
                }
                treeNodeList  = treeNodeList.stream()
                        .filter(Objects::nonNull)
                        .flatMap(t -> Stream.of(t.left, t.right))
                        .collect(Collectors.toList());;

                if(treeNodeList.stream().noneMatch(Objects::nonNull)  ){
                    break;
                }

                continue;
                //下一轮循环
            }
            return false;

        }
        return true;
    }


}
