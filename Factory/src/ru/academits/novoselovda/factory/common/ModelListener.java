package ru.academits.novoselovda.factory.common;

public interface ModelListener {
    void updateItemsCount(Items item, int storageCount, int producedCount);

    void updateTarget(int value);
}
