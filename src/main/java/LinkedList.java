public class LinkedList {
	/**
	 * Defining a class to represent a node of a Linked List.
	 * It holds a value represented by data variable, it also holds reference of the next node represented by next.
	 * */
    static class Node<T> {
		T data;
		Node<T> next;
		
		Node(T data) {
			this.data = data;
			next = null;
		}
    }

    /**
     * 2.1 Remove Dups: Write code to remove duplicates from an unsorted linked list.
     * FOLLOW UP
     * How would you solve this problem if a temporary buffer is not allowed?
     * */
	 private static <T> void removeDups(Node<T> head) {
		 Node<T> current = head;
		 while (current != null) {
			 Node<T> runner = current;
			 while (runner.next != null) {
				 if (runner.next.data == current.data) {
				     runner.next = runner.next.next;
				 } else {
					 runner = runner.next;
				 }
			 }
			 current = current.next;
		 }
	 }

	 /**
	  * 2.2 Return Kth to Last: Implement an algorithm to find the kth to last element of a singly linked list.
	  * */
	private static <T> T lastElement(Node<T> head, int k) {
		int size = length(head);
		
		Node<T> runner = head;
		int count = 0;
		T val = null;
		while (runner != null) {
			if (count == size - k) {
				val = runner.data;
				break;
			}
			count++;
			runner = runner.next;
		}
		return val;
	}

	/**
	 * 2.3 Delete Middle Node: Implement an algorithm to delete a node in the middle (i.e., any node but
	 * the first and last node, not necessarily the exact middle) of a singly linked list, given only access to
	 * that node.
	 * EXAMPLE
	 * lnput:the node c from the linked list a->b->c->d->e->f
	 * Result: nothing is returned, but the new linked list looks like a ->b->d- >e- >f
	 * */
	private static <T> Node<T> deleteMiddle(Node<T> head) {
		int size = length(head);
		
		Node<T> runner = head;
		int count = 1;
		int middle = size % 2 == 0 ? size / 2 : (size + 1) / 2;
		while (runner.next != null ) {
			count += 1;
			if (count == middle) {
				runner.next = runner.next.next;
			} else {
				runner = runner.next;
			}
		}
		return head;
	}

	/**
	 * 2.4 Partition: Write code to partition a linked list around a value x, such that all nodes less than x come
	 * before all nodes greater than or equal to x. If x is contained within the list the values of x only need
	 * to be after the elements less than x (see below). The partition element x can appear anywhere in the
	 * "right partition"; it does not need to appear between the left and right partitions.
	 * EXAMPLE
	 * Input: 3 -> 5 -> 8 -> 5 -> 10 -> 2 -> 1 [partition= 5]
	 * Output: 3 -> 1 -> 2 -> 10 -> 5 -> 5 -> 8
	 * */
	private static Node<Integer> partition(Node<Integer> head, int x) {
		Node<Integer> runner = head;
		Node<Integer> left = null, leftRunner = null;
		Node<Integer> right = null, rightRunner = null;
		
		while (runner != null) {
			if (runner.data < x) {
				if (leftRunner == null) {
					leftRunner = new Node<>(runner.data);
					left = leftRunner;
				}
				else {
					leftRunner.next = new Node<>(runner.data);
					leftRunner = leftRunner.next;
				}
			} else {
				if (rightRunner == null) {
					rightRunner = new Node<>(runner.data);
					right = rightRunner;
				}
				else {
					rightRunner.next = new Node<>(runner.data);
					rightRunner = rightRunner.next;
				}
			}
			runner = runner.next;
		}
		if (leftRunner != null)
			leftRunner.next = right;
		else
			left = right;
		return left;
	}

	/**
	 * 2.5 Sum Lists: You have two numbers represented by a linked list, where each node contains a single
	 * digit. The digits are stored in reverse order, such that the 1 's digit is at the head of the list. Write a
	 * function that adds the two numbers and returns the sum as a linked list.
	 * EXAMPLE
	 * Input: (7-> 1 -> 6) + (5 -> 9 -> 2).That is,617 + 295.
	 * Output: 2 -> 1 -> 9. That is, 912.
	 * FOLLOW UP
	 * Suppose the digits are stored in forward order. Repeat the above problem.
	 * Input: (6 -> 1 -> 7) + (2 -> 9 -> 5).That is,617 + 295.
	 * Output: 9 -> 1 -> 2. That is, 912.
	 * */
	private static Node<Integer> addList(Node<Integer> n1, Node<Integer> n2, Integer carry) {
		if (n1 == null && n2 == null && carry == 0)
			return null;
		
		Integer value = carry;
		if (n1 != null)
			value += n1.data;
		if (n2 != null)
			value += n2.data;
		
		Node<Integer> head = new Node<>(value >= 10 ? value - 10 : value);
		
		head.next = addList(n1 == null ? null : n1.next, n2 == null ? null : n2.next, value >= 10 ? 1 : 0);
		return head;
	}

	static class PartialSum {
		Node<Integer> sum;
		int carry;
	}

	private static Node<Integer> addListForward(Node<Integer> n1, Node<Integer> n2) {
		int l1 = length(n1);
		int l2 = length(n2);
		
		if (l1 < l2)
			n1 = lpad(n1, 0, l2 - l1);
		else
			n2 = lpad(n2, 0, l1 - l2);
		
		PartialSum sum = addListHelper(n1, n2);
		
		if (sum.carry == 0)
			return sum.sum;
		else
			return lpad(sum.sum, sum.carry, 1);
	}
	
	private static PartialSum addListHelper(Node<Integer> n1, Node<Integer> n2) {
		if (n1 == null && n2 == null)
			return new PartialSum();
		
		PartialSum sum = addListHelper(n1.next, n2.next);
		
		int val = sum.carry + n1.data + n2.data;
		
		sum.sum = lpad(sum.sum, val % 10, 1);
		sum.carry = val / 10;
		
		return sum;
	}

	/**
	 * 2.6 Palindrome: Implement a function to check if a linked list is a palindrome.
	 * */
	private static <T> boolean isPalindrome(Node<T> head) {
		if (head == null)
			return false;
		
		int size = length(head);
		for (int i = 1; i <= size/2; i++)
			if (get(head, i) != lastElement(head, i)) return false;
		
		return true;
	}

	/**
	 * 2.7 Intersection: Given two (singly) linked lists, determine if the two lists intersect. Return the
	 * intersecting node. Note that the intersection is defined based on reference, not value. That is, if the
	 * kth node of the first linked list is the exact same node (by reference) as the jth node of the second
	 * linked list, then they are intersecting.
	 * */
	private static <T> Node<T> intersection(Node<T> n1, Node<T> n2) {
		if (n1 == null || n2 == null)
			return null;

		TailAndLength<T> tailAndLength1 = tailAndLength(n1);
		TailAndLength<T> tailAndLength2 = tailAndLength(n2);

		if (tailAndLength1.tail != tailAndLength2.tail)
			return null;

		Node<T> shorter = tailAndLength1.length < tailAndLength2.length ? n1 : n2;
		Node<T> longer = tailAndLength1.length < tailAndLength2.length ? n2 : n1;
		
		longer = getNthNode(longer, Math.abs(tailAndLength1.length - tailAndLength2.length) + 1);
		
		while (shorter != longer) {
			shorter = shorter.next;
			longer = longer.next;
		}
		
		return longer;
	}

	/**
	 * 2.8 Loop Detection: Given a circular linked list, implement an algorithm that returns the node at the
	 * beginning of the loop.
	 * DEFINITION
	 * Circular linked list: A (corrupt) linked list in which a node's next pointer points to an earlier node, so
	 * as to make a loop in the linked list.
	 * EXAMPLE
	 * Input: A - > B - > C - > D - > E - > C [the same C as earlier]
	 * Output: C
	 * */
	private static <T> T firstElementInLoop(Node<T> head) {
		Node<T> slowRunner = head;
		Node<T> fastRunner = head;

		while (slowRunner != null && fastRunner != null) {
			slowRunner = slowRunner.next;
			fastRunner = fastRunner.next.next;
			if (slowRunner == fastRunner)
				break;
		}

		if (fastRunner == null || slowRunner == null)
			return null;

		slowRunner = head;
		while (slowRunner != fastRunner) {
			slowRunner = slowRunner.next;
			fastRunner = fastRunner.next;
		}

		return fastRunner.data;
	}

    public static void main(String[] args) {
		System.out.println("************* Remove Duplicate ******************");
		Node<Integer> head = prepareData();
		print(head);
		removeDups(head);
		print(head);
		System.out.println("*************************************************");
		
		System.out.println("************* Kth element ******************");
		head = prepareData();
		print(head);
		System.out.println(lastElement(head, 2));
		System.out.println("*************************************************");
		
		System.out.println("************* Delete middle ******************");
		head = prepareData();
		print(head);
		print(deleteMiddle(head));
		System.out.println("*************************************************");
		
		System.out.println("************* Partitioning ******************");
		head = prepareParitionData();
		print(head);
		print(partition(head, 5));
		System.out.println("*************************************************");
		
		System.out.println("************* Sum ******************");
		Node<Integer> n1 = createList(617, true);
		print(n1);
		Node<Integer> n2 = createList(295, true);
		print(n2);
		Node<Integer> sum = addList(n1, n2, 0);
		print(sum);

		n1 = createList(6175, false);
		print(n1);
		n2 = createList(295, false);
		print(n2);
		sum = addListForward(n1, n2);
		print(sum);
		System.out.println("*************************************************");
		
		System.out.println("************* Is Palindrome ******************");
		head = createList(1223221, false);
		print(head);
		System.out.println("Is Palindrome: " + isPalindrome(head));
		
		head = createList(122221, false);
		print(head);
		System.out.println("Is Palindrome: " + isPalindrome(head));
		
		head = createList(1223251, false);
		print(head);
		System.out.println("Is Palindrome: " + isPalindrome(head));
		System.out.println("*************************************************");
		
		System.out.println("************* Intersection ******************");
		Node<Integer> subList = createList(721, false);
		Node<Integer> list1 = createList(3159, false);
		list1 = append(list1, subList);
		print(list1);
		Node<Integer> list2 = createList(46, false);
		list2 = append(list2, subList);
		print(list2);
		Node<Integer> common = intersection(list1, list2);
		print(common);
		System.out.println("*************************************************");
		
		System.out.println("************* First Element of Circular List ******************");
		Node<Character> list = createList("ABC", false);
		Node<Character> tail = tailAndLength(list).tail;
		tail.next = new Node<>('D');
		tail.next.next = new Node<>('E');
		tail.next.next.next = tail;
		System.out.println("First element = " + firstElementInLoop(list));
		System.out.println("*************************************************");
    }
	
	private static Node<Integer> prepareData() {
		Node<Integer> n1 = new Node<>(1);
		Node<Integer> n2 = new Node<>(1);
		Node<Integer> n3 = new Node<>(2);
		Node<Integer> n4 = new Node<>(3);
		Node<Integer> n5 = new Node<>(1);
		Node<Integer> n6 = new Node<>(2);
		
		n1.next = n2;
		n2.next = n3;
		n3.next = n4;
		n4.next = n5;
		n5.next = n6;
		
		return n1;
	}
	
	private static Node<Integer> prepareParitionData() {
		Node<Integer> n1 = new Node<>(3);
		Node<Integer> n2 = new Node<>(5);
		Node<Integer> n3 = new Node<>(8);
		Node<Integer> n4 = new Node<>(5);
		Node<Integer> n5 = new Node<>(10);
		Node<Integer> n6 = new Node<>(2);
		Node<Integer> n7 = new Node<>(1);
		Node<Integer> n8 = new Node<>(4);
		Node<Integer> n9 = new Node<>(6);
		
		n1.next = n2;
		n2.next = n3;
		n3.next = n4;
		n4.next = n5;
		n5.next = n6;
		n6.next = n7;
		n7.next = n8;
		n8.next = n9;
		
		return n1;
	}
	
	private static <T> void print(Node<T> head) {
		Node<T> runner = head;
		StringBuilder sb = new StringBuilder();
		sb.append(runner.data);
		while (runner.next != null) {
			runner = runner.next;
			sb.append(" -> ");
			sb.append(runner.data);
		}
		System.out.println(sb.toString());
	}
	
	static class TailAndLength<T> {
		int length;
		Node<T> tail;
		
		public TailAndLength(int length, Node<T> tail) {
			this.length = length;
			this.tail = tail;
		}
	}
	
	private static <T> int length(Node<T> head) {
		TailAndLength<T> tailAndLength = tailAndLength(head);
		return tailAndLength.length;
	}
	
	private static <T> TailAndLength<T> tailAndLength(Node<T> head) {
		Node<T> runner = head;
		Node<T> tail = null;
		int length = 0;
		while (runner != null) {
			length++;
			if (runner.next == null)
				tail = runner;
			runner = runner.next;
		}
		
		return new TailAndLength<>(length, tail);
	}
	
	private static Node<Character> createList(String str, boolean reverse) {
		Node<Character> head = null, runner = null;
		if (reverse)
			for (int i = str.length() - 1; i >= 0; i--)
				if (runner == null) {
					runner = new Node<>(str.charAt(i));
					head = runner;
				} else {
					runner.next = new Node<>(str.charAt(i));
					runner = runner.next;
				}
		else
			for (int i = 0; i < str.length(); i++)
				if (runner == null) {
					runner = new Node<>(str.charAt(i));
					head = runner;
				} else {
					runner.next = new Node<>(str.charAt(i));
					runner = runner.next;
				}
		return head;
	}
	
	private static Node<Integer> createList(Integer num, boolean reverse) {
		num = reverse ? num : reverse(num);
		Node<Integer> head = null, runner = null;
		while(num != 0) {
			if (runner == null) {
				runner = new Node<>(num % 10);
				head = runner;
			} else {
				runner.next = new Node<>(num % 10);
				runner = runner.next;
			}
			num /= 10;
		}
		return head;
	}
	
	private static Integer reverse(Integer num) {
		int sum = 0;
		while(num != 0) {
			sum = sum * 10 + num % 10;
			num /= 10;
		}
		return sum;
	}
	
	private static <T> Node<T> lpad(Node<T> head, T val, int length) {
		for (int i = 0; i< length; i++) {
			Node<T> node = new Node<>(val);
			node.next = head;
			head = node;
		}
		return head;
	}
	
	private static <T> T get(Node<T> head, int n) {
		Node<T> node = getNthNode(head, n);
	    return node == null ? null : node.data;
	}
	
	private static <T> Node<T> getNthNode(Node<T> head, int n) {
		Node<T> runner = head;
		int count = 0;
		while (runner != null) {
			count++;
			if (count == n)
				return runner;
			runner = runner.next;
		}
	    return null;
	}
	
	private static <T> Node<T> append(Node<T> n1, Node<T> n2) {
		Node<T> runner = n1;
		while (runner != null) {
			if (runner.next == null) {
				runner.next = n2;
				break;
			}
			runner = runner.next;
		}
		return n1;
	}
}
