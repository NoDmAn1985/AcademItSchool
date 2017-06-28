package ru.academits.novoselovda.threadpool;

public class Producer<T> implements Runnable {
    private ThreadPool<T> threadPool;
    private static int id;
    private int producerId;

    Producer(ThreadPool<T> threadPool) {
        this.producerId = id;
        ++id;
        this.threadPool = threadPool;
    }

    @Override
    public void run() {
        while (true) {
            try {
                if(!threadPool.getSourcesItems()) {
                    System.out.println("сборщик всё");
                    return;
                }
                Thread.sleep(threadPool.getProducerSleepTime());
                threadPool.addItem();
            } catch (InterruptedException e) {
                return;
            }
        }
    }

}
