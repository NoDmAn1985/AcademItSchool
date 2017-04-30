package ru.academits.novoselovda.hashtable;

import java.util.*;

public class HashTable<T> implements Collection<T> {
    private final int DEFAULT_CAPACITY = 20;
    private final double CAPACITY_RATE = 1.3;
    private int capacity;
    private Object[] table;
    private int length;
    private int digitsCount;

    public HashTable() {
        this.capacity = DEFAULT_CAPACITY;
        this.table = new Object[this.capacity];
        this.length = 0;
    }

    public HashTable(int capacity) {
        if (capacity <= 0) {
            throw new IndexOutOfBoundsException();
        }
        this.capacity = capacity;
        this.table = new Object[this.capacity];
        this.length = 0;
    }

    @Override
    public int size() {
        return this.length;
    }

    @Override
    public boolean isEmpty() {
        return this.length == 0;
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) {
            throw new NullPointerException();
        }
        return (this.length != 0 && Objects.equals(this.table[getHash(o)], o));
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[this.length];
        if (length != 0) {
            int index = 0;
            Iterator<T> iterator = new Itr();
            while (iterator.hasNext()) {
                array[index] = iterator.next();
                ++index;
            }
        }
        return array;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T1> T1[] toArray(T1[] a) {
        if (this.length != 0) {
            T1[] tempArray = (T1[]) Arrays.copyOf(this.toArray(), this.length);
            if (a.length < this.length) {
                return Arrays.copyOf(tempArray, this.length);
            }
            System.arraycopy(tempArray, 0, a, 0, this.length);
            if (a.length > this.length) {
                a[this.length] = null;
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
        autoResizeTable(index, t);
        if (this.table[index] == null) {
            this.table[index] = t;
            ++this.length;
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) {
            throw new NullPointerException();
        }
        if (this.length != 0) {
            int index = getHash(o);
            if (Objects.equals(this.table[index], o)) {
                this.table[index] = null;
                --this.length;
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
        if (c.size() == 0 || this.length == 0) {
            return false;
        }
        int count = 0;
        for (Object object : c) {
            if (contains(object)) {
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
        if (c.size() == 0 || this.length == 0) {
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
        if (c.size() == 0 || this.length == 0) {
            return false;
        }
        boolean isRetain = false;
        for (int i = 0; i < this.capacity; i++) {
            if (this.table[i] != null && !c.contains(this.table[i])) {
                this.table[i] = null;
                --length;
                isRetain = true;
            }
        }
        return isRetain;
    }

    @Override
    public void clear() {
        if (this.length != 0) {
            Arrays.fill(this.table, null);
            this.length = 0;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<T> {
        private int count = 0;
        private int cursor = 0;

        {
            findNotNullCell();
        }

        @Override
        public boolean hasNext() {
            return this.count <= HashTable.this.length;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int tempIndex = this.cursor;
            ++this.cursor;
            findNotNullCell();
            return objectToTypeT(HashTable.this.table[tempIndex]);
        }

        private void findNotNullCell() {
            while (this.count != HashTable.this.length && HashTable.this.table[this.cursor] == null) {
                ++this.cursor;
            }
            ++this.count;
        }
    }

    private int getHash(Object object) {
        return (int) Math.abs(object.hashCode()) % this.capacity;
    }

    private void resizeTable() {
        this.capacity *= CAPACITY_RATE;
        Object[] newKeys = new Object[this.capacity];
        if (this.length != 0) {
            Iterator<T> iterator = new Itr();
            while (iterator.hasNext()) {
                T tempData = iterator.next();
                int newIndex = getHash(tempData);
                if (newKeys[newIndex] == null) {
                    newKeys[newIndex] = tempData;
                } else {
                    resizeTable();
                    return;
                }
            }
        }
        this.table = Arrays.copyOf(newKeys, this.capacity);
    }

    private void autoResizeTable(int index, Object object) {
        while (this.table[index] != null && !Objects.equals(this.table[index], object)) {
            resizeTable();
        }
    }

    @SuppressWarnings("unchecked")
    private T objectToTypeT(Object object) {
        return (object == null ? null : (T) object);
    }

    @Override
    public String toString() {
        return String.format("%s, size = %d, arraySize = %d",
                Arrays.toString(this.table), this.length, this.table.length);
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
        if (this.length != tempTable.length) {
            return false;
        }
        Iterator<?> iterator1 = this.iterator();
        Iterator<?> iterator2 = tempTable.iterator();
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
        for (T cell : this) {
            hash = prime * hash + (cell == null ? 0 : cell.hashCode());
        }
        return hash;
    }
}

