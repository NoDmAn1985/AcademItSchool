package ru.academits.novoselovda.hashtable;

import java.util.*;

public class HashTable<T> implements Collection<T> {
    private final static int DEFAULT_CAPACITY = 11;
    private int capacity;
    private List<T>[] table;
    private int elementsCount;
    private int modificationsCount;

    @SuppressWarnings("unchecked")
    public HashTable(int capacity) {
        if (capacity <= 0) {
            throw new IndexOutOfBoundsException();
        }
        this.capacity = capacity;
        this.table = new LinkedList[this.capacity];
        this.elementsCount = 0;
    }

    public HashTable() {
        this(DEFAULT_CAPACITY);
    }

    @Override
    public int size() {
        return this.elementsCount;
    }

    @Override
    public boolean isEmpty() {
        return this.elementsCount == 0;
    }

    @Override
    public boolean contains(Object o) {
        int index = getHash(o);
        return (this.elementsCount != 0 && this.table[index] != null && this.table[index].contains(o));
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[this.elementsCount];
        if (this.elementsCount != 0) {
            int index = 0;
            for (T element : this) {
                array[index] = element;
                ++index;
            }
        }
        return array;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T1> T1[] toArray(T1[] a) {
        if (this.elementsCount != 0) {
            T1[] tempArray = (T1[]) Arrays.copyOf(this.toArray(), this.elementsCount);
            if (a.length < this.elementsCount) {
                return tempArray;
            }
            System.arraycopy(tempArray, 0, a, 0, this.elementsCount);
            if (a.length > this.elementsCount) {
                a[this.elementsCount] = null;
            }
        }
        return a;
    }

    @Override
    public boolean add(T t) {
        int index = getHash(t);
        if (this.table[index] == null) {
            this.table[index] = new LinkedList<>();
        }
        if (this.table[index].contains(t)) {
            return false;
        }
        this.table[index].add(t);
        ++this.elementsCount;
        ++this.modificationsCount;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (this.elementsCount != 0) {
            int index = getHash(o);
            if (this.table[index] != null && this.table[index].remove(o)) {
                --this.elementsCount;
                ++this.modificationsCount;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (c == null) {
            throw new NullPointerException();
        }
        if (c.size() == 0) {
            return true;
        }
        if (this.elementsCount == 0) {
            return false;
        }
        int count = 0;
        boolean[] isVerified = new boolean[c.size()];
        for (T element : this) {
            int index = 0;
            for (Object object : c) {
                if (!isVerified[index] && Objects.equals(object, element)) {
                    isVerified[index] = true;
                    ++count;
                    if (count == c.size()) {
                        return true;
                    }
                    break;
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
        boolean isAnyChanges = false;
        for (T element : c) {
            if (add(element)) {
                isAnyChanges = true;
            }
        }
        return isAnyChanges;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (c == null) {
            throw new NullPointerException();
        }
        if (c.size() == 0 || this.elementsCount == 0) {
            return false;
        }
        boolean isAnyChanges = false;
        for (Object object : c) {
            if (remove(object)) {
                isAnyChanges = true;
            }
        }
        return isAnyChanges;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (c == null) {
            throw new NullPointerException();
        }
        if (this.elementsCount == 0) {
            return false;
        }
        if (c.size() == 0) {
            clear();
            return true;
        }
        boolean isAnyChanges = false;
        Iterator<T> iterator = new Itr();
        while (iterator.hasNext()) {
            if (!c.contains(iterator.next())) {
                iterator.remove();
                isAnyChanges = true;
            }
        }
        return isAnyChanges;
    }

    @Override
    public void clear() {
        if (this.elementsCount != 0) {
            for (List<T> row : this.table) {
                if (row != null) {
                    row.clear();
                }
            }
            ++this.modificationsCount;
            this.elementsCount = 0;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<T> {
        private int listIndex = 0;
        private int elementIndex = -1;
        private int count = 0;
        private int expectedModificationCount = HashTable.this.modificationsCount;

        @Override
        public boolean hasNext() {
            return this.count < HashTable.this.elementsCount;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            checkForModifications();
            if (HashTable.this.table[this.listIndex].size() == 0 ||
                    this.elementIndex >= HashTable.this.table[this.listIndex].size() - 1) {
                this.elementIndex = -1;
                ++this.listIndex;
                findNotNullCell();
            }
            ++this.elementIndex;
            ++this.count;
            return HashTable.this.table[this.listIndex].get(this.elementIndex);
        }

        public void remove() {
            if (HashTable.this.elementsCount == 0) {
                throw new NoSuchElementException();
            }
            HashTable.this.table[this.listIndex].remove(this.elementIndex);
            --this.count;
            --this.elementIndex;
            --HashTable.this.elementsCount;
            ++HashTable.this.modificationsCount;
            this.expectedModificationCount = HashTable.this.modificationsCount;
        }

        private void findNotNullCell() {
            while (HashTable.this.table[this.listIndex] == null ||
                    HashTable.this.table[this.listIndex].size() == 0) {
                ++this.listIndex;
            }
        }

        void checkForModifications() {
            if (HashTable.this.modificationsCount != this.expectedModificationCount) {
                throw new ConcurrentModificationException();
            }
        }
    }

    private int getHash(Object object) {
        if (object == null) {
            return 0;
        }
        return Math.abs(object.hashCode()) % this.capacity;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (Object element : this.table) {
            sb.append(element).append(", ");
        }
        return sb.delete(sb.length() - 2, sb.length()).append("} , size = ").append(this.elementsCount).
                append(", capacity = ").append(this.capacity).toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HashTable<?> tempTable = (HashTable<?>) o;
        if (this.elementsCount != tempTable.elementsCount) {
            return false;
        }
        Iterator<?> iterator1 = this.iterator();
        Iterator<?> iterator2 = tempTable.iterator();
        while (iterator1.hasNext()) {
            if (!iterator1.next().equals(iterator2.next())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int prime = 37;
        int hash = 1;
        for (T cell : this) {
            hash = prime * hash + (cell == null ? 0 : cell.hashCode());
        }
        return hash;
    }
}

