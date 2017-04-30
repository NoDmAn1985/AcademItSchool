package ru.academits.novoselovda.main;

import ru.academits.novoselovda.hashtable.HashTable;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        @SuppressWarnings("unchecked") HashTable<String>[] hashTables = new HashTable[2];
        hashTables[0] = new HashTable<>();
        hashTables[1] = new HashTable<>(5);

        System.out.println("Исходные таблицы:");
        for (HashTable<String> table : hashTables) {
            System.out.println(table);
        }
        System.out.println();

        System.out.println("add(\"add(T t)\")");
        for (HashTable<String> table : hashTables) {
            System.out.println(table.add("\"add(T t)\"") + " > " + table);
        }
        System.out.println();

        System.out.println("addAll(collection1)");
        ArrayList<String> collection1 = new ArrayList<>();
        collection1.add("collection1 - 1x2");
        collection1.add("collection1 - 2x2");
        collection1.add("collection1 - 2x1");
        for (HashTable<String> table : hashTables) {
            System.out.println(table.addAll(collection1) + " > " + table);
        }
        System.out.println();

        System.out.println("clear()");
        for (HashTable<String> table : hashTables) {
            table.clear();
            System.out.println(table);
        }
        System.out.println();

        System.out.println("Новые таблицы:");
        ArrayList<String> collection2 = new ArrayList<>();
        collection2.add("2collection2 - 1x2");
        collection2.add("2collection2 - 2x2");
        collection2.add("2collection2 - 2x1");
        for (HashTable<String> table : hashTables) {
            table.addAll(collection1);
            table.addAll(collection2);
            System.out.println(table);
        }
        System.out.println();

        System.out.println("contains(\"collection1 - 2x1\")");
        for (HashTable<String> table : hashTables) {
            System.out.println(table.contains("collection1 - 2x1") + " > " + table);
        }
        System.out.println();

        System.out.println("isEmpty()");
        for (HashTable<String> table : hashTables) {
            System.out.println(table.isEmpty() + " > " + table);
        }
        System.out.println();

        System.out.println("remove(\"collection1 - 2x1\")");
        for (HashTable<String> table : hashTables) {
            System.out.println(table.remove("collection1 - 2x1") + " > " + table);
        }
        System.out.println();


        System.out.println("removeAll(collection1)");
        for (HashTable<String> table : hashTables) {
            System.out.println(table.removeAll(collection1) + " > " + table);
        }
        System.out.println();

        System.out.println("Новые таблицы:");
        for (HashTable<String> table : hashTables) {
            table.addAll(collection1);
            System.out.println(table);
        }
        System.out.println();

        System.out.println("retainAll(collection1)");
        for (HashTable<String> table : hashTables) {
            System.out.println(table.retainAll(collection1) + " > " + table);
        }
        System.out.println();

        System.out.println("toArray()");
        for (HashTable<String> table : hashTables) {
            System.out.println(Arrays.toString(table.toArray()) + " > " + table);
        }
        System.out.println();

        System.out.println("toArray(T1[] a)");
        String[] a = new String[5];
        for (HashTable<String> table : hashTables) {
            System.out.println(Arrays.toString(table.toArray(a)) + " > " + table);
        }
        System.out.println();

        System.out.println("equals");
        System.out.println(hashTables[0].equals(hashTables[1]));








    }
}
