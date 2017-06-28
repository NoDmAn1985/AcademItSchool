package ru.academits.novoselovda.threadpool;

public class Item<T> {
    private static int id;
    private int itemId;
    private T data;
    private Item<T> source1;
    private Item<T> source2;
    private Item<T> source3;
    private final static Object lock = new Object();

    public Item(T data) {
        this(data, null, null, null);
    }

    public Item(T data, Item<T> source1, Item<T> source2, Item<T> source3) {
        this.itemId = getId();
        this.data = data;
        this.source1 = source1;
        this.source2 = source2;
        this.source3 = source3;
    }

    private static int getId() {
        synchronized (lock) {
            ++id;
            return id;
        }
    }

    @Override
    public String toString() {
        if (source1 == null || source2 == null || source3 == null) {
            return data + ": <" + itemId + ">";
        } else {
            return data + " <" + itemId + "> (" + source1 + ", " + source2 + ", " + source3 + ")";
        }
    }
}
