package leecode._05_06_array_linkedlist;

import leecode.utils.ListNode;

/**
 *
 *
 *  
 * @since 2020/10/23
 * @author : weizc 
 */
public class _2TwoFour {

    /**
     * 输入：head = [1,2,3,4]
     * 输出：[2,1,4,3]
     * 示例 2：
     *
     * 输入：head = []
     * 输出：[]
     * 示例 3：
     *
     * 输入：head = [1]
     * 输出：[1]
     *
     * 链接：https://leetcode-cn.com/problems/swap-nodes-in-pairs
     * @param head
     */
    public static ListNode mySwapPairs(ListNode head) {
        ListNode cur=head;
        while (cur!=null && cur.next!=null){
            int tmp=cur.val;
            cur.val=cur.next.val;
            cur.next.val=tmp;

            cur=cur.next.next;
        }
        return head;
    }


    public static void main(String[] args) {
        ListNode node = ListNode.buildNode();
        System.out.println(node);

        ListNode reverseNode = mySwapPairs(ListNode.buildNode());
        System.out.println(reverseNode);
    }
}
