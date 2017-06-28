package ru.academits.novoselovda.factory.common;

import java.util.HashMap;

public interface FrameViewListener {
    void setStartOptions(HashMap<Configuration, Integer> configurationIntegerHashMap);

    void updatePartsACount(int storageCount, int producedCount);

    void updatePartsBCount(int storageCount, int producedCount);

    void updatePartsCCount(int storageCount, int producedCount);

    void updateCarsCount(int storageCount, int producedCount);

    void updateTarget(int value);
}
