package com.psl.coding.dubbo.consumer.adaptive;


public class Main{
    public static void main(String[] args){

        ListNode a = new ListNode(1);
        ListNode b = new ListNode(2);
        ListNode c = new ListNode(3);
        ListNode d = new ListNode(4);
        ListNode e = new ListNode(5);
        ListNode f = new ListNode(6);
        ListNode g = new ListNode(7);

        a.next = b;
        b.next = c;
        c.next = d;
        d.next = e;
        e.next = f;
        f.next = g;

        ListNode result = solution(a,2);

//         ListNode result = rever(a,c);
        while(result != null){
            System.out.println(result.val);
            result = result.next;
        }

    }
    public static ListNode solution(ListNode head,int k){
        if(head == null || k<=0){
            return head;
        }

        ListNode hair =  new ListNode();
        hair.next = head;

        int cnt = 1;
        ListNode pre = hair;
        ListNode h = head;
        while(h!= null){
            if(cnt % k == 0){
                ListNode h1 = pre.next;
                ListNode tail = h.next;
                ListNode temp = rever(h1,tail);

                pre.next = temp;
                h1.next = tail;
                pre = h1;
                h = tail;

            }else{
                h = h.next;

            }
            cnt++;
        }
        return hair.next;
    }

    public static ListNode rever(ListNode head,ListNode tail){
        ListNode pre = null;
        ListNode h = head;
        while(h!=null && h != tail){
            ListNode next = h.next;
            h.next = pre;
            pre = h;
            h = next;
        }
        return pre;
    }

    public static class ListNode{
        public ListNode next;
        public int val;
        public ListNode(int val){
            this.val = val;
        }
        public ListNode(){
        }
    }
}