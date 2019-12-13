package com.galaxy.flink.java.alg;


/**
 *
 * https://time.geekbang.org/column/article/41013
 *
 * 如果字符串是通过单链表来存储的，那该如何来判断是一个回文串呢？
 * 你有什么好的解决思路呢？
 * 相应的时间空间复杂度又是多少呢？
 *
 *
 * 使用快慢两个指针找到链表中点，慢指针每次前进一步，快指针每次前进两步。
 * 在慢指针前进的过程中，同时修改其next指针，使得链表前半部分反序，获得prev反序list。
 * 最后比较中点两侧的链表是否相等。
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 *
 *
 *
 * */
class ListNode {
         int val;
         ListNode next;
         ListNode(int x) { val = x; }
     }

public class Solution {

    public static void main(String[] args) {
        ListNode l1=new ListNode(1);
        ListNode l2=new ListNode(2);
        l1.next=l2;
        ListNode l3=new ListNode(3);
        l2.next=l3;
        ListNode l4=new ListNode(2);
        l3.next=l4;
        ListNode l5=new ListNode(1);
        l4.next=l5;


        Solution s=new Solution();
        boolean isPalindrome=s.isPalindrome(l1);
        System.out.println(isPalindrome);

        System.out.println(l1.val);
        System.out.println(l2.val);
        System.out.println(l3.val);
        System.out.println(l4.val);
        System.out.println(l5.val);

    }

    //判断是否是回文数字
    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }

        //prev用来将数字的前半部分反转，12321的前半部分反转 2->1->null
        ListNode prev = null;
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            ListNode next = slow.next;
            slow.next = prev;
            prev = slow;
            slow = next;
        }

        //如果有奇数个节点，则将show节点定为中心节点的下一个节点
        if (fast != null) {
            slow = slow.next;
        }

        //使用slow节点，继续向下遍历2->1->null,与prev节点2->1->null进行比较，判断是否为回文
        while (slow != null) {
            if (slow.val != prev.val) {
                return false;
            }
            slow = slow.next;
            prev = prev.next;
        }
        return true;
    }
}
