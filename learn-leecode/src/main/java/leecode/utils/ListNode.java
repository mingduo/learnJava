package leecode.utils;

import lombok.ToString;

import java.util.List;

@ToString
public class ListNode {
    public int val;
    public ListNode next;

    public ListNode(int x) {
        val = x;
    }


    public static ListNode buildNode(){
        ListNode one = new ListNode(1);
        one.next=new ListNode(2);
        one.next.next=new ListNode(3);
        return one;
    }
}