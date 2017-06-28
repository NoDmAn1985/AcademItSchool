package ru.academits.novoselovda.factory.model;

import ru.academits.novoselovda.factory.common.*;
import ru.academits.novoselovda.factory.control.Control;
import ru.academits.novoselovda.threadpool.IntParameters;
import ru.academits.novoselovda.threadpool.Item;
import ru.academits.novoselovda.threadpool.ThreadPool;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Model implements ThreadPoolListener<Items> {
    private HashMap<Items, Integer> storageCapacitiesList = new HashMap<>();
    private HashMap<Items, Queue<Item<Items>>> storageList = new HashMap<>();
    private HashMap<Items, Integer> producedList = new HashMap<>();
    private Queue<Item<Items>> target = new LinkedList<>();

    private HashMap<Workers, Integer> workersCount = new HashMap<>();

    private HashMap<Workers, Integer> sleepTimesList = new HashMap<>();
    private static final int SPEED_TO_TIME_RATE = 10000;

    private HashMap<Items, Object> locks = new HashMap<>();
    private final Object lockPartA = new Object();
    private final Object lockPartB = new Object();
    private final Object lockPartC = new Object();

    private int scheduleCount;

    private Control control;
    private ThreadPool<Items> threadPool;

    public Model(Control control) {
        this.control = control;
        HashMap<Configuration, Integer> configuration = control.getConfig();

        workersCount.put(Workers.PROVIDER_A, configuration.get(Configuration.BODY_PROVIDERS_COUNT));
        workersCount.put(Workers.PROVIDER_B, configuration.get(Configuration.ENGINE_PROVIDERS_COUNT));
        workersCount.put(Workers.PROVIDER_C, configuration.get(Configuration.ACCESSORIES_PROVIDERS_COUNT));
        workersCount.put(Workers.PRODUCER, configuration.get(Configuration.PRODUCERS_COUNT));
        workersCount.put(Workers.DEALER, configuration.get(Configuration.DEALERS_COUNT));
        workersCount.put(Workers.CONTROLLER, configuration.get(Configuration.CONTROLLERS_COUNT));

        storageCapacitiesList.put(Items.PART_A, configuration.get(Configuration.BODY_STORAGE_SIZE));
        storageCapacitiesList.put(Items.PART_B, configuration.get(Configuration.ENGINE_STORAGE_SIZE));
        storageCapacitiesList.put(Items.PART_C, configuration.get(Configuration.ACCESSORIES_STORAGE_SIZE));
        storageCapacitiesList.put(Items.CAR, configuration.get(Configuration.CAR_STORAGE_SIZE));

        sleepTimesList.put(Workers.PROVIDER_A, getSleepTime(configuration.get(Configuration.BODY_PROVIDERS_SPEED)));
        sleepTimesList.put(Workers.PROVIDER_B, getSleepTime(configuration.get(Configuration.ENGINE_PROVIDERS_SPEED)));
        sleepTimesList.put(Workers.PROVIDER_C, getSleepTime(configuration.get(Configuration.ACCESSORIES_PROVIDERS_SPEED)));
        sleepTimesList.put(Workers.PRODUCER, getSleepTime(configuration.get(Configuration.PRODUCERS_SPEED)));
        sleepTimesList.put(Workers.DEALER, getSleepTime(configuration.get(Configuration.DEALERS_SPEED)));
        sleepTimesList.put(Workers.CONTROLLER, getSleepTime(configuration.get(Configuration.CONTROLLERS_SPEED)));

        scheduleCount = configuration.get(Configuration.TARGET_VALUE);

        locks.put(Items.PART_A, lockPartA);
        locks.put(Items.PART_B, lockPartB);
        locks.put(Items.PART_C, lockPartC);

        for (Items item : Items.values()) {
            storageList.put(item, new LinkedList<>());
            producedList.put(item, 0);
        }
    }

    public void start() {
        HashMap<IntParameters, Integer> intParameters = new HashMap<>();
        intParameters.put(IntParameters.SCHEDULE_COUNT, scheduleCount);
        intParameters.put(IntParameters.CAPACITY, storageCapacitiesList.get(Items.CAR));
        intParameters.put(IntParameters.PRODUCER_SLEEP_TIME, sleepTimesList.get(Workers.PRODUCER));
        intParameters.put(IntParameters.CONSUMER_SLEEP_TIME, sleepTimesList.get(Workers.DEALER));
        intParameters.put(IntParameters.CONTROLLER_SLEEP_TIME, sleepTimesList.get(Workers.CONTROLLER));
        intParameters.put(IntParameters.PRODUCERS_COUNT, workersCount.get(Workers.PRODUCER));
        intParameters.put(IntParameters.CONSUMERS_COUNT, workersCount.get(Workers.DEALER));

        threadPool = new ThreadPool<>(intParameters, Items.CAR, Items.PART_A, Items.PART_B,
                Items.PART_C, target, storageList, storageCapacitiesList, locks, this);
        threadPool.start();

        createProviders(Workers.PROVIDER_A, workersCount.get(Workers.PROVIDER_A));
        createProviders(Workers.PROVIDER_B, workersCount.get(Workers.PROVIDER_B));
        createProviders(Workers.PROVIDER_C, workersCount.get(Workers.PROVIDER_C));
    }

    private int getSleepTime(int speed) {
        return SPEED_TO_TIME_RATE / (speed * speed);
    }

    public void changeSpeed(Workers worker, int speed) {
        int time = getSleepTime(speed);
        sleepTimesList.replace(worker, time);
    }

    private void createProviders(Workers worker, int count) {
        if (!worker.isProvider()) {
            return;
        }
        for (int i = 0; i < count; i++) {
            Provider provider = new Provider(this, worker, locks);
            new Thread(provider).start();
        }
    }


    @Override
    public void getOneItem() {
        control.updateTarget(target.size());
        updateItemsCount(Items.CAR);
    }

    @Override
    public void addOneItem() {
        producedList.put(Items.CAR, producedList.get(Items.CAR) + 1);
        updateItemsCount(Items.CAR);
    }

    void addItemToStorage(Items itemName, Item<Items> item) {
        storageList.get(itemName).add(item);
        producedList.put(itemName, producedList.get(itemName) + 1);
        updateItemsCount(itemName);
    }

        @Override
    public void updateItemsCount(Items data) {
        if (data == Items.PART_A) {
            control.updateItemsCount(Items.PART_A, storageList.get(data).size(), producedList.get(Items.PART_A));
        } else if (data == Items.PART_B) {
            control.updateItemsCount(Items.PART_B, storageList.get(data).size(), producedList.get(Items.PART_B));
        } else if (data == Items.PART_C) {
            control.updateItemsCount(Items.PART_C, storageList.get(data).size(), producedList.get(Items.PART_C));
        } else {
            control.updateItemsCount(Items.CAR, storageList.get(data).size(), producedList.get(Items.CAR));
        }
    }

    boolean isStorageFull(Items item) {
        return storageList.get(item).size() >= storageCapacitiesList.get(item);
    }

    int getSleepTime(Workers worker) {
        return sleepTimesList.get(worker);
    }

    boolean isTargetAchieved() {
        return threadPool.isTargetAchieved();
    }
}
