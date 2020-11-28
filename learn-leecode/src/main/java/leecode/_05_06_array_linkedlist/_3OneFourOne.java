package leecode._05_06_array_linkedlist;

import leecode.utils.ListNode;

import java.util.HashSet;

/**
 * https://leetcode-cn.com/problems/linked-list-cycle/
 */
public class _3OneFourOne {
    /**
     * 快慢指针 空间复杂度O(1),时间复杂度O(N)
     *
     * @param head
     * @return
     */
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        ListNode fast = head, slow = head;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                return true;
            }
        }
        return false;
    }

    /**
     * set判断重复。空间复杂度O(N),时间复杂度O(N)
     * @param head
     * @return
     */
    public static boolean hasCycleBySet(ListNode head) {
        HashSet<Integer> set = new HashSet<>();
        while (head!=null){

            boolean add = set.add(head.val);
            if(!add){
                return false;
            }
            head=head.next;

        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(hasCycleBySet(ListNode.buildNode()));

    }
}