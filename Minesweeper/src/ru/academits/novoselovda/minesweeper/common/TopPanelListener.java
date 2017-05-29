package ru.academits.novoselovda.minesweeper.common;

public interface TopPanelListener {
    void needStartTimer();

    void needStopTimer();

    void needShowLost();

    void needShowWins();

    void needShowPressed();

    void needShowDefaultFace();

    void needUpdateFlagsCounter(int count);
}
