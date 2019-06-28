package sj.leetcode;

import sj.util.ListNode;

/**
 * @author: Jian Shi
 * @email: shijianhzchina@gmail.com
 * @date: 12/9/2018 14:16
 */

public class ListOperations {

    public static void main(String[] args) {
        //linkedList 1
        ListNode head1 = new ListNode(1);
        ListNode node1 = new ListNode(2);
        ListNode node2 = new ListNode(3);
        head1.setNext(node1);
        node1.setNext(node2);
        node2.setNext(null);
        //linkedList 2 intersect with 1
        ListNode head2 = new ListNode(5);
        head2.setNext(node1);


        //linkedList 3
        ListNode head3 = new ListNode(1);
        ListNode node3 = new ListNode(2);
        ListNode node4 = new ListNode(3);
        ListNode node5 = new ListNode(4);
        ListNode node6 = new ListNode(5);
        head3.setNext(node3);
        node3.setNext(node4);
        node4.setNext(node5);
        node5.setNext(node6);
        node6.setNext(null);

        System.out.println("List 1:");
        printList(head1);
        System.out.println("List 2:");
        printList(head3);

        //printList(reverse2(head2));
        //System.out.println(getIntersectionNode(head1, head2).val);
        //printList(mergeTwoLists(head1, head3));
        //printList(deleteDuplicates(head3));
        //printList(removeNthFromEnd(head1, 3));
        //printList(swapPairs(head3));
        //printList(oddEvenList(head3));
        printList(addTwoNumbers(head1, head3));

    }

    public static int countLength(ListNode head) {
        int length = 0;
        while (head != null) {
            length++;
            head = head.next;
        }
        return length;
    }

    public static boolean isEven(int num) {
        return num % 2 == 0;
    }

    public static void printList(ListNode head) {
        while (head != null) {
            System.out.print(head.val + " -> ");
            head = head.next;
        }
        System.out.println("null");
    }

    public static ListNode reverse(ListNode head) {
        if (head == null) {
            return null;
        }
        if (head.next == null) {
            return head;
        }

        ListNode next = head.next;
        ListNode finalHead = reverse(next);
        next.next = head;
        head.next = null;
        return finalHead;
    }

    public static ListNode reverse2(ListNode head) {
        ListNode pre = null;
        ListNode cur = head;
        ListNode next;
        while (cur != null) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    public static ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode l1 = headA;
        ListNode l2 = headB;
        while (l1 != l2) {
            l1 = l1 == null ? headB : l1.next;
            l2 = l2 == null ? headA : l2.next;
        }
        return l1;
    }

    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode head;
        ListNode cur;
        if (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                cur = l1;
                l1 = l1.next;
            } else {
                cur = l2;
                l2 = l2.next;
            }
        } else if (l1 != null) {
            return l1;
        } else {
            return l2;
        }
        head = cur;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                cur.next = l1;
                l1 = l1.next;
            } else {
                cur.next = l2;
                l2 = l2.next;
            }
            cur = cur.next;
        }
        if (l1 == null && l2 != null) {
            cur.next = l2;
        } else if (l1 != null && l2 == null) {
            cur.next = l1;
        }
        return head;
    }

    public static ListNode deleteDuplicates(ListNode head) {
        ListNode cur = head;
        while (cur != null && cur.next != null) {
            if (cur.val == cur.next.val) {
                cur.next = cur.next.next;
            } else {
                cur = cur.next;
            }
        }
        return head;
    }

    // two - pass
    public static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode cur = new ListNode(0);
        cur.next = head;
        int length = countLength(head);
        if (length <= 1) {
            return null;
        }
        if (length - n == 0) {
            return head.next;
        }
        for (int i = 0; i < length - n; i++) {
            cur = cur.next;
        }
        cur.next = cur.next.next;
        return head;
    }

    public static ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode newHead = head.next;
        ListNode first = head;
        ListNode second = head.next.next;
        newHead.next = head;
        first.next = second;
        while (second != null && second.next != null) {
            first.next = second.next;
            second.next = second.next.next;
            first.next.next = second;
            first = first.next.next;
            second = second.next;
        }
        return newHead;
    }

    public static ListNode oddEvenList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode odd = head;
        ListNode evenHead = head.next;
        ListNode even = head.next;
        while (even.next != null) {
            odd.next = even.next;
            odd = even.next;
            even.next = odd.next;
            if(odd.next == null) break;
            even = odd.next;
        }
        odd.next = evenHead;
        return head;
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int tens = 0;
        ListNode head;
        ListNode cur = new ListNode(0);
        if (l1 != null && l2 != null) {
            if (l1.val + l2.val > 9) {
                cur.val = l1.val + l2.val - 10;
                tens = 1;
                l1 = l1.next;
                l2 = l2.next;
            } else {
                cur.val = l1.val + l2.val;
                l1 = l1.next;
                l2 = l2.next;
            }
        } else if (l1 != null) {
            return l1;
        } else {
            return l2;
        }
        head = cur;
        while(l1 != null && l2 != null){
            if (l1.val + l2.val + tens > 9){
                ListNode next = new ListNode(l1.val + l2.val + tens - 10);
                cur.next = next;
                tens = 1;
                l1 = l1.next;
                l2 = l2.next;
                cur = cur.next;
            }else{
                ListNode next = new ListNode(l1.val + l2.val + tens);
                cur.next = next;
                tens = 0;
                l1 = l1.next;
                l2 = l2.next;
                cur = cur.next;
            }
        }
        while (l1 != null){
            if (l1.val + tens > 9){
                ListNode next = new ListNode(l1.val + tens - 10);
                cur.next = next;
                tens = 1;
                l1 = l1.next;
                cur = cur.next;
            }else{
                ListNode next = new ListNode(l1.val + tens);
                cur.next = next;
                tens = 0;
                l1 = l1.next;
                cur = cur.next;
            }
        }
        while (l2 != null){
            if (l2.val + tens > 9){
                ListNode next = new ListNode(l2.val + tens - 10);
                cur.next = next;
                tens = 1;
                l2 = l2.next;
                cur = cur.next;
            }else{
                ListNode next = new ListNode(l2.val + tens);
                cur.next = next;
                tens = 0;
                l2 = l2.next;
                cur = cur.next;
            }
        }

        if(l1 == null && l2 == null){
            if(tens == 1){
                ListNode next = new ListNode(1);
                cur.next = next;
            }
        }
        return head;
    }
}
