package ru.academits.novoselovda.linkedlist;

import java.util.*;

public class LinkedList<T> implements List<T>, Deque<T> {
    private int length;
    private Node firstNode;
    private Node lastNode;
    private int modificationsCount;

    private class Node {
        private Object data;
        private Node next;
        private Node previous;

        Node(Object data, Node previous, Node next) {
            this.data = data;
            this.next = next;
            this.previous = previous;
        }

        void setData(T t) {
            this.data = t;
        }

        void setNext(Node node) {
            this.next = node;
        }

        void setPrevious(Node node) {
            this.previous = node;
        }
    }

    public LinkedList() {
        length = 0;
        this.firstNode = null;
        this.lastNode = null;
        modificationsCount = 0;
    }

    @Override
    public Iterator<T> descendingIterator() {
        return new descendingItr();
    }

    private class descendingItr implements Iterator<T> {
        private final ListItr listItr = new ListItr(LinkedList.this.length);

        @Override
        public boolean hasNext() {
            return listItr.hasPrevious();
        }

        @Override
        public T next() {
            return listItr.previous();
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Itr();
    }

    @Override
    public ListIterator<T> listIterator() {
        return new ListItr(0);
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return new ListItr(index);
    }

    private class Itr implements Iterator<T> {
        Node nextElement = LinkedList.this.firstNode;
        int nextIndex = 0;
        int previousIndex = -1;
        int expectedModCount = modificationsCount;

        @Override
        public boolean hasNext() {
            return nextIndex < length;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            checkForModifications();
            Node temp = nextElement;
            nextElement = nextElement.next;
            ++nextIndex;
            previousIndex = nextIndex - 1;
            return objectToTypeT(temp.data);
        }

        void checkForModifications() {
            if (modificationsCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }
    }

    private class ListItr extends Itr implements ListIterator<T> {
        ListItr(int index) {
            super();
            if (index != length) {
                testOnEntrance(index);
            }
            nextElement = findNode(index);
            nextIndex = index;
            previousIndex = nextIndex - 1;
        }

        @Override
        public boolean hasPrevious() {
            return nextIndex > 0;
        }

        @Override
        public T previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            checkForModifications();
            nextElement = (nextElement == null ? nextElement = LinkedList.this.lastNode : nextElement.previous);
            --nextIndex;
            previousIndex = nextIndex - 1;
            return objectToTypeT(nextElement.data);
        }

        @Override
        public int nextIndex() {
            return nextIndex;
        }

        @Override
        public int previousIndex() {
            return previousIndex;
        }

        @Override
        public void remove() {
            if (length == 0) {
                throw new NoSuchElementException();
            }
            checkForModifications();
            Node temp = nextElement;
            if (LinkedList.this.firstNode == nextElement) {
                if (length > 0) {
                    nextElement = LinkedList.this.firstNode;
                } else {
                    nextElement = null;
                    nextIndex = -1;
                    previousIndex = nextIndex - 1;
                }
            } else if (LinkedList.this.lastNode == nextElement) {
                nextElement = nextElement.previous;
                --nextIndex;
                previousIndex = nextIndex - 1;
            } else {
                nextElement = nextElement.next;
            }
            LinkedList.this.removeThisNode(temp);
            expectedModCount = modificationsCount;
        }

        @Override
        public void set(T t) {
            checkForModifications();
            nextElement.setData(t);
        }

        @Override
        public void add(T t) {
            checkForModifications();
            LinkedList.this.insertNode(t, nextElement.previous, nextElement);
            nextElement = nextElement.previous;
            expectedModCount = modificationsCount;
        }
    }

    @Override
    public void addFirst(T t) {
        this.firstNode = new Node(t, null, this.firstNode);
        if (length == 0) {
            this.lastNode = this.firstNode;
        } else {
            this.firstNode.next.previous = this.firstNode;
        }
        ++length;
        ++modificationsCount;
    }

    @Override
    public void addLast(T t) {
        this.lastNode = new Node(t, this.lastNode, null);
        if (length == 0) {
            this.firstNode = this.lastNode;
        } else {
            this.lastNode.previous.next = this.lastNode;
        }
        ++length;
        ++modificationsCount;
    }

    @Override
    public boolean offerFirst(T t) {
        addFirst(t);
        return true;
    }

