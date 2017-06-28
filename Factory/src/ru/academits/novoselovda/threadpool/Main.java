package ru.academits.novoselovda.threadpool;

public class Main {
    public static void main(String[] args) {
        ThreadPool<Integer> thread = new ThreadPool<>(null, 1, 2, 3,
                null, null, null, null, null, null);
        thread.start();
    }
}
