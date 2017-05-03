package ru.academits.novoselovda.hashtable;

import java.util.*;

public class HashTable<T> implements Collection<T> {
    private final static int DEFAULT_CAPACITY = 11;
    private int capacity;
    private List<T>[] table;
    private int countOfElements;

    @SuppressWarnings("unchecked")
    public HashTable(int capacity) {
        if (capacity <= 0) {
            throw new IndexOutOfBoundsException();
        }
        this.capacity = capacity;
        this.table = new LinkedList[this.capacity];
        this.countOfElements = 0;
    }

    public HashTable() {
        this(DEFAULT_CAPACITY);
    }

    @Override
    public int size() {
        return this.countOfElements;
    }

    @Override
    public boolean isEmpty() {
        return this.countOfElements == 0;
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) {
            throw new NullPointerException();
        }
        int index = getHash(o);
        return (this.countOfElements != 0 && this.table[index] != null && this.table[index].contains(o));
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[this.countOfElements];
        if (this.countOfElements != 0) {
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
        if (this.countOfElements != 0) {
            T1[] tempArray = (T1[]) Arrays.copyOf(this.toArray(), this.countOfElements);
            if (a.length < this.countOfElements) {
                return tempArray;
            }
            System.arraycopy(tempArray, 0, a, 0, this.countOfElements);
            if (a.length > this.countOfElements) {
                a[this.countOfElements] = null;
            }
        }
        return a;
    }

    @Override
    public boolean add(T t) {
        if (t == null) {
            throw new NullPointerException();
        }
        int index = getHash(t);
        if (this.table[index] == null) {
            this.table[index] = new LinkedList<>();
            this.table[index].add(t);
            ++this.countOfElements;
            return true;
        } else if (this.table[index] != null && !this.table[index].contains(t)) {
            this.table[index].add(t);
            ++this.countOfElements;
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) {
            throw new NullPointerException();
        }
        if (this.countOfElements != 0) {
            int index = getHash(o);
            if (this.table[index] != null && this.table[index].remove(o)) {
                --this.countOfElements;
                if (this.table[index].size() == 0) {
                    this.table[index] = null;
                }
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
        if (c.size() == 0 || this.countOfElements == 0) {
            return false;
        }
        int count = 0;
        for (T element : this) {
            if (c.contains(element)) {
                ++count;
            }
        }
        return count == c.size();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (c == null) {
            throw new NullPointerException();
        }
        if (c.size() == 0) {
            return false;
        }
        boolean isAdd = false;
        for (Object object : c) {
            if (object != null) {
                add(objectToTypeT(object));
                isAdd = true;
            }
        }
        return isAdd;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (c == null) {
            throw new NullPointerException();
        }
        if (c.size() == 0 || this.countOfElements == 0) {
            return false;
        }
        boolean isRemove = false;
        for (Object object : c) {
            if (object != null) {
                remove(object);
                isRemove = true;
            }
        }
        return isRemove;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (c == null) {
            throw new NullPointerException();
        }
        if (c.size() == 0 || this.countOfElements == 0) {
            return false;
        }
        boolean isRetain = false;
        for (T element : this) {
            if (!c.contains(element)) {
                remove(element);
                isRetain = true;
            }
        }
        return isRetain;
    }

    @Override
    public void clear() {
        if (this.countOfElements != 0) {
            Arrays.fill(this.table, null);
            this.countOfElements = 0;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<T> {
        private int cursor = 0;
        private int insideCursor = 0;

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
            T tempData;
            if (HashTable.this.table[this.cursor].size() > 1 &&
                    this.insideCursor < HashTable.this.table[this.cursor].size() - 1) {
                tempData = objectToTypeT(HashTable.this.table[this.cursor].get(this.insideCursor));
                ++this.insideCursor;
            } else {
                this.insideCursor = 0;
                tempData = objectToTypeT(HashTable.this.table[this.cursor].
                        get(HashTable.this.table[this.cursor].size() - 1));
                ++this.cursor;
                findNotNullCell();
            }

            return tempData;
        }

        private void findNotNullCell() {
            while (this.cursor < HashTable.this.table.length && HashTable.this.table[this.cursor] == null) {
                ++this.cursor;
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
        return sb.delete(sb.length() - 2, sb.length()).append("} , size = ").append(this.countOfElements).
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
        if (this.countOfElements != tempTable.countOfElements) {
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