    @Override
    public boolean offerLast(T t) {
        addLast(t);
        return true;
    }

    @Override
    public T removeFirst() {
        if (length == 0) {
            return null;
        }
        Object tempData = firstNode.data;
        if (length == 1) {
            this.firstNode = null;
            this.lastNode = null;
        } else {
            this.firstNode = this.firstNode.next;
            this.firstNode.previous = null;
        }
        --length;
        ++modificationsCount;
        return objectToTypeT(tempData);
    }

    @Override
    public T removeLast() {
        if (length == 0) {
            return null;
        }
        Object tempData = lastNode.data;
        if (length == 1) {
            this.firstNode = null;
            this.lastNode = null;
        } else {
            this.lastNode = this.lastNode.previous;
            this.lastNode.next = null;
        }
        --length;
        ++modificationsCount;
        return objectToTypeT(tempData);
    }

    @Override
    public T pollFirst() {
        return removeFirst();
    }

    @Override
    public T pollLast() {
        return removeLast();
    }

    @Override
    public T getFirst() {
        if (length == 0) {
            return null;
        }
        return objectToTypeT(this.firstNode.data);
    }

    @Override
    public T getLast() {
        if (length == 0) {
            return null;
        }
        return objectToTypeT(this.lastNode.data);
    }

    @Override
    public T peekFirst() {
        return getFirst();
    }

