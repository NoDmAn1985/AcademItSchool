package ru.academits.novoselovda.minesweeper.model;

public class Timer {

    private long start = System.currentTimeMillis();

    public int getTime() {
        long now = System.currentTimeMillis();
        final int millsToSeconds = 1000;
        return (int) ((now - start) / millsToSeconds);
    }
}
