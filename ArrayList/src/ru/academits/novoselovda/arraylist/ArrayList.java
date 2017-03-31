package ru.academits.novoselovda.arraylist;

import java.util.Arrays;
import java.util.Collection;

public class ArrayList<E> {
    private static final int INITIAL_ARRAY_LENGTH = 10;
    private Object[] array;
    private int length;

    public ArrayList() {
        array = new Object[INITIAL_ARRAY_LENGTH];
    }

    public ArrayList(int userCapacity) {
        if (userCapacity >= 0) {
            array = new Object[userCapacity];
        } else {
            throw new IllegalArgumentException("ОШИБКА: вместимость листа указана неверно");
        }
    }

    public ArrayList(Collection<? extends E> collection) {
        array = collection.toArray();
        if (array.length > 0) {
            if (array.getClass() != Object[].class) {
                array = Arrays.copyOf(array, array.length, Object[].class);
            }
        } else {
            array = new Object[0];
        }
    }

    public void add(Object object) {
        autoIncreaseCapacity();
        array[length] = object;
        ++length;
    }

    public void add(int index, Object object) {
        if (index != length) {
            testOfEntrance(index);
        }
        System.arraycopy(array, index, array, index + 1, length - index - 1);
        array[index] = object;
        ++length;
    }

    public void addArray(Object[] objects) {
        System.arraycopy(objects, 0, array, length, objects.length);
        length += objects.length;
    }

    public boolean addAll(Collection collection) {
        boolean isAdd = false;
        for (Object object : collection) {
            add(object);
            isAdd = true;
        }
        return isAdd;
    }

    public boolean addAll(int index, Collection collection) {
        testOfEntrance(index);
        boolean isAdd = false;
        for (Object object : collection) {
            if (object != null) {
                add(index, object);
                ++index;
                isAdd = true;
            }
        }
        return isAdd;
    }

    public void clear() {
        length = 0;
    }

    public void ensureCapacity(int userCapacity) {
        if (userCapacity > array.length) {
            array = Arrays.copyOf(array, userCapacity);
        }
    }

    public boolean isContains(Object object) {
        return (indexOf(object) >= 0);
    }

    public int getSize() {
        return length;
    }

    public int indexOf(Object object) {
        if (object != null) {
            for (int i = 0; i < array.length; ++i) {
                if (array[i].equals(object)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public int lastIndexOf(Object object) {
        if (object != null) {
            for (int i = array.length - 1; i > 0; --i) {
                if (array[i].equals(object)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public Object clone() {
        try {
            ArrayList clone = (ArrayList) super.clone();
            System.arraycopy(this.array, 0, clone.array, 0, this.array.length);
            return clone;
        } catch (CloneNotSupportedException exception) {
            throw new InternalError(exception);
        }
    }

    public Object[] toArray() {
        return array;
    }

    private void increaseCapacity() {
        ensureCapacity(array.length * 2);
    }

    public boolean isEmpty() {
        return length == 0;
    }

    public Object get(int index) {
        testOfEntrance(index);
        return array[index];
    }

    public void remove(int index) {
        if (index != length - 1) {
            testOfEntrance(index);
            System.arraycopy(array, index + 1, array, index, length - index - 1);
        }
        --length;
    }

    public boolean remove(Object object) {
        if (object != null) {
            for (int i = 0; i < array.length; ++i) {
                if (array[i].equals(object)) {
                    remove(i);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean removeRange(int begin, int endExcept) {
        testOfEntrance(begin);
        testOfEntrance(endExcept - 1);
        if (begin < endExcept) {
            System.arraycopy(array, endExcept, array, begin, length - begin - 1);
            return true;
        }
        return false;
    }

    public boolean removeAll(Collection collection) {
        boolean isRemove = false;
        for (Object object : collection) {
            if (object != null) {
                for (int i = 0; i < array.length; i++) {
                    if (array[i].equals(object)) {
                        remove(array[i]);
                        isRemove = true;
                        --i;
                    }
                }
            }
        }
        return isRemove;
    }

    public boolean retainAll(Collection collection) {
        for (int i = 0; i < array.length; ++i) {
            boolean isEquals = false;
            for (Object object : collection) {
                if (object != null) {
                    if (array[i].equals(object)) {
                        isEquals = true;
                    }
                }
            }
            if (!isEquals) {
                remove(array[i]);
                --i;
            }
        }
        return array.length != 0;
    }

    public void trimToSize() {
        if (array.length > length) {
            array = Arrays.copyOf(array, length);
        }
    }

    public void set(int index, Object object) {
        testOfEntrance(index);
        this.array[index] = object;
    }

    private void testOfEntrance(int index) {
        if (index < 0 || index >= length) {
            throw new IllegalArgumentException("ОШИБКА: индекс в списке не найден");
        }
    }

    private void autoIncreaseCapacity() {
        if (length >= array.length) {
            increaseCapacity();
        }
    }

    @Override
    public String toString() {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("{");
        for (Object element : array) {
            stringbuilder.append(element).append(", ");
        }
        stringbuilder.delete(stringbuilder.length() - 1, stringbuilder.length()).append("}");
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
        ArrayList arrayList = (ArrayList) o;
        return (length == arrayList.length && Arrays.equals(array, arrayList.array));
    }

    @Override
    public int hashCode() {
        int prime = 37;
        int hash = 1;
        hash = prime * hash + length;
        hash = prime * hash + Arrays.hashCode(array);
        return hash;
    }
}
