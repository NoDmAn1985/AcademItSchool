package ru.academits.novoselovda.main;

import ru.academits.novoselovda.linkedlist.LinkedList;

import java.util.Arrays;
import java.util.Iterator;
import java.util.ListIterator;

public class Main {
    public static void main(String[] args) {
        LinkedList<String>[] lists = new LinkedList[2];

        lists[0] = new LinkedList<>();
        lists[1] = new LinkedList<>();
        lists[1].add("ff");
        lists[1].add("ss");
        lists[1].add("ff");
        lists[1].add("ff");
        lists[1].add("tt");
        lists[1].add("hh");

        System.out.println("Исходные листы");
        for (int i = 0; i < lists.length; i++) {
            System.out.println("\tлист № " + i + " > " + lists[i] + " > " + lists[i].size());
        }

        System.out.println("addFirst(T t)");
        for (int i = 0; i < lists.length; i++) {
            lists[i].addFirst("addFirst");
            System.out.println("\tлист № " + i + " > " + lists[i] + " > " + lists[i].size());
        }

        System.out.println("addLast(T t)");
        for (int i = 0; i < lists.length; i++) {
            lists[i].addLast("addLast");
            System.out.println("\tлист № " + i + " > " + lists[i] + " > " + lists[i].size());
        }

        System.out.println("offerFirst(T t)");
        for (int i = 0; i < lists.length; i++) {
            System.out.println("\tлист № " + i + " > " + lists[i].offerFirst("offerFirst") +
                    " > " + lists[i] + " > " + lists[i].size());
        }

        System.out.println("offerLast(T t)");
        for (int i = 0; i < lists.length; i++) {
            System.out.println("\tлист № " + i + " > " + lists[i].offerLast("offerLast") +
                    " > " + lists[i] + " > " + lists[i].size());
        }

        System.out.println("removeFirst()");
        for (int i = 0; i < lists.length; i++) {
            System.out.println("\tлист № " + i + " > " + lists[i].removeFirst() +
                    " > " + lists[i] + " > " + lists[i].size());
        }

        System.out.println("removeLast()");
        for (int i = 0; i < lists.length; i++) {
            System.out.println("\tлист № " + i + " > " + lists[i].removeLast() +
                    " > " + lists[i] + " > " + lists[i].size());
        }

        System.out.println("pollFirst()");
        for (int i = 0; i < lists.length; i++) {
            System.out.println("\tлист № " + i + " > " + lists[i].pollFirst() +
                    " > " + lists[i] + " > " + lists[i].size());
        }

        System.out.println("pollLast()");
        for (int i = 0; i < lists.length; i++) {
            System.out.println("\tлист № " + i + " > " + lists[i].pollLast() +
                    " > " + lists[i] + " > " + lists[i].size());
        }

        System.out.println("getFirst()");
        for (int i = 0; i < lists.length; i++) {
            System.out.println("\tлист № " + i + " > " + lists[i].getFirst() +
                    " > " + lists[i] + " > " + lists[i].size());
        }

        System.out.println("getLast()");
        for (int i = 0; i < lists.length; i++) {
            System.out.println("\tлист № " + i + " > " + lists[i].getLast() +
                    " > " + lists[i] + " > " + lists[i].size());
        }

        System.out.println("peekFirst()");
        for (int i = 0; i < lists.length; i++) {
            System.out.println("\tлист № " + i + " > " + lists[i].peekFirst() +
                    " > " + lists[i] + " > " + lists[i].size());
        }

        System.out.println("peekLast()");
        for (int i = 0; i < lists.length; i++) {
            System.out.println("\tлист № " + i + " > " + lists[i].peekLast() +
                    " > " + lists[i] + " > " + lists[i].size());
        }

        System.out.println("removeFirstOccurrence(\"ff\")");
        for (int i = 0; i < lists.length; i++) {
            System.out.println("\tлист № " + i + " > " + lists[i].removeFirstOccurrence("ff") +
                    " > " + lists[i] + " > " + lists[i].size());
        }

        System.out.println("removeLastOccurrence(\"ff\")");
        for (int i = 0; i < lists.length; i++) {
            System.out.println("\tлист № " + i + " > " + lists[i].removeLastOccurrence("ff") +
                    " > " + lists[i] + " > " + lists[i].size());
        }

        System.out.println("offer(T t)");
        for (int i = 0; i < lists.length; i++) {
            System.out.println("\tлист № " + i + " > " + lists[i].offer("offer(T)") +
                    " > " + lists[i] + " > " + lists[i].size());
        }

        System.out.println("remove()");
        for (int i = 0; i < lists.length; i++) {
            System.out.println("\tлист № " + i + " > " + lists[i].remove() +
                    " > " + lists[i] + " > " + lists[i].size());
        }

        System.out.println("poll()");
        for (int i = 0; i < lists.length; i++) {
            System.out.println("\tлист № " + i + " > " + lists[i].poll() +
                    " > " + lists[i] + " > " + lists[i].size());
        }

        System.out.println("element()");
        for (int i = 0; i < lists.length; i++) {
            System.out.println("\tлист № " + i + " > " + lists[i].element() +
                    " > " + lists[i] + " > " + lists[i].size());
        }

        System.out.println("peek()");
        for (int i = 0; i < lists.length; i++) {
            System.out.println("\tлист № " + i + " > " + lists[i].peek() +
                    " > " + lists[i] + " > " + lists[i].size());
        }

        System.out.println("push(T t)");
        for (int i = 0; i < lists.length; i++) {
            lists[i].push("push");
            System.out.println("\tлист № " + i + " > " + " > " + lists[i] + " > " + lists[i].size());
        }

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

        System.out.println("contains(\"tt\")");
        for (int i = 0; i < lists.length; i++) {
            System.out.println("\tлист № " + i + " > " + lists[i].contains("tt") +
                    " > " + lists[i] + " > " + lists[i].size());
        }

        System.out.println("toArray()");
        for (int i = 0; i < lists.length; i++) {
            System.out.println("\tлист № " + i + " > " + Arrays.toString(lists[i].toArray()) +
                    " > " + lists[i] + " > " + lists[i].size());
        }

        System.out.println("toArray(T1[] a)");
        String[] a = new String[3];
        for (int i = 0; i < lists.length; i++) {
            System.out.println("\tлист № " + i + " > " + Arrays.toString(lists[i].toArray(a)) +
                    " > " + lists[i] + " > " + lists[i].size());
        }

        System.out.println("add(\"fqfe\")");
        for (int i = 0; i < lists.length; i++) {
            System.out.println("\tлист № " + i + " > " + lists[i].add("fqfe") +
                    " > " + lists[i] + " > " + lists[i].size());
        }

        System.out.println("remove(\"tt\")");
        for (int i = 0; i < lists.length; i++) {
            System.out.println("\tлист № " + i + " > " + lists[i].remove("tt") +
                    " > " + lists[i] + " > " + lists[i].size());
        }

        java.util.LinkedList<String> collection = new java.util.LinkedList<>();
        collection.add("fqfe");
        collection.add("hh");
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
        collection3.add("push");
        collection3.add("offer(T)");
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
        lists[0].addAll(collection3);
        lists[1].addAll(collection3);
        lists[1].addAll(collection);
        lists[1].addAll(collection2);
        lists[1].addAll(collection);

        System.out.println("get(1)");
        for (int i = 0; i < lists.length; i++) {
            System.out.println("\tлист № " + i + " > " + lists[i].get(1) +
                    " > " + lists[i] + " > " + lists[i].size());
        }

        System.out.println("set(1, \"element\")");
        for (int i = 0; i < lists.length; i++) {
            System.out.println("\tлист № " + i + " > " + lists[i].set(1, "element") +
                    " > " + lists[i] + " > " + lists[i].size());
        }

        System.out.println("add(2, \"element2\")");
        for (int i = 0; i < lists.length; i++) {
            lists[i].add(1, "element2");
            System.out.println("\tлист № " + i + " > " + lists[i] + " > " + lists[i].size());
        }

        System.out.println("remove(1)");
        for (int i = 0; i < lists.length; i++) {
            System.out.println("\tлист № " + i + " > " + lists[i].remove(1) +
                    " > " + lists[i] + " > " + lists[i].size());
        }

        System.out.println("indexOf(\"fqfe\")");
        for (int i = 0; i < lists.length; i++) {
            System.out.println("\tлист № " + i + " > " + lists[i].indexOf("fqfe") +
                    " > " + lists[i] + " > " + lists[i].size());
        }

        System.out.println("lastIndexOf(\"fqfe\")");
        for (int i = 0; i < lists.length; i++) {
            System.out.println("\tлист № " + i + " > " + lists[i].lastIndexOf("fqfe") +
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
        System.out.println("Обратный Итератор");
        for (int i = 0; i < lists.length; i++) {
            System.out.println("лист № " + i + " > " + lists[i] + " > " + lists[i].size());
            Iterator<String> descendingIterator = lists[i].descendingIterator();
            int index = 0;
            while (descendingIterator.hasNext()) {
                System.out.print("\t");
                System.out.print(index);
                System.out.print(") ");
                System.out.println(descendingIterator.next());
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
                if (listIterator.nextIndex() == 5) {
                    listIterator.remove();
                    System.out.println("\tУдаляю 5-й элемент> " + lists[i] + " > " + lists[i].size());
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
                if (listIterator.nextIndex() == 4) {
                    listIterator.remove();
                    System.out.println("\tУдаляю 4-й элемент> " + lists[i] + " > " + lists[i].size());
                }
                listIterator.previous();
            }
        }

        System.out.println("ЛистИтератор c индекса 2");
        for (int i = 0; i < lists.length; i++) {
            System.out.println("лист № " + i + " > " + lists[i] + " > " + lists[i].size());
            ListIterator<String> listIterator = lists[i].listIterator(2);
            while (listIterator.hasNext()) {
                System.out.print("\t");
                System.out.print(listIterator.nextIndex());
                System.out.print(") ");
                System.out.println(listIterator.next());
            }
        }
        System.out.println();
        System.out.println("Сравнение: ");
        LinkedList<String> list1 = new LinkedList<>();
        list1.add("dfsdf");
        list1.add("dfsdsdaf");
        list1.add("ddsdaf");
        list1.remove(1);
        LinkedList<String> list2 = new LinkedList<>();
        list2.add("dfsdf");
        list2.add("ddsdaf");
        System.out.println("лист1: " + list1 + " > " + list1.size());
        System.out.println("лист2: " + list2 + " > " + list1.size());
        System.out.println(list1.equals(list2));
    }
}
