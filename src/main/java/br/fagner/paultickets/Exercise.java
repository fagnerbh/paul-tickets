package br.fagner.paultickets;

public class Exercise {
    static class Node{
    int data;
        Node next;
        Node(int data){this.data = data; this.next = null;}
        Node(int data, Node next){this.data = data; this.next = next;}
    }
  
    static class LinkedList{
        Node head;
        LinkedList(){this.head = null;}
        LinkedList(Node head){this.head = head;}
    }
    
    public LinkedList addTwoLinkedLists(LinkedList list1, LinkedList list2){
        // your code here
    	if (list1 == null && list2 == null) {
    		return null;
    	}
    	
    	if (list1 == null && (list2 != null)) {
    		return list2;
    	}
    	
    	if (list2 == null && (list1 != null)) {
    		return list1;
    	}    	
    	    	
    	addElements(null, list1.head, list2.head, 0);
    	
    	return list1;
    }

	private void addElements(Node previous1, Node head1, Node head2, int carrier) {
		if (head1 != null || head2 != null || carrier > 0) {
			int sumHeads = (head1 != null) ? head1.data : 0 + ((head2 != null) ? head2.data : 0) + carrier;
			
			if (head1 != null) {
			head1.data = sumHeads < 10 ? sumHeads : sumHeads - 10;
			
			} else {
				head1 = new Node(sumHeads < 10 ? sumHeads : sumHeads - 10);
				if (previous1 != null) {
					previous1.next = head1;
				}
			}
			
			carrier = sumHeads < 10 ? 0 : sumHeads % 10;
			
			addElements(head1, head1.next, head2.next, carrier);
		}
	}
	
	}