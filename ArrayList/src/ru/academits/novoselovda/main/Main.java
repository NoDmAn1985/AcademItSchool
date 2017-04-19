package ru.academits.novoselovda.main;

import ru.academits.novoselovda.arraylist.ArrayList;

import java.util.Arrays;
import java.util.Iterator;
import java.util.ListIterator;

public class Main {
    public static void main(String[] args) {
        ArrayList<String>[] lists = new ArrayList[3];

        lists[0] = new ArrayList<>();
        lists[1] = new ArrayList<>();
        lists[1].add("ff");
        lists[1].add("ss");
        lists[1].add("tt");
        lists[1].add("hh");
        lists[2] = new ArrayList<>(10);

        System.out.println("size()");
        for (int i = 0; i < lists.length; i++) {
            System.out.println("\tлист № " + i + " > " + lists[i].size() +
                    " > " + lists[i] + " > " + lists[i].size());
        }

        System.out.println("isEmpty()");
        for (int i = 0; i < lists.length; i++) {
            System.out.println("\tлист № " + i + " > " + lists[i].isEmpty() +
                    " > " + lists[i] + " > " + lists[i].size());
        }

        System.out.println("contains(\"ss\")");
        for (int i = 0; i < lists.length; i++) {
            System.out.println("\tлист № " + i + " > " + lists[i].contains("ss") +
                    " > " + lists[i] + " > " + lists[i].size());
        }

        System.out.println("toArray()");
        for (int i = 0; i < lists.length; i++) {
            System.out.println("\tлист № " + i + " > " + Arrays.toString(lists[i].toArray()) +
                    " > " + lists[i] + " > " + lists[i].size());
        }

        System.out.println("toArray(T1[] a)");
        String[] a = new String[5];
        for (int i = 0; i < lists.length; i++) {
            System.out.println("\tлист № " + i + " > " + Arrays.toString(lists[i].toArray(a)) +
                    " > " + lists[i] + " > " + lists[i].size());
        }

        System.out.println("add(T t)");
        for (int i = 0; i < lists.length; i++) {
            System.out.println("\tлист № " + i + " > " + lists[i].add("fqfe") +
                    " > " + lists[i] + " > " + lists[i].size());
        }

        System.out.println("remove(Object o)");
        for (int i = 0; i < lists.length; i++) {
            System.out.println("\tлист № " + i + " > " + lists[i].remove("ss") +
                    " > " + lists[i] + " > " + lists[i].size());
        }

        java.util.ArrayList<String> collection = new java.util.ArrayList<>();
        collection.add("fqfe");
        collection.add("tt");
        System.out.println("containsAll(Collection<?> c)");
        for (int i = 0; i < lists.length; i++) {
            System.out.println("\tлист № " + i + " > " + lists[i].containsAll(collection) +
                    " > " + lists[i] + " > " + lists[i].size());
        }

        System.out.println("addAll(Collection<? extends T> c)");
        for (int i = 0; i < lists.length; i++) {
            System.out.println("\tлист № " + i + " > " + lists[i].addAll(collection) +
                    " > " + lists[i] + " > " + lists[i].size());
        }

        System.out.println("addAll(int index, Collection<? extends T> c)");
        for (int i = 0; i < lists.length; i++) {
            System.out.println("\tлист № " + i + " > " + lists[i].addAll(2, collection) +
                    " > " + lists[i] + " > " + lists[i].size());
        }

        java.util.ArrayList<String> collection2 = new java.util.ArrayList<>();
        collection2.add("fqfe");
        System.out.println("removeAll(Collection<?> c)");
        for (int i = 0; i < lists.length; i++) {
            System.out.println("\tлист № " + i + " > " + lists[i].removeAll(collection2) +
                    " > " + lists[i] + " > " + lists[i].size());
        }

        System.out.println("retainAll(Collection<?> c)");
        java.util.ArrayList<String> collection3 = new java.util.ArrayList<>();
        collection3.add("tt");
        for (int i = 0; i < lists.length; i++) {
            System.out.println("\tлист № " + i + " > " + lists[i].retainAll(collection3) +
                    " > " + lists[i] + " > " + lists[i].size());
        }

        System.out.println("clear()");
        for (int i = 0; i < lists.length; i++) {
            lists[i].clear();
            System.out.println("\tлист № " + i + " > " + lists[i] + " > " + lists[i].size());
        }

        lists[0].addAll(collection);
        lists[0].addAll(collection2);
        lists[0].addAll(collection);
        lists[1].addAll(collection2);
        lists[1].addAll(collection);
        lists[1].addAll(collection2);
        lists[2].addAll(collection);
        lists[2].addAll(collection);
        lists[2].addAll(collection);

        System.out.println("get(int index)");
        for (int i = 0; i < lists.length; i++) {
            System.out.println("\tлист № " + i + " > " + lists[i].get(1) +
                    " > " + lists[i] + " > " + lists[i].size());
        }

        System.out.println("set(int index, T element)");
        for (int i = 0; i < lists.length; i++) {
            System.out.println("\tлист № " + i + " > " + lists[i].set(1, "element") +
                    " > " + lists[i] + " > " + lists[i].size());
        }

        System.out.println("add(int index, T element)");
        for (int i = 0; i < lists.length; i++) {
            lists[i].add(1, "element2");
            System.out.println("\tлист № " + i + " > " + lists[i] + " > " + lists[i].size());
        }

        System.out.println("remove(int index)");
        for (int i = 0; i < lists.length; i++) {
            System.out.println("\tлист № " + i + " > " + lists[i].remove(1) +
                    " > " + lists[i] + " > " + lists[i].size());
        }

        System.out.println("indexOf(Object o)");
        for (int i = 0; i < lists.length; i++) {
            System.out.println("\tлист № " + i + " > " + lists[i].indexOf("fqfe") +
                    " > " + lists[i] + " > " + lists[i].size());
        }

        System.out.println("lastIndexOf(Object o)");
        for (int i = 0; i < lists.length; i++) {
            System.out.println("\tлист № " + i + " > " + lists[i].lastIndexOf("fqfe") +
                    " > " + lists[i] + " > " + lists[i].size());
        }

        System.out.println("trimToSize()");
        for (int i = 0; i < lists.length; i++) {
            System.out.println("\tлист № " + i + " > " + lists[i].getCapacity() +
                    " > " + lists[i] + " > " + lists[i].size());
            lists[i].trimToSize();
            System.out.println("\tпосле подрезки > " + lists[i].getCapacity() +
                    " > " + lists[i] + " > " + lists[i].size());
        }

        System.out.println("ensureCapacity(int userCapacity)");
        for (int i = 0; i < lists.length; i++) {
            lists[i].ensureCapacity(99);
            System.out.println("\tлист № " + i + " > " + lists[i].getCapacity() +
                    " > " + lists[i] + " > " + lists[i].size());
        }
        System.out.println();
        System.out.println("Итератор");
        for (int i = 0; i < lists.length; i++) {
            System.out.println("лист № " + i + " > " + lists[i] + " > " + lists[i].size());
            Iterator<String> iterator = lists[i].iterator();
            int index = 0;
            while (iterator.hasNext()) {
                System.out.print("\t");
                System.out.print(index);
                System.out.print(") ");
                System.out.println(iterator.next());
                ++index;
            }
        }
        System.out.println();
        System.out.println("ЛистИтератор");
        for (int i = 0; i < lists.length; i++) {
            System.out.println("лист № " + i + " > " + lists[i] + " > " + lists[i].size());
            ListIterator<String> listIterator = lists[i].listIterator();
            while (listIterator.hasNext()) {
                if (listIterator.nextIndex() == 2) {
                    listIterator.set("TWO");
                    System.out.println("\tМеняю 2-й элемент на TWO > " + lists[i] + " > " + lists[i].size());
                }
                if (listIterator.nextIndex() == 3) {
                    listIterator.add("FFF");
                    System.out.println("\tДобавляюю 3-м элементом FFF > " + lists[i] + " > " + lists[i].size());
                }
                if (listIterator.previousIndex() == 4) {
                    listIterator.remove();
                    System.out.println("\tУдаляю всё с 5-го элемента> " + lists[i] + " > " + lists[i].size());
                }
                listIterator.next();
            }
            System.out.println("Обратно");
            while (listIterator.hasPrevious()) {
                if (listIterator.nextIndex() == 2) {
                    listIterator.set("THREE");
                    System.out.println("\tМеняю 2-й элемент на THREE > " + lists[i] + " > " + lists[i].size());
                    listIterator.add("ZZZZ");
                    System.out.println("\tДобавляюю 2-м индексом элемент ZZZZ > " + lists[i] + " > " + lists[i].size());
                }
                if (listIterator.previousIndex() == 3) {
                    listIterator.remove();
                    System.out.println("\tУдаляю всё с 4-го элемента> " + lists[i] + " > " + lists[i].size());
                }
                listIterator.previous();
            }
        }
        ArrayList<String> list1 = new ArrayList<>();
        list1.add("dfsdf");
        list1.add("dfsdsdaf");
        list1.add("ddsdaf");
        list1.remove(1);
        ArrayList<String> list2 = new ArrayList<>();
        list2.add("dfsdf");
        list2.add("ddsdaf");
        System.out.println(list1.equals(list2));
    }
}
