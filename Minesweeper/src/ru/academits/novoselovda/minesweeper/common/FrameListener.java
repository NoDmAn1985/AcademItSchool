package ru.academits.novoselovda.minesweeper.common;

public interface FrameListener {
    void needStartNewGame(int yCellsCount, int xCellsCount, int minesCount);

    void needRestart();

    int getCellSize();
}
