package ru.academits.novoselovda.factory.model;

import ru.academits.novoselovda.factory.common.Items;
import ru.academits.novoselovda.factory.common.Workers;
import ru.academits.novoselovda.threadpool.Item;

import java.util.HashMap;

public class Provider implements Runnable {
    private Model model;
    private Workers worker;
    private HashMap<Items, Object> locks;

    Provider(Model model, Workers worker, HashMap<Items, Object> locks) {
        this.model = model;
        this.worker = worker;
        this.locks = locks;
    }

    @Override
    public void run() {
        Items itemName = worker.getItem();
        while (true) {
            if (model.isTargetAchieved()) {
                System.out.println("поставщики всё");
                return;
            }
            try {
                Thread.sleep(model.getSleepTime(worker));
            } catch (InterruptedException e) {
                return;
            }
            Item<Items> item = new Item<>(itemName);
            synchronized (locks.get(itemName)) {
                while (model.isStorageFull(itemName)) {
                    try {
                        locks.get(itemName).wait();
                    } catch (InterruptedException e) {
                        return;
                    }
                }
                model.addItemToStorage(itemName, item);
                locks.get(itemName).notifyAll();
            }
        }
    }
}

