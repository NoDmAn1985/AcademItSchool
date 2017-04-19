package ru.academits.novoselovda.arraylist;

import java.util.*;

public class ArrayList<T> implements List<T> {
    private static final int INITIAL_ARRAY_LENGTH = 10;
    private Object[] array;
    private int length;
    private int modificationsCount;

    public ArrayList() {
        array = new Object[INITIAL_ARRAY_LENGTH];
        modificationsCount = 0;
    }

    public ArrayList(int userCapacity) {
        if (userCapacity >= 0) {
            array = new Object[userCapacity];
            modificationsCount = 0;
        } else {
            throw new IllegalArgumentException("ОШИБКА: вместимость ArrayList указана неверно");
        }
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
        return (indexOf(o) >= 0);
    }

    @Override
    public Iterator<T> iterator() {
        return new Itr();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(array, length);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T1> T1[] toArray(T1[] a) {
        if (a.length < length) {
            return (T1[]) Arrays.copyOf(array, length, a.getClass());
        }
        System.arraycopy(array, 0, a, 0, length);
        if (a.length > length) {
            a[length] = null;
        }
        return a;
    }

    @Override
    public boolean add(T t) {
        autoIncreaseCapacity();
        array[length] = t;
        ++length;
        ++modificationsCount;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index >= 0) {
            remove(index);
            return true;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        boolean[] isCollectionContains = new boolean[c.size()];
        int count = 0;
        for (Object element : array) {
            int index = 0;
            for (Object object : c) {
                if (!isCollectionContains[index] && Objects.equals(element, object)) {
                    isCollectionContains[index] = true;
                    ++count;
                    if (count == c.size()) {
                        return true;
                    }
                    break;
                }
                ++index;
            }
        }
        return count == c.size();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (c.size() == 0) {
            throw new NullPointerException();
        }
        return addAll(length, c);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean addAll(int index, Collection<? extends T> c) {
        if (index != length) {
            testOfEntrance(index);
        }
        if (length + c.size() < array.length) {
            ensureCapacity(length + c.size());
        }
        T[] tempArray = (T[]) c.toArray();
        if (index != length) {
            System.arraycopy(array, index, array, index + tempArray.length, length - index);
        }
        System.arraycopy(tempArray, 0, array, index, tempArray.length);
        length += tempArray.length;
        modificationsCount += tempArray.length;
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean isRemove = false;
        for (Object object : c) {
            for (int i = 0; i < length; i++) {
                if (Objects.equals(array[i], object)) {
                    remove(i);
                    isRemove = true;
                    ++modificationsCount;
                    --i;
                }
            }
        }
        return isRemove;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean isRetainAll = false;
        for (int i = 0; i < length; ++i) {
            if (!c.contains(array[i])) {
                remove(i);
                isRetainAll = true;
            }
        }
        return isRetainAll;
    }

    @Override
    public void clear() {
        ++modificationsCount;
        length = 0;
    }

    @SuppressWarnings("unchecked")
    private T objectToTypeT(int index) {
        return (T) array[index];
    }

    @Override
    public T get(int index) {
        testOfEntrance(index);
        return objectToTypeT(index);
    }

    @Override
    public T set(int index, T element) {
        testOfEntrance(index);
        T oldElement = objectToTypeT(index);
        this.array[index] = element;
        return oldElement;
    }

    @Override
    public void add(int index, T element) {
        if (index != length) {
            testOfEntrance(index);
            System.arraycopy(array, index, array, index + 1, length - index);
        }
        array[index] = element;
        ++length;
        ++modificationsCount;
    }

    @Override
    public T remove(int index) {
        testOfEntrance(index);
        T oldElement = objectToTypeT(index);
        if (index != length - 1) {
            System.arraycopy(array, index + 1, array, index, length - index);
        }
        --length;
        ++modificationsCount;
        return oldElement;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < length; ++i) {
            if (Objects.equals(array[i], o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = length - 1; i > 0; --i) {
            if (Objects.equals(array[i], o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        testOfEntrance(index);
        return new ListItr(index);
    }

    @Override
    public ListIterator<T> listIterator() {
        return new ListItr(0);
    }

    private class ListItr extends Itr implements ListIterator<T> {
        ListItr(int index) {
            super();
            nextIndex = index;
        }

        @Override
        public boolean hasPrevious() {
            return nextIndex > 0;
        }

        @Override
        @SuppressWarnings("unchecked")
        public T previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            checkForModifications();
            --nextIndex;
            --previousIndex;
            return (T) ArrayList.this.array[nextIndex];
        }

        @Override
        public int nextIndex() {
            return nextIndex;
        }

        @Override
        public int previousIndex() {
            return nextIndex - 1;
        }

        @Override
        public void remove() {
            checkForModifications();
            try {
                ArrayList.this.remove(nextIndex);
                if (nextIndex == length - 1 && nextIndex > 0) {
                    --nextIndex;
                    --previousIndex;
                }
                expectedModCount = modificationsCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public void set(T t) {
            checkForModifications();
            try {
                ArrayList.this.set(nextIndex(), t);
            } catch (IndexOutOfBoundsException exception) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public void add(T t) {
            checkForModifications();
            try {
                ArrayList.this.add(nextIndex, t);
                expectedModCount = modificationsCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }

    private class Itr implements Iterator<T> {
        int nextIndex;
        int previousIndex = -1;
        int expectedModCount = modificationsCount;

        @Override
        public boolean hasNext() {
            return nextIndex < length;
        }

        @Override
        @SuppressWarnings("unchecked")
        public T next() {
            if (previousIndex == length) {
                throw new NoSuchElementException();
            }
            checkForModifications();
            ++previousIndex;
            ++nextIndex;
            return (T) ArrayList.this.array[previousIndex];
        }

        void checkForModifications() {
            if (modificationsCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return null;
    }

    public void trimToSize() {
        if (array.length > length) {
            array = Arrays.copyOf(array, length);
        }
    }

    private void testOfEntrance(int index) {
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void autoIncreaseCapacity() {
        if (length >= array.length) {
            increaseCapacity();
        }
    }

    private void increaseCapacity() {
        ensureCapacity(array.length * 2);
    }

    public void ensureCapacity(int userCapacity) {
        if (userCapacity > array.length) {
            array = Arrays.copyOf(array, userCapacity);
        }
    }

    public int getCapacity() {
        return array.length;
    }

    @Override
    public String toString() {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("{");
        for (int i = 0; i < length; ++i) {
            stringbuilder.append(array[i]).append(", ");
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
        ArrayList<?> tempList = (ArrayList<?>) o;
        if (this.length != tempList.length) {
            return false;
        }
        for (int i = 0; i < length; ++i) {
            if (!Objects.equals(this.array[i], tempList.array[i])) {
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