package leecode._05_06_array_linkedlist;

import leecode.utils.ListNode;

/**
 * @author weizc
 * <p>
 * https://leetcode-cn.com/problems/reverse-linked-list/
 * 反转链表
 */
public class _1TwoZeroSix {

    /**
     * 输入: 1->2->3->4->5->NULL
     * 输出: 5->4->3->2->1->NULL
     * 方法一：迭代
     * <p>
     * 假设存在链表 1 → 2 → 3 → Ø，我们想要把它改成 Ø ← 1 ← 2 ← 3。
     * <p>
     * 在遍历列表时，将当前节点的 next 指针改为指向前一个元素。
     * 由于节点没有引用其上一个节点，因此必须事先存储其前一个元素。在更改引用之前，
     * 还需要另一个指针来存储下一个节点。
     * 不要忘记在最后返回新的头引用！
     * <p>
     * 时间复杂度：O(n)，假设 nn 是列表的长度，时间复杂度是 O(n)O(n)。
     * 空间复杂度：O(1)。
     *
     * @param head
     * @return
     */
    public static ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode newHead = null;
        while (head != null) {
            ListNode next = head.next;
            head.next = newHead;
            newHead = head;
            head = next;
        }
        return newHead;
    }

    public static ListNode myReverseList(ListNode head) {

        if(head==null){
            return null;
        }
        ListNode prev=null;
        while (head!=null){
            ListNode cur=new ListNode(head.val);
            cur.next=prev;
            prev=cur;
            head=head.next;
        }
        return prev;
    }

    public static void main(String[] args) {
        ListNode node = ListNode.buildNode();
        System.out.println(node);

        ListNode reverseNode = reverseList(node);
        System.out.println(reverseNode);

        reverseNode = myReverseList(ListNode.buildNode());
        System.out.println(reverseNode);
    }

}
