package ru.academits.novoselovda.list;

import java.util.ArrayList;

public class List<T> {
    private int length;
    private Node<T> head;

    public List() {
        this.head = null;
        length = 0;
    }

    public int getLength() {
        return length;
    }

    public Node getFirstNode() {
        return head;
    }

    public Node<T> getNode(int userIndex) {
        testOfEntrance(userIndex);
        int index = 0;
        for (Node<T> p = head; p != null; p = p.getNext()) {
            if (index == userIndex) {
                return p;
            }
            ++index;
        }
        return null;
    }

    public T getNodeData(int userIndex) {
        return getNode(userIndex).getData();
    }

    public T setNodeData(T userData, int userIndex) {
        Node<T> temp = getNode(userIndex);
        T oldData = temp.getData();
        temp.setData(userData);
        return oldData;
    }

    public Node<T> deleteNode(int userIndex) {
        testOfEntrance(userIndex);
        Node<T> oldNode;
        if (userIndex > 0) {
            Node<T> temp = getNode(userIndex - 1);
            oldNode = temp;
            temp.setNext(temp.getNext().getNext());
        } else {
            oldNode = head;
            head = head.getNext();
        }
        --length;
        return oldNode;
    }

    public void insertNode(Node<T> userNode, int userIndex) {
        if (userIndex > 0) {
            Node<T> temp = getNode(userIndex - 1);
            userNode.setNext(temp.getNext());
            temp.setNext(userNode);
        } else {
            if (length > 0) {
                userNode.setNext(head);
                head = userNode;
            }
            head = userNode;
        }
        ++length;
    }

    public Node<T> deleteNode(T userData) {
        Node<T> oldNode;
        if (length == 1 && head.equals(userData)) {
            oldNode = head;
            head = null;
            --length;
            return oldNode;
        } else if (length > 1) {
            for (Node<T> p = head; p.getNext() != null; p = p.getNext()) {
                if (head.getData().equals(userData)) {
                    oldNode = head;
                    head = head.getNext();
                    --length;
                    return oldNode;
                } else if (p.getNext().getData().equals(userData)) {
                    oldNode = p.getNext();
                    if (p.getNext().getNext() != null) {
                        p.setNext(p.getNext().getNext());
                    } else {
                        p.setNext(null);
                    }
                    --length;
                    return oldNode;
                }
            }
        }
        return null;
    }

    public Node<T> deleteNextNode(Node<T> userNode) {
        if (userNode.getNext() != null) {
            Node<T> oldNode;
            oldNode = userNode.getNext();
            userNode.setNext(userNode.getNext().getNext());
            --length;
            return oldNode;
        } else {
            throw new IllegalArgumentException("ОШИБКА: не удалось удалить следующий узел, так как его нет");
        }
    }

    public void insertNextNode(Node<T> nodeToInsert, Node<T> userNode) {
        Node<T> temp = userNode.getNext();
        if (temp != null) {
            nodeToInsert.setNext(temp);
            userNode.setNext(nodeToInsert);
            ++length;
        } else {
            throw new IllegalArgumentException("ОШИБКА: отсутствует искомый элемент");
        }
    }

    public void reverseOfList() {
        if (length > 1) {
            for (Node<T> prevPrev = null, prev = head, p = prev.getNext();
                 p != null; prevPrev = prev, prev = p, p = p.getNext()) {
                prev.setNext(prevPrev);
                if (p.getNext() == null) {
                    head = p;
                    head.setNext(prev);
                    break;
                }
            }
        }
    }

    public int getIndex(Node<T> node) {
        int index = 0;
        for (Node p = head; p != null; p = p.getNext()) {
            if (p.equals(node)) {
                return index;
            }
            ++index;
        }
        return -1;
    }

    public List cloneList() {
        ArrayList<Node<T>> nodes = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            nodes.add(i, new Node<>());
        }
        int link;
        int index = 0;
        List<T> newList = new List<>();
        for (Node<T> p = head; p != null; p = p.getNext()) {
            link = (p.getRandomNext() != null) ? getIndex(p.getRandomNext()) : -1;
            nodes.get(index).setData(p.getData());
            nodes.get(index).setRandomNext((link != -1) ? nodes.get(link) : null);
            newList.insertNode(nodes.get(index), index);
            ++index;
        }
        return newList;
    }

    @Override
    public String toString() {
        if (length > 0) {
            StringBuilder listToString = new StringBuilder();
            listToString.append("[");
            int index = 0;
            for (Node p = head; p != null; p = p.getNext()) {
                listToString.append(p.getData()).append("(").append(index).append("), ");
                ++index;
            }
            listToString.delete(listToString.length() - 2, listToString.length()).append("], длиной ").append(length);
            return listToString.toString();
        } else {
            return "список пуст";
        }
    }

    private void testOfEntrance(int index) {
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException("ОШИБКА: индекс в списке не найден");
        }
    }
}
