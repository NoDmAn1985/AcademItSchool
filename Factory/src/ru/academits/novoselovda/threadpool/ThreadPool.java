package ru.academits.novoselovda.threadpool;

import ru.academits.novoselovda.factory.common.ThreadPoolListener;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class ThreadPool<T> {
    private static final int TASK_RATE = 2;

    private final Object lock1 = new Object();
    private final Object lock2 = new Object();
    private final Object lock3 = new Object();
    private int defaultTaskCount;
    private int taskCount;
    private int completedCount;
    private T resultItem;
    private boolean isControllerNeeds;
    private HashMap<IntParameters, Integer> intParameters;
    private Queue<Item<T>> target = new LinkedList<>();
    private HashMap<T, Queue<Item<T>>> storageList;
    private HashMap<T, Integer> storageCapacitiesList;
    private HashMap<T, Object> locks;
    private final Object controllerLock = new Object();
    private boolean isTargetAchieved = false;
    private T source1;
    private T source2;
    private T source3;
    private int producersCount;

    private ThreadPoolListener threadPoolListener;

    public ThreadPool(HashMap<IntParameters, Integer> intParameters, T resultItem, T source1, T source2, T source3,
                      Queue<Item<T>> target, HashMap<T, Queue<Item<T>>> storageList, HashMap<T, Integer> storageCapacitiesList,
                      HashMap<T, Object> locks, ThreadPoolListener threadPoolListener) {
        this.intParameters = intParameters;
        int max = Math.max(intParameters.get(IntParameters.CONSUMERS_COUNT),
                intParameters.get(IntParameters.PRODUCERS_COUNT));
        defaultTaskCount = TASK_RATE * max;
        taskCount = Math.min(defaultTaskCount, intParameters.get(IntParameters.SCHEDULE_COUNT));
        this.resultItem = resultItem;
        this.target = target;
        this.storageList = storageList;
        this.storageCapacitiesList = storageCapacitiesList;
        this.locks = locks;
        this.source1 = source1;
        this.source2 = source2;
        this.source3 = source3;
        this.threadPoolListener = threadPoolListener;
        producersCount = intParameters.get(IntParameters.PRODUCERS_COUNT);
    }

    public void start() {
        int consumersCount = intParameters.get(IntParameters.CONSUMERS_COUNT);
        for (int i = 0; i < consumersCount; i++) {
            new Thread(new Consumer<>(this)).start();
        }

        for (int i = 0; i < producersCount; i++) {
            new Thread(new Producer<>(this)).start();
        }

        new Thread(new Controller<>(this)).start();
    }

    Item<T> getItem() throws InterruptedException {
        synchronized (lock1) {
            while (storageList.get(resultItem).size() <= 0) {
                if (isTargetAchieved) {
                    lock1.notifyAll();
                    return null;
                }
                lock1.wait();
            }
            Item<T> item = storageList.get(resultItem).remove();
            target.add(item);
            threadPoolListener.getOneItem();
            synchronized (lock2) {
                isControllerNeeds = true;
                lock2.notifyAll();
            }
            lock1.notifyAll();
            ++completedCount;
            return item;
        }
    }

    boolean getSourcesItems() throws InterruptedException {
        synchronized (controllerLock) {
            if (isTargetAchieved) {
                controllerLock.notifyAll();
                return false;
            }
            while (taskCount <= 0) {
                controllerLock.wait();
            }
            --taskCount;
        }
        Thread thread1 = new Thread(() -> getSourceItem(source1));
        Thread thread2 = new Thread(() -> getSourceItem(source2));
        Thread thread3 = new Thread(() -> getSourceItem(source3));

        thread1.start();
        thread2.start();
        thread3.start();
        thread1.join();
        thread2.join();
        thread3.join();
        return true;
    }

    private void getSourceItem(T sourceItem) {
        synchronized (locks.get(sourceItem)) {
            while (storageList.get(sourceItem).size() <= 0) {
                try {
                    locks.get(sourceItem).wait();
                } catch (InterruptedException e) {
                    return;
                }
            }
            storageList.get(sourceItem).remove();
            threadPoolListener.updateItemsCount(sourceItem);
            locks.get(sourceItem).notifyAll();
        }
    }

    void addItem() throws InterruptedException {
        Item<T> item = new Item<>(resultItem);
        synchronized (lock1) {
            while (storageList.get(resultItem).size() >= intParameters.get(IntParameters.CAPACITY)) {
                lock1.wait();
            }
            storageList.get(resultItem).add(item);
            threadPoolListener.addOneItem();
            synchronized (controllerLock) {
            }
            lock1.notifyAll();
        }
    }

    void control() throws InterruptedException {
        synchronized (lock2) {
            while (!isControllerNeeds) {
                lock2.wait();
            }
        }
    }

    int getProducerSleepTime() {
        return intParameters.get(IntParameters.PRODUCER_SLEEP_TIME);
    }

    int getConsumerSleepTime() {
        return intParameters.get(IntParameters.CONSUMER_SLEEP_TIME);
    }

    int getControllerSleepTime() {
        return intParameters.get(IntParameters.CONTROLLER_SLEEP_TIME);
    }

    boolean updateTaskCount() {
        synchronized (controllerLock) {
            synchronized (lock1) {
                int leftCount = intParameters.get(IntParameters.SCHEDULE_COUNT) - completedCount - taskCount - producersCount;
                if (leftCount <= 0) {
                    isTargetAchieved = true;
                    return false;
                } else {
                    taskCount = Math.min(defaultTaskCount, leftCount);
                }
            }
            controllerLock.notifyAll();
            isControllerNeeds = false;
            return  true;
        }
    }

    public boolean isTargetAchieved() {
        return isTargetAchieved;
    }
}