    @Override
    public T peekLast() {
        return getLast();
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        for (Node p = this.firstNode; p != null; p = p.next) {
            if (Objects.equals(p.data, o)) {
                removeThisNode(p);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        for (Node p = this.lastNode; p != null; p = p.previous) {
            if (Objects.equals(p.data, o)) {
                removeThisNode(p);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean offer(T t) {
        return offerLast(t);
    }

    @Override
    public T remove() {
        return removeFirst();
    }

    @Override
    public T poll() {
        return removeFirst();
    }

    @Override
    public T element() {
        if (length == 0) {
            return null;
        }
        return objectToTypeT(this.firstNode.data);
    }

    @Override
    public T peek() {
        return element();
    }

    @Override
    public void push(T t) {
        addFirst(t);
    }

    @Override
    public T pop() {
        return removeFirst();
    }

    @Override
    public int size() {
        return length;
    }

    @Override
    public boolean isEmpty() {
        return length == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (Node p = this.firstNode; p != null; p = p.next) {
            if (Objects.equals(p.data, o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[this.length];
        int index = 0;
        for (Node p = this.firstNode; p != null; p = p.next) {
            array[index] = p.data;
            ++index;
        }
        return array;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T1> T1[] toArray(T1[] a) {
        if (a.length < length) {
            return (T1[]) Arrays.copyOf(this.toArray(), length, a.getClass());
        }
        System.arraycopy((T1[]) this.toArray(), 0, a, 0, length);
        if (a.length > length) {
            a[length] = null;
        }
        return a;
    }

    @Override
    public boolean add(T t) {
        addLast(t);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        return removeFirstOccurrence(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        boolean[] isVerified = new boolean[c.size()];
        int count = 0;
        for (Node p = this.firstNode; p != null; p = p.next) {
            int index = 0;
            for (Object object : c) {
                if (!isVerified[index] && Objects.equals(p.data, object)) {
                    isVerified[index] = true;
                    ++count;
                    if (count == c.size()) {
                        return true;
                    }
                }
                ++index;
            }
        }
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        for (Object object : c) {
            if (object == null) {
                addLast(null);
            } else {
                addLast(objectToTypeT(object));
            }
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        testOnEntrance(index);
        if (length == 0) {
            addAll(c);
            return true;
        }
        int i = 0;
        for (Node p = this.firstNode; p != null; p = p.next) {
            if (i == index) {
                Node tempLast = this.lastNode;
                this.lastNode = p.previous;
                for (Object object : c) {
                    addLast(objectToTypeT(object));
                }
                this.lastNode.next = p;
                p.previous = this.lastNode;
                this.lastNode = tempLast;
                return true;
            }
            ++i;
        }
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean isRemoved = false;
        Node temp;
        for (Node p = this.firstNode; p != null; p = temp) {
            temp = p.next;
            if (c.contains(p.data)) {
                removeThisNode(p);
                isRemoved = true;
            }
        }
        return isRemoved;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean isRetain = false;
        Node temp;
        for (Node p = this.firstNode; p != null; p = temp) {
            temp = p.next;
            if (!c.contains(p.data)) {
                removeThisNode(p);
                isRetain = true;
            }
        }
        return isRetain;
    }

    @Override
    public void clear() {
        this.firstNode = null;
        this.lastNode = null;
        this.length = 0;
    }

    @Override
    public T get(int index) {
        testOnEntrance(index);
        Node temp = findNode(index);
        if (temp == null) {
            return null;
        } else {
            return objectToTypeT(temp.data);
        }
    }

    @Override
    public T set(int index, T element) {
        testOnEntrance(index);
        Node temp = findNode(index);
        if (temp == null) {
            return null;
        } else {
            temp.setData(element);
            return objectToTypeT(temp.data);
        }
    }

    @Override
    public void add(int index, T element) {
        testOnEntrance(index);
        Node tempNext = findNode(index);
        if (tempNext != null) {
            insertNode(element, tempNext.previous, tempNext);
        }
    }

    @Override
    public T remove(int index) {
        testOnEntrance(index);
        Node temp = findNode(index);
        removeThisNode(temp);
        return (temp == null ? null : objectToTypeT(temp.data));
    }

    @Override
    public int indexOf(Object o) {
        testZeroLength();
        int index = 0;
        for (Node p = this.firstNode; p != null; p = p.next) {
            if (Objects.equals(p.data, o)) {
                return index;
            }
            ++index;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        testZeroLength();
        int index = length - 1;
        for (Node p = this.lastNode; p != null; p = p.previous) {
            if (Objects.equals(p.data, o)) {
                return index;
            }
            --index;
        }
        return -1;
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return null;
    }

    @SuppressWarnings("unchecked")
    private T objectToTypeT(Object object) {
        return (object == null ? null : (T) object);
    }

    private void testOnEntrance(int index) {
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void testZeroLength() {
        if (length == 0) {
            throw new NegativeArraySizeException();
        }
    }

    private Node findNode(int index) {
        if (index > length / 2) {
            int i = length - 1;
            for (Node p = this.lastNode; p != null; p = p.previous) {
                if (i == index) {
                    return p;
                }
                --i;
            }
        } else {
            int i = 0;
            for (Node p = this.firstNode; p != null; p = p.next) {
                if (i == index) {
                    return p;
                }
                ++i;
            }
        }
        return null;
    }

    private void insertNode(T t, Node previous, Node next) {
        Node temp = new Node(t, previous, next);
        previous.setNext(temp);
        next.setPrevious(temp);
        ++length;
        ++modificationsCount;
    }

    private void removeThisNode(Node node) {
        if (this.firstNode == node) {
            if (length == 1) {
                this.firstNode = null;
                this.lastNode = null;
            } else {
                this.firstNode = node.next;
                this.firstNode.previous = null;
                if (this.firstNode != this.lastNode) {
                    this.firstNode.next.previous = this.firstNode;
                } else {
                    this.lastNode = this.firstNode;
                }
            }
        } else if (this.lastNode == node) {
            this.lastNode = node.previous;
            this.lastNode.next = null;
            if (this.firstNode != this.lastNode) {
                this.lastNode.previous.next = this.lastNode;
            }
        } else {
            node.previous.next = node.next;
            node.next.previous = node.previous;
        }
        --length;
        ++modificationsCount;
    }

    @Override
    public String toString() {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("{");
        for (Node p = this.firstNode; p != null; p = p.next) {
            stringbuilder.append(objectToTypeT(p.data)).append(", ");
        }
        if (length > 0) {
            stringbuilder.delete(stringbuilder.length() - 2, stringbuilder.length());
        }
        stringbuilder.append("}");
        return stringbuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LinkedList<?> tempList = (LinkedList<?>) o;
        if (this.length != tempList.length) {
            return false;
        }
        Iterator<?> iterator1 = this.iterator();
        Iterator<?> iterator2 = tempList.iterator();
        while (iterator1.hasNext()) {
            if (!Objects.equals(iterator1.next(), iterator2.next())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int prime = 37;
        int hash = 1;
        for (T element : this) {
            hash = prime * hash + (element == null ? 0 : element.hashCode());
        }
        return hash;
    }
}
