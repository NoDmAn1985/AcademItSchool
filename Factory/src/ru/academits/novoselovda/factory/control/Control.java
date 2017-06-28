package ru.academits.novoselovda.factory.control;

import ru.academits.novoselovda.factory.common.*;
import ru.academits.novoselovda.factory.model.Config;
import ru.academits.novoselovda.factory.model.Model;

import java.util.HashMap;

public class Control implements ControlListener, ModelListener {
    private Config config;
    private Model model;
    private FrameViewListener frameViewListener;

    public Control() {
        config = new Config();
    }

    @Override
    public void getOptions() {
        config.loadConfigFromFile();
        clear();
    }

    public HashMap<Configuration, Integer> getConfig() {
        return config.getOptions();
    }

    @Override
    public void start() {
        model.start();
    }

    @Override
    public void clear() {
        model = new Model(this);
        frameViewListener.setStartOptions(config.getOptions());
    }

    @Override
    public void changeProvidersOfPartsASpeed(int value) {
        model.changeSpeed(Workers.PROVIDER_A, value);
    }

    @Override
    public void changeProvidersOfPartsBSpeed(int value) {
        model.changeSpeed(Workers.PROVIDER_B, value);
    }

    @Override
    public void changeProvidersOfPartsCSpeed(int value) {
        model.changeSpeed(Workers.PROVIDER_C, value);
    }

    @Override
    public void changeProducersSpeed(int value) {
        model.changeSpeed(Workers.PRODUCER, value);
    }

    @Override
    public void changeDealersSpeed(int value) {
        model.changeSpeed(Workers.DEALER, value);
    }

    @Override
    public void changeControllerSpeed(int value) {
        model.changeSpeed(Workers.CONTROLLER, value);
    }

    public void addViewListener(FrameViewListener frameViewListener) {
        this.frameViewListener = frameViewListener;
    }

    @Override
    public void updateItemsCount(Items item, int storageCount, int producedCount) {
        if (item == Items.PART_A) {
            frameViewListener.updatePartsACount(storageCount, producedCount);
        } else if (item == Items.PART_B) {
            frameViewListener.updatePartsBCount(storageCount, producedCount);
        } else if (item == Items.PART_C) {
            frameViewListener.updatePartsCCount(storageCount, producedCount);
        } else if (item == Items.CAR) {
            frameViewListener.updateCarsCount(storageCount, producedCount);
        }
    }
    @Override
    public void updateTarget(int value) {
        frameViewListener.updateTarget(value);
    }
}
