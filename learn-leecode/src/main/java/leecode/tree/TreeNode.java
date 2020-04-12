package leecode.tree;

import lombok.Data;

/**
 * 
 * @apiNode:
 * @since 2020/4/3
 * @author : weizc 
 */
@Data
public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }

}
