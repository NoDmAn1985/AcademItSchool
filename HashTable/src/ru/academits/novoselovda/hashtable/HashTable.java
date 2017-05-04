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
        if (o == null) {
            return (this.elementsCount != 0 && this.table[0] != null && this.table[0].contains(null));
        }
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
        int index = (t == null ? 0 : getHash(t));
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
            int index = (o == null ? 0 : getHash(o));
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
        for (Object object : c) {
            if (add(objectToTypeT(object)) && !isAnyChanges) {
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
            if (remove(object) && !isAnyChanges) {
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
        for (T element : this) {
            if (!c.contains(element)) {
                if (remove(element)) {
                    --this.modificationsCount;
                    isAnyChanges = true;
                }
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
            this.modificationsCount = 0;
            this.elementsCount = 0;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<T> {
        private int cursor = 0;
        private int insideCursor = 0;
        private int expectedModificationCount = HashTable.this.modificationsCount;
        private int length = HashTable.this.elementsCount;

        Itr() {
            findNotNullCell();
        }

        @Override
        public boolean hasNext() {
            return this.cursor < HashTable.this.table.length;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            checkForModifications();
            if (this.insideCursor != 0 && this.length > HashTable.this.elementsCount) {
                --this.insideCursor;
                this.length = HashTable.this.elementsCount;
            }
            T tempData;
            if (this.insideCursor < HashTable.this.table[this.cursor].size() - 1) {
                tempData = objectToTypeT(HashTable.this.table[this.cursor].get(this.insideCursor));
                ++this.insideCursor;
            } else {
                this.insideCursor = 0;
                this.length = HashTable.this.elementsCount;
                tempData = objectToTypeT(HashTable.this.table[this.cursor].
                        get(HashTable.this.table[this.cursor].size() - 1));
                ++this.cursor;
                findNotNullCell();
            }
            return tempData;
        }

        private void findNotNullCell() {
            while (this.cursor < HashTable.this.table.length &&
                    (HashTable.this.table[this.cursor] == null ||
                            HashTable.this.table[this.cursor].size() == 0)) {
                ++this.cursor;
            }
        }

        void checkForModifications() {
            if (HashTable.this.modificationsCount != this.expectedModificationCount) {
                throw new ConcurrentModificationException();
            }
        }
    }

    private int getHash(Object object) {
        return Math.abs(object.hashCode()) % this.capacity;
    }

    @SuppressWarnings("unchecked")
    private T objectToTypeT(Object object) {
        return (object == null ? null : (T) object);
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

