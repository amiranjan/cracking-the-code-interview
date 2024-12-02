class Node:
    def __init__(self, data):
        self.data = data
        self.next = None

class LinkedList:

    def print(self, head):
        list_items = []
        runner = head
        list_items.append(repr(runner.data))
        while runner.next:
            runner = runner.next
            list_items.append(" -> ")
            list_items.append(repr(runner.data))
        print(''.join(list_items))

    def len(self, head):
        runner = head
        length = 0
        while runner:
            length += 1
            runner = runner.next
        return length

    def remove_duplicates(self, head):
        current = head
        while current:
            runner = current
            while runner.next:
                if runner.next.data == current.data:
                    runner.next = runner.next.next
                else:
                    runner = runner.next
            current = current.next
        return head

    def last_element(self, head, position):
        index, size = 0, self.len(head)
        runner = head
        while runner:
            index += 1
            if index == size - position:
                runner.next = runner.next.next
            runner = runner.next
        return head

    def delete_middle(self, head):
        index, size = 0, self.len(head)
        middle = size // 2 if size % 2 == 0 else 1 + size // 2
        runner = head
        while runner:
            index += 1
            if index == middle - 1:
                runner.next = runner.next.next
            runner = runner.next
        return head

    def partition(self, head, position):
        """2.4 Partition: Write code to partition a linked list around a value x, such that all nodes less than x come
            before all nodes greater than or equal to x. If x is contained within the list the values of x only need
            to be after the elements less than x (see below). The partition element x can appear anywhere in the
            "right partition"; it does not need to appear between the left and right partitions.

        EXAMPLE
            Input: 3 -> 5 -> 8 -> 5 -> 10 -> 2 -> 1 [partition= 5]
            Output: 3 -> 1 -> 2 -> 10 -> 5 -> 5 -> 8
        """
        return head

    def add_list(self, n1: Node, n2: Node, carry):
        """ 2.5 Sum Lists: You have two numbers represented by a linked list, where each node contains a single
            digit. The digits are stored in reverse order, such that the 1 's digit is at the head of the list. Write a
            function that adds the two numbers and returns the sum as a linked list.

        EXAMPLE
            Input: (7-> 1 -> 6) + (5 -> 9 -> 2).That is,617 + 295.
            Output: 2 -> 1 -> 9. That is, 912.

        FOLLOW UP
            Suppose the digits are stored in forward order. Repeat the above problem.
            Input: (6 -> 1 -> 7) + (2 -> 9 -> 5).That is,617 + 295.
            Output: 9 -> 1 -> 2. That is, 912.
        """
        if not n1 and not n2 and carry == 0:
            return None

        value = carry
        if n1:
            value += n1.data
        if n2:
            value += n2.data

        head = Node(value - 10 if value >= 10 else value)

        head.next = self.add_list(n1.next, n2.next, value // 10 if value >= 10 else 0)

        return head

    def add_list_forward(self, n1: Node, n2: Node) -> Node:
        l1 = self.len(n1)
        l2 = self.len(n2)
        if l1 < l2:
            n1 = self.__lpad(n1, 0, l2 - l1)
        else:
            n2 = self.__lpad(n2, 0, l1 - l2)

        sum = self.__add_list_helper(n1, n2)

        if sum.carry == 0:
            return sum.sum
        else:
            return self.__lpad(sum.sum, sum.carry, 1)

    def __lpad(self, head: Node, value, length):
        for _ in range(length):
            node = Node(value)
            node.next = head
            head = node
        return head

    class __PartialSum:
        sum: Node = None
        carry: int = 0

    def __add_list_helper(self, n1: Node, n2: Node):
        if not n1 and not n2:
            return self.__PartialSum()

        sum = self.__add_list_helper(n1.next, n2.next)

        val = sum.carry + n1.data + n2.data

        sum.sum = self.__lpad(sum.sum, val % 10, 1)
        sum.carry = val // 10

        return sum

    def is_palindrome(self, head: Node) -> bool:
        slow, fast = head, head

        # Find the middle of the list
        while fast.next and fast.next.next:
            slow = slow.next
            fast = fast.next.next

        # Split the list and reverse the second half
        head2 = self.reverse(slow.next)
        slow.next = None

        # Check if the two halves are identical
        ret = self.is_identical(head, head2)

        # Restore the original list
        head2 = self.reverse(head2)
        slow.next = head2

        return ret

    def is_palindrome_using_list(self, head: Node) -> bool:
        curr = head
        list_items = []

        # Find the middle of the list
        while curr:
            list_items.append(curr.data)
            curr = curr.next

        curr = head
        while curr:
            if curr.data != list_items.pop():
                return False
            curr = curr.next
        return True

    def reverse(self, head: Node) -> Node:
        prev, curr = None, head
        while curr:
            next = curr.next
            curr.next = prev
            prev = curr
            curr = next
        return prev

    def is_identical(self, n1: Node, n2: Node) -> bool:
        while n1 and n2:
            if n1.data != n2.data:
                return False
            n1 = n1.next
            n2 = n2.next
        return True

    def append(self, n1, n2):
        runner = n1
        while runner:
            if not runner.next:
                runner.next = n2
                break
            runner = runner.next
        return n1


    def intersection(self, n1: Node, n2: Node):
        """2.7 Intersection: Given two (singly) linked lists, determine if the two lists intersect. Return the
           intersecting node. Note that the intersection is defined based on reference, not value. That is, if the
           kth node of the first linked list is the exact same node (by reference) as the jth node of the second
           linked list, then they are intersecting.
        """
        if not n1 and not n2:
            return None

        n1_length, n1_tail = self.tailAndLength(n1)
        n2_length, n2_tail = self.tailAndLength(n2)

        if n1_tail != n2_tail:
            return None

        shorter = n1 if n1_length < n2_length else n2
        longer = n1 if n1_length > n2_length else n2

        longer = self.getNthNode(longer, abs(n1_length - n2_length) + 1)

        while shorter != longer:
            shorter = shorter.next
            longer = longer.next

        return longer

    def tailAndLength(self, head: Node):
        runner, tail = head, None
        length = 0
        while runner:
            length += 1
            if not runner.next:
                tail = runner
            runner = runner.next

        return length, tail

    def getNthNode(self, head: Node, n: int):
        runner = head
        count = 0
        while runner:
            count += 1
            if count == n:
                return runner
            runner = runner.next
        return None

    def firstElementInLoop(self, head):
        """2.8 Loop Detection: Given a circular linked list, implement an algorithm that returns the node at the
           beginning of the loop.
           DEFINITION
           Circular linked list: A (corrupt) linked list in which a node's next pointer points to an earlier node, so
           as to make a loop in the linked list.
           EXAMPLE
           Input: A - > B - > C - > D - > E - > C [the same C as earlier]
           Output: C
        """
        slow, fast = head, head

        while fast and slow:
            slow = slow.next
            fast = fast.next.next
            if slow == fast:
                break

        if not slow or not fast:
            return None

        slow = head
        while slow != fast:
            slow = slow.next
            fast = fast.next

        return slow.data

def prepareData():
    n1 = Node(1)
    n2 = Node(1)
    n3 = Node(2)
    n4 = Node(3)
    n5 = Node(1)
    n6 = Node(2)

    n1.next = n2
    n2.next = n3
    n3.next = n4
    n4.next = n5
    n5.next = n6

    return n1

def prepareParitionData():
    n1 = Node(3)
    n2 = Node(5)
    n3 = Node(8)
    n4 = Node(5)
    n5 = Node(10)
    n6 = Node(2)
    n7 = Node(1)
    n8 = Node(4)
    n9 = Node(6)

    n1.next = n2
    n2.next = n3
    n3.next = n4
    n4.next = n5
    n5.next = n6
    n6.next = n7
    n7.next = n8
    n8.next = n9

    return n1

def createListFromStr(s: str, reverse):
    head, runner = None, None
    if reverse:
        for  i in range(len(s) - 1, 0):
            if not runner:
                runner = Node(s[i])
                head = runner
            else:
                runner.next = Node(s[i])
                runner = runner.next
    else:
        for i in range(len(s)):
            if not runner:
                runner = Node(s[i])
                head = runner
            else:
                runner.next = Node(s[i])
                runner = runner.next
    return head

def createList(num: int, reverse):
    def reverse(num):
        result = 0
        while num > 0:
            result = result * 10 + num % 10
            num //= 10
        return reverse
    num = num if reverse else reverse(num)
    head, runner = None, None
    while num != 0:
        if runner:
            runner.next = Node(num % 10)
            runner = runner.next
        else:
            runner = Node(num % 10)
            head = runner
        num //= 10
    return head

if __name__ == "__main__":
    linked_list = LinkedList()

    print("************* Remove Duplicate ******************")
    head = prepareData()
    linked_list.print(head)
    deduped_data = linked_list.remove_duplicates(head)
    linked_list.print(deduped_data)
    print("*************************************************")

    print("************* Kth element ******************")
    head = prepareData()
    linked_list.print(head)
    linked_list.print(linked_list.last_element(head, 2))
    print("*************************************************")

    print("************* Delete middle ******************")
    head = prepareData()
    linked_list.print(head)
    linked_list.print(linked_list.delete_middle(head))
    print("*************************************************")

    print("************* Partitioning ******************")
    head = prepareParitionData()
    linked_list.print(head)
    print(linked_list.partition(head, 5))
    print("*************************************************")

    print("************* Sum ******************")
    n1 = createList(617, True)
    linked_list.print(n1)
    n2 = createList(295, True)
    linked_list.print(n2)
    sum = linked_list.add_list(n1, n2, 0)
    linked_list.print(sum)

    n1 = createList(6175, False)
    linked_list.print(n1)
    n2 = createList(295, False)
    linked_list.print(n2)
    sum = linked_list.add_list_forward(n1, n2)
    linked_list.print(sum)
    print("*************************************************")

    print("************* Is Palindrome ******************");
    head = createList(1223221, False)
    linked_list.print(head)
    print("Is Palindrome: {}".format(linked_list.is_palindrome(head)))
    print("Is Palindrome Using List: {}".format(linked_list.is_palindrome_using_list(head)))

    head = createList(122221, False)
    linked_list.print(head)
    print("Is Palindrome: {}".format(linked_list.is_palindrome(head)))
    print("Is Palindrome Using List: {}".format(linked_list.is_palindrome_using_list(head)))

    head = createList(1223251, False)
    linked_list.print(head)
    print("Is Palindrome: {}".format(linked_list.is_palindrome(head)))
    print("Is Palindrome Using List: {}".format(linked_list.is_palindrome_using_list(head)))
    print("*************************************************")

    print("************* Intersection ******************")
    subList = createList(721, False)
    list1 = createList(3159, False)
    list1 = linked_list.append(list1, subList)
    linked_list.print(list1)
    list2 = createList(46, False)
    list2 = linked_list.append(list2, subList)
    linked_list.print(list2)
    common = linked_list.intersection(list1, list2)
    linked_list.print(common)
    print("*************************************************")

    print("************* First Element of Circular List ******************")
    list = createListFromStr("ABC", False)
    linked_list.print(list)
    leng, tail = linked_list.tailAndLength(list)
    tail.next = Node('D')
    tail.next.next = Node('E')
    tail.next.next.next = tail
    print("First element = {}".format(linked_list.firstElementInLoop(list)))
    print("*************************************************")

