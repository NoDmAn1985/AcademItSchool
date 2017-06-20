package ru.academits.novoselovda.minesweeper.common;

public interface GameOverListener {
    void win();

    void saveScore();

    void showHighScore();

    void lost(int yPos, int xPos);
}
