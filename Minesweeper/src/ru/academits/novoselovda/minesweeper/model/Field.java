package ru.academits.novoselovda.minesweeper.model;

import java.util.Random;

public class Field {
    private int xCellsCount;
    private int yCellsCount;
    private int minesCount;
    private int flagsCount;
    private int restCellsCount;

    private Cell[][] map;

    private boolean isLost;

    public Field(int firstYPos, int firstXPos, int yCellsCount, int xCellsCount, int minesCount)
            throws IndexOutOfBoundsException {
        if (yCellsCount < 1 || xCellsCount < 1 || minesCount < 1) {
            throw new IndexOutOfBoundsException();
        }
        this.isLost = false;
        this.yCellsCount = yCellsCount;
        this.xCellsCount = xCellsCount;
        this.minesCount = minesCount;
        this.map = new Cell[yCellsCount][xCellsCount];
        setMines(firstYPos, firstXPos);
        setNumbers();
        this.flagsCount = this.minesCount;
        this.restCellsCount = this.yCellsCount * this.xCellsCount;
    }

    private void setMines(int firstYPos, int firstXPos) {
        Random random = new Random();
        for (int mine = 0; mine < this.minesCount; ++mine) {
            int yMinePos;
            int xMinePos;
            do {
                do {
                    yMinePos = random.nextInt(yCellsCount);
                    xMinePos = random.nextInt(xCellsCount);
                } while (yMinePos == firstYPos && xMinePos == firstXPos);
            } while (this.map[yMinePos][xMinePos] != null);
            this.map[yMinePos][xMinePos] = new Cell();
            this.map[yMinePos][xMinePos].setMine();
        }
    }

    private void setNumbers() {
        for (int y = 0; y < this.yCellsCount; ++y) {
            for (int x = 0; x < this.xCellsCount; ++x) {
                if (this.map[y][x] == null) {
                    getMinesCount(y, x);
                }
            }
        }
    }

    private void getMinesCount(int yPos, int xPos) {
        int count = 0;
        int yMin = (yPos == 0 ? yPos : yPos - 1);
        int yMax = (yPos == this.yCellsCount - 1 ? yPos : yPos + 1);
        int xMin = (xPos == 0 ? xPos : xPos - 1);
        int xMax = (xPos == this.xCellsCount - 1 ? xPos : xPos + 1);
        for (int y = yMin; y <= yMax; y++) {
            for (int x = xMin; x <= xMax; x++) {
                if (this.map[y][x] != null && this.map[y][x].isMineHere()) {
                    ++count;
                }
            }
        }
        this.map[yPos][xPos] = new Cell(count);
    }

    public int getCellInformation(int yPos, int xPos) {
        return this.map[yPos][xPos].getNumber();
    }

    public boolean isCellShown(int yPos, int xPos) {
        return this.map[yPos][xPos].isShown();
    }

    public void setCellShown(int yPos, int xPos) {
        if (this.map[yPos][xPos].isMineHere()) {
            this.isLost = true;
        }
        this.map[yPos][xPos].setShown();
        --this.restCellsCount;
    }

    public void setFlagHere(boolean isFlagHere, int yPos, int xPos) {
        this.map[yPos][xPos].setFlagHere(isFlagHere);
        if (isFlagHere) {
            --this.flagsCount;
            --this.restCellsCount;
        } else {
            ++this.flagsCount;
            ++this.restCellsCount;
        }
    }

    public boolean isFlagHere(int yPos, int xPos) {
        return this.map[yPos][xPos].isFlagHere();
    }

    public boolean isAllNeighboringFlagsPutted(int yPos, int xPos) {
        int count = 0;
        int yMin = (yPos == 0 ? yPos : yPos - 1);
        int yMax = (yPos == this.yCellsCount - 1 ? yPos : yPos + 1);
        int xMin = (xPos == 0 ? xPos : xPos - 1);
        int xMax = (xPos == this.xCellsCount - 1 ? xPos : xPos + 1);
        for (int y = yMin; y <= yMax; y++) {
            for (int x = xMin; x <= xMax; x++) {
                if (this.map[y][x].isFlagHere()) {
                    ++count;
                }
            }
        }
        return (this.map[yPos][xPos].getNumber() == count);
    }

    public int getFlagsRemainCount() {
        return this.flagsCount;
    }

    public boolean isWin() {
        return (this.restCellsCount - this.flagsCount == 0);
    }

    public boolean isLost() {
        return (isLost || this.restCellsCount - this.flagsCount < 0);
    }

    public int getXCellsCount() {
        return xCellsCount;
    }

    public int getYCellsCount() {
        return yCellsCount;
    }

    public int getMinesCount() {
        return minesCount;
    }
}
