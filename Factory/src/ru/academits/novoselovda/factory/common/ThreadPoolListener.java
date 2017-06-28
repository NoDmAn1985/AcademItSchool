package ru.academits.novoselovda.factory.common;

public interface ThreadPoolListener<T> {
    void getOneItem();

    void addOneItem();

    void updateItemsCount(T data);
}
