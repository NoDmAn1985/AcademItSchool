package ru.academits.novoselovda.threadpool;

public class Consumer<T> implements Runnable {
    private ThreadPool<T> threadPool;
    private static int id;
    private int consumerId;

    Consumer(ThreadPool<T> threadPool) {
        this.consumerId = id;
        ++id;
        this.threadPool = threadPool;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Item<T> item = threadPool.getItem();
                if (item == null) {
                    System.out.println("продавец всё");
                    return;
                }
                Thread.sleep(threadPool.getConsumerSleepTime());
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    public int getId() {
        return consumerId;
    }
}
