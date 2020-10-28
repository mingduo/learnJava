package leecode.tree;

import leecode.utils.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author : weizc
 * @apiNode:
 * @since 2020/4/3
 */
public interface TreeNodeUtils {
    //输入: root = [3,5,1,6,2,0,8,null,null,7,4]
    static TreeNode createInstanceTreeNode() {
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

        TreeNode root = createInstanceTreeNode();
        fontSearch(root, 0);

        List<Integer> list = new ArrayList<>();
        ;
        printPath(root, root.right.right, list);


        System.out.println(binaryTreePaths(root));

    }

    static List<String> binaryTreePaths(TreeNode root) {
        List<String> paths = new ArrayList<String>();
        constructPaths(root, "", paths);
        return paths;
    }

    static void constructPaths(TreeNode root, String path, List<String> paths) {
        if (root != null) {
            StringBuffer pathSB = new StringBuffer(path);
            pathSB.append(root.val);
            if (root.left == null && root.right == null) {  // 当前节点是叶子节点
                paths.add(pathSB.toString());  // 把路径加入到答案中
            } else {
                pathSB.append("->");  // 当前节点不是叶子节点，继续递归遍历
                constructPaths(root.left, pathSB.toString(), paths);
                constructPaths(root.right, pathSB.toString(), paths);
            }
        }
    }


    static void printPath(TreeNode node, TreeNode search, List<Integer> list) {
        if(node==search){
           list.add(node.val);
          //  System.out.println(list+":"+node.val);

            return;
        }
        if (node == null) {
            //  list.clear();
            return;
        }

        list.add(node.val);
        if (node.left == null && node.right == null) {

            System.out.println(list+":"+node.val);

            //list.add(node.val);
        } else {


            Stream.of(node.left, node.right)
                    .forEach(t -> printPath(t, search, list));
        }
    }


    // 前序遍历：根结点 ---> 左子树 ---> 右子树
    static void fontSearch(TreeNode node, int level) {
        if (node != null) {
            IntStream.range(0, level).forEach(t -> System.out.print("-"));
            System.out.println("当前节点值为:" + node.val);
            fontSearch(node.left, level + 1);
            fontSearch(node.right, level + 1);
        }
    }

    //中序遍历：左子树---> 根结点 ---> 右子树
    static void midSearch(TreeNode node) {

    }
}
