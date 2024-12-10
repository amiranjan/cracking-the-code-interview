#["LRUCache","put","put","get","put","get","put","get","get","get"]
#[[2],[1,1],[2,2],[1],[3,3],[2],[4,4],[1],[3],[4]]

class Node:
    def __init__(self, key, value):
        self.key = key
        self.value = value
        self.previous = None
        self.next = None

class LRUCache:
    def __init__(self, capacity: int):
        self.capacity = capacity
        self.head = Node(-1, -1)
        self.tail = Node(-1, -1)
        self.head.next = self.tail
        self.tail.previous = self.head
        self.dict = {}

    def get(self, key: int) -> int:
        result = -1
        if key in self.dict.keys():
            node = self.dict[key]
            result = node.value

            del self.dict[key]
            self.__removeNode(node)
            self.capacity += 1

            self.__addNode(node)
            self.dict[key] = self.head.next
            self.capacity -= 1
        return result

    def put(self, key: int, value: int) -> None:
        if key in self.dict.keys():
            node = self.dict.pop(key)
            self.__removeNode(node)
            self.capacity += 1

        if self.capacity <= 0:
            node = self.tail.previous
            del self.dict[node.key]
            self.__removeNode(node)
            self.capacity += 1

        node = Node(key, value)
        self.__addNode(node)
        self.dict[key] = self.head.next
        self.capacity -= 1

    def __addNode(self, new_node: Node) -> None:
        temp = self.head.next
        new_node.next = temp
        new_node.previous = self.head
        self.head.next = new_node
        temp.previous = new_node

    def __removeNode(self, node: Node) -> None:
        prevv = node.previous
        nextt = node.next

        prevv.next = nextt
        nextt.previous = prevv


# Your LRUCache object will be instantiated and called as such:
# obj = LRUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)