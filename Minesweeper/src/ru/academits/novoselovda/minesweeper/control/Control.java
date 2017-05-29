package ru.academits.novoselovda.minesweeper.control;

import ru.academits.novoselovda.minesweeper.model.Field;
import ru.academits.novoselovda.minesweeper.model.HighScore;
import ru.academits.novoselovda.minesweeper.model.Timer;

public class Control {
    private final int minYCounts = 9;
    private final int minXCounts = 9;
    private final int minMinesCounts = 10;
    
    private boolean isItFirstMove;
    private boolean isGameStarted;

    public boolean isItFirstMove() {
        return isItFirstMove;
    }

    public boolean isNewGameStarted() {
        return isGameStarted;
    }

    public void setNewGameStarted(boolean gameStarted) {
        isGameStarted = gameStarted;
    }

    private Field field;
    private HighScore highScore;
    private Timer timer;

    public Control() {
        this.highScore = new HighScore();
    }

    public HighScore getHighScore() {
        return this.highScore;
    }

    public void restart() {
        this.isItFirstMove = true;
        this.isGameStarted = false;
    }

    public void initField(int yPos, int xPos, int yCellsCount, int xCellsCount, int minesCount)
            throws IndexOutOfBoundsException {
        this.field = new Field(yPos, xPos, yCellsCount, xCellsCount, minesCount);
        this.isGameStarted = true;
        this.isItFirstMove = false;
        this.timer = new Timer();
    }

    public int getTime() {
        if (!this.isItFirstMove && this.timer != null) {
            return this.timer.getTime();
        }
        return 0;
    }

    public int getFlagsRemainCount(int minesCount) {
        if (this.field != null) {
            return this.field.getFlagsRemainCount();
        }
        return minesCount;
    }

    public int getMinYCounts() {
        return minYCounts;
    }

    public int getMinXCounts() {
        return minXCounts;
    }

    public int getMinMinesCounts() {
        return minMinesCounts;
    }

    public int getCellNumber(int yPos, int xPos) {
        return this.field.getCellInformation(yPos, xPos);
    }

    public void setCellShown(int yPos, int xPos) {
        this.field.setCellShown(yPos, xPos);
    }

    public void setFlagHere(boolean isFlagHere, int yPos, int xPos) {
        this.field.setFlagHere(isFlagHere, yPos, xPos);
    }

    public boolean isFlagHere(int yPos, int xPos) {
        return this.field.isFlagHere(yPos, xPos);
    }

    public boolean isAllNeighboringFlagsPutted(int yPos, int xPos) {
        return this.field.isAllNeighboringFlagsPutted(yPos, xPos);
    }

    public boolean isCellShown(int yPos, int xPos) {
        return this.field.isCellShown(yPos, xPos);
    }

    public boolean isCellZero(int yPos, int xPos) {
        return getCellNumber(yPos, xPos) == 0;
    }

    public boolean isMineHere(int yPos, int xPos) {
        return getCellNumber(yPos, xPos) == -1;
    }

    public boolean isWin() {
        if (this.field.isWin()) {
            this.isGameStarted = false;
            int time = getTime();
            saveScore(this.field.getYCellsCount(), this.field.getXCellsCount(), this.field.getMinesCount(), time);
            return true;
        }
        return false;
    }

    public boolean isLost() {
        if (this.field.isLost()) {
            this.isGameStarted = false;
            return true;
        }
        return false;
    }

    private void saveScore(int yCellCounts, int xCellCounts, int minesCount, int time) {
        this.highScore.save(yCellCounts, xCellCounts, minesCount, time);
    }

    public void setUserName(String userName) {
        this.highScore.setUserName(userName);
    }

    public String getHighScoreList() {
        return this.highScore.show();
    }
}
