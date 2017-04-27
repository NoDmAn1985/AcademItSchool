package ru.academits.novoselovda.linkedlist;

import java.util.*;

public class LinkedList<T> implements List<T>, Deque<T> {
    private int length;
    private Node<T> firstNode;
    private Node<T> lastNode;
    private int modificationsCount;

    private static class Node<T> {
        private T data;
        private Node<T> next;
        private Node<T> previous;

        Node(T data, Node<T> previous, Node<T> next) {
            this.data = data;
            this.next = next;
            this.previous = previous;
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
        return new DescendingItr();
    }

    private class DescendingItr implements Iterator<T> {
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
        Node<T> nextElement = LinkedList.this.firstNode;
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
            Node<T> temp = nextElement;
            nextElement = nextElement.next;
            ++nextIndex;
            previousIndex = nextIndex - 1;
            return temp.data;
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
            return nextElement.data;
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
            Node<T> temp = nextElement;
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
            nextElement.data = t;
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
        this.firstNode = new Node<>(t, null, this.firstNode);
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
        this.lastNode = new Node<>(t, this.lastNode, null);
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
            throw new NoSuchElementException();
        }
        T tempData = firstNode.data;
        if (length == 1) {
            this.firstNode = null;
            this.lastNode = null;
        } else {
            this.firstNode = this.firstNode.next;
            this.firstNode.previous = null;
        }
        --length;
        ++modificationsCount;
        return tempData;
    }

    @Override
    public T removeLast() {
        if (length == 0) {
            throw new NoSuchElementException();
        }
        T tempData = lastNode.data;
        if (length == 1) {
            this.firstNode = null;
            this.lastNode = null;
        } else {
            this.lastNode = this.lastNode.previous;
            this.lastNode.next = null;
        }
        --length;
        ++modificationsCount;
        return tempData;
    }

    @Override
    public T pollFirst() {
        return poll();
    }

    @Override
    public T pollLast() {
        if (length == 0) {
            return null;
        }
        return removeLast();
    }

    @Override
    public T getFirst() {
        if (length == 0) {
            throw new NoSuchElementException();
        }
        return this.firstNode.data;
    }

    @Override
    public T getLast() {
        if (length == 0) {
            throw new NoSuchElementException();
        }
        return this.lastNode.data;
    }

    @Override
    public T peekFirst() {
        if (length == 0) {
            return null;
        }
        return this.firstNode.data;
    }

    @Override
    public T peekLast() {
        if (length == 0) {
            return null;
        }
        return this.lastNode.data;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        for (Node<T> p = this.firstNode; p != null; p = p.next) {
            if (Objects.equals(p.data, o)) {
                removeThisNode(p);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        for (Node<T> p = this.lastNode; p != null; p = p.previous) {
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
        if (length == 0) {
            return null;
        }
        return removeFirst();
    }

    @Override
    public T element() {
        if (length == 0) {
            throw new NoSuchElementException();
        }
        return this.firstNode.data;
    }

    @Override
    public T peek() {
        if (length == 0) {
            return null;
        }
        return this.firstNode.data;
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
        return (indexOf(o) != -1);
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[this.length];
        int index = 0;
        for (Node<T> p = this.firstNode; p != null; p = p.next) {
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
        int index = 0;
        for (Node<T> p = this.firstNode; p != null; p = p.next) {
            a[index] = (T1) p.data;
            ++index;
        }
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
        for (Node<T> p = this.firstNode; p != null; p = p.next) {
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
        if (c == null) {
            throw new NullPointerException();
        }
        if (c.size() == 0) {
            return false;
        }
        for (Object object : c) {
            addLast(objectToTypeT(object));
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        if (c == null) {
            throw new NullPointerException();
        }
        if (c.size() == 0) {
            return false;
        }
        testOnEntrance(index);
        if (length == 0) {
            addAll(c);
            return true;
        }
        Node<T> p = findNode(index);
        if (p == null) {
            addAll(c);
            return true;
        }
        Node<T> tempLast = this.lastNode;
        this.lastNode = p.previous;
        for (Object object : c) {
            addLast(objectToTypeT(object));
        }
        this.lastNode.next = p;
        p.previous = this.lastNode;
        this.lastNode = tempLast;
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (c == null) {
            throw new NullPointerException();
        }
        if (c.size() == 0) {
            return false;
        }
        boolean isRemoved = false;
        Node<T> temp;
        for (Node<T> p = this.firstNode; p != null; p = temp) {
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
        if (c == null) {
            throw new NullPointerException();
        }
        if (c.size() == 0) {
            clear();
            return false;
        }
        boolean isRetain = false;
        Node<T> temp;
        for (Node<T> p = this.firstNode; p != null; p = temp) {
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
        Node<T> temp = findNode(index);
        return (temp == null ? null : temp.data);
    }

    @Override
    public T set(int index, T element) {
        testOnEntrance(index);
        Node<T> temp = findNode(index);
        if (temp == null) {
            return null;
        }
        T tempData = temp.data;
        temp.data = element;
        return tempData;
    }

    @Override
    public void add(int index, T element) {
        testOnEntrance(index);
        Node<T> tempNext = findNode(index);
        if (tempNext != null) {
            insertNode(element, tempNext.previous, tempNext);
        }
    }

    @Override
    public T remove(int index) {
        testOnEntrance(index);
        Node<T> temp = findNode(index);
        if (temp == null) {
            return null;
        }
        removeThisNode(temp);
        return temp.data;
    }

    @Override
    public int indexOf(Object o) {
        testZeroLength();
        int index = 0;
        for (Node<T> p = this.firstNode; p != null; p = p.next) {
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
        for (Node<T> p = this.lastNode; p != null; p = p.previous) {
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

    private Node<T> findNode(int index) {
        if (index == length) {
            return null;
        } else if (index > length / 2) {
            int i = length - 1;
            for (Node<T> p = this.lastNode; p != null; p = p.previous) {
                if (i == index) {
                    return p;
                }
                --i;
            }
        } else {
            int i = 0;
            for (Node<T> p = this.firstNode; p != null; p = p.next) {
                if (i == index) {
                    return p;
                }
                ++i;
            }
        }
        throw new NullPointerException();
    }

    private void insertNode(T t, Node<T> previous, Node<T> next) {
        Node<T> temp = new Node<>(t, previous, next);
        previous.next = temp;
        next.previous = temp;
        ++length;
        ++modificationsCount;
    }

    private void removeThisNode(Node<T> node) {
        if (this.firstNode == node) {
            removeFirst();
        } else if (this.lastNode == node) {
            removeLast();
        } else {
            node.previous.next = node.next;
            node.next.previous = node.previous;
            --length;
            ++modificationsCount;
        }
    }

    @Override
    public String toString() {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("{");
        for (Node<T> p = this.firstNode; p != null; p = p.next) {
            stringbuilder.append(p.data).append(", ");
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
