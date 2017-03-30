package ru.academits.novoselovda.list;

public class List<E> {
    private int length;
    private Node head;

    public List() {
        this.head = new Node();
        length = 0;
    }

    public int getLength() {
        return length;
    }

    public Node getFirstNode() {
        return head.getNext();
    }

    public Node getNode(int userIndex) {
        testOfEntrance(userIndex);
        int index = 0;
        for (Node p = head.getNext(); p != null; p = p.getNext()) {
            if (index == userIndex) {
                return p;
            }
            ++index;
        }
        return null;
    }

    public String getNodeData(int userIndex) {
        return getNode(userIndex).toString();
    }

    public void setNodeData(E userData, int userIndex) {
        Node temp = getNode(userIndex);
        if (temp != null) {
            temp.setData(userData);
        }
    }

    public void dellNode(int userIndex) {
        testOfEntrance(userIndex);
        if (userIndex > 0) {
            Node temp = getNode(userIndex - 1);
            temp.setNext(temp.getNext().getNext());
        } else {
            head.setNext(head.getNext().getNext());
        }
        --length;
    }

    public void insertNode(Node userNode, int userIndex) {
        if (userIndex > 0) {
            Node temp = getNode(userIndex - 1);
            userNode.setNext(temp.getNext());
            temp.setNext(userNode);
        } else {
            if (length > 0) {
                userNode.setNext(head.getNext());
            }
            head.setNext(userNode);
        }
        ++length;
    }

    public void dellNode(E userData) {
        for (Node p = head; p.getNext() != null; p = p.getNext()) {
            if (p.getNext().getData().equals(userData)) {
                if (p.getNext().getNext() != null) {
                    p.setNext(p.getNext().getNext());
                } else {
                    p.setNext(null);
                }
                --length;
                break;
            }
        }
    }

    public void dellNexNode(Node userNode) {
        if (userNode.getNext() != null) {
            userNode.setNext(userNode.getNext().getNext());
            --length;
        } else {
            throw new IllegalArgumentException("ОШИБКА: не удалось удалить следующий узел, так как его нет");
        }

    }

    public void insertNextNode(Node nodeToInsert, Node userNode) {
        Node temp = userNode.getNext();
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
            boolean isFirst = true;
            for (Node prevPrev = head, prev = prevPrev.getNext(), p = prev.getNext();
                 p != null; prevPrev = prevPrev.getNext(), prev = p, p = p.getNext()) {
                if (isFirst) {
                    prev.setNext(null);
                    isFirst = false;
                } else {
                    prev.setNext(prevPrev);
                }
                if (p.getNext() == null) {
                    head.setNext(p);
                    p.setNext(prev);
                    break;
                }
            }
        }
    }

    public int getIndex(Node node) {
        int index = 0;
        for (Node p = head.getNext(); p != null; p = p.getNext()) {
            if (p.equals(node)) {
                return index;
            }
            ++index;
        }
        return -1;
    }

    public List cloneList() {
        Node[] nodes = new Node[length];
        for (int i = 0; i < length; i++) {
            nodes[i] = new Node();
        }
        int link;
        int index = 0;
        List newList = new List();
        for (Node p = head.getNext(); p != null; p = p.getNext()) {
            link = (p.getRandomNext() != null) ? getIndex(p.getRandomNext()) : -1;
            nodes[index].setData(p.getData());
            nodes[index].setRandomNext((link != -1) ? nodes[link] : null);
            newList.insertNode(nodes[index], index);
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
            for (Node p = head.getNext(); p != null; p = p.getNext()) {
                listToString.append(p.getData()).append("(").append(index).append("), ");
                ++index;
            }
            listToString.delete(listToString.length() - 2, listToString.length()).append("]");
            return listToString.toString();
        } else {
            return "список пуст";
        }
    }

    private void testOfEntrance(int index) {
        if (index < 0 || index >= length) {
            throw new IllegalArgumentException("ОШИБКА: индекс в списке не найден");
        }
    }
}
