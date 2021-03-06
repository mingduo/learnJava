package leecode.utils;

import lombok.ToString;

@ToString
public class ListNode {
    public int val;
    public ListNode next;

    public ListNode(int x) {
        val = x;
    }


    public static ListNode buildNode(){
        ListNode one = new ListNode(3);
        one.next=new ListNode(2);
        one.next.next=new ListNode(0);
        one.next.next.next=new ListNode(-4);

        return one;
    }
}