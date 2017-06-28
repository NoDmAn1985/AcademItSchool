package ru.academits.novoselovda.threadpool;

public class Controller<T> implements Runnable {
    private ThreadPool<T> threadPool;

    Controller(ThreadPool<T> threadPool) {
        this.threadPool = threadPool;
    }

    @Override
    public void run() {
        while (true) {
            try {
                threadPool.control();
                Thread.sleep(threadPool.getControllerSleepTime());
                if (!threadPool.updateTaskCount()) {
                    System.out.println("котроль всё");
                    return;
                }
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
