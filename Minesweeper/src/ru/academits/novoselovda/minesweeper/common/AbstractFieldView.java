package ru.academits.novoselovda.minesweeper.common;

import ru.academits.novoselovda.minesweeper.control.Control;

public abstract class AbstractFieldView {
    protected int yCellsCount;
    protected int xCellsCount;
    protected int minesCount;

    protected Control control;

    protected abstract void change(Signs signs, int y, int x);

    protected void getMinValues() {
        this.yCellsCount = this.control.getMinYCounts();
        this.xCellsCount = this.control.getMinXCounts();
        this.minesCount = this.control.getMinMinesCounts();
    }

    private void getCellImage(int y, int x) {
        int cellNumber = this.control.getCellNumber(y, x);
        Signs signs;
        switch (cellNumber) {
            case 0:
                signs = Signs.ZERO;
                break;
            case 1:
                signs = Signs.ONE;
                break;
            case 2:
                signs = Signs.TWO;
                break;
            case 3:
                signs = Signs.THREE;
                break;
            case 4:
                signs = Signs.FOUR;
                break;
            case 5:
                signs = Signs.FIVE;
                break;
            case 6:
                signs = Signs.SIX;
                break;
            case 7:
                signs = Signs.SEVEN;
                break;
            case 8:
                signs = Signs.EIGHT;
                break;
            default:
                signs = Signs.MINE;
        }
        change(signs, y, x);
        this.control.setCellShown(y, x);
    }

    public void openThisCell(int yPos, int xPos) {
        if (this.control.isItFirstMove()) {
            this.control.initField(yPos, xPos, this.yCellsCount, this.xCellsCount, this.minesCount);
        }
        int cellNumber = this.control.getCellNumber(yPos, xPos);
        if (this.control.isFlagHere(yPos, xPos)) {
            this.control.setFlagHere(false, yPos, xPos);
        }
        if (cellNumber == -1) {
            this.control.setCellShown(yPos, xPos);
        } else if (cellNumber > 0) {
            getCellImage(yPos, xPos);
        } else {
            getCellImage(yPos, xPos);
            openNeighborCells(yPos, xPos);
        }
    }

    public void openAllCells(boolean isWin) {
        for (int y = 0; y < this.yCellsCount; y++) {
            for (int x = 0; x < AbstractFieldView.this.xCellsCount; x++) {
                if (AbstractFieldView.this.control.isCellShown(y, x)) {
                    continue;
                }
                if (!isWin && AbstractFieldView.this.control.isFlagHere(y, x) &&
                        AbstractFieldView.this.control.getCellNumber(y, x) != -1) {
                    change(Signs.WRONG_FLAG, y, x);
                } else if (!AbstractFieldView.this.control.isFlagHere(y, x)) {
                    if (isWin && this.control.isMineHere(y, x)) {
                        change(Signs.FLAG, y, x);
                    } else {
                        getCellImage(y, x);
                    }
                }
            }
        }
    }

    public void openNeighborCells(int yPos, int xPos) {
        int yMin = (yPos == 0 ? yPos : yPos - 1);
        int yMax = (yPos == yCellsCount - 1 ? yPos : yPos + 1);
        int xMin = (xPos == 0 ? xPos : xPos - 1);
        int xMax = (xPos == xCellsCount - 1 ? xPos : xPos + 1);
        for (int y = yMin; y <= yMax; y++) {
            for (int x = xMin; x <= xMax; x++) {
                if (AbstractFieldView.this.control.isCellShown(y, x) || AbstractFieldView.this.control.isFlagHere(y, x)) {
                    continue;
                }
                if (AbstractFieldView.this.control.isMineHere(y, x)) {
                    this.control.setCellShown(y, x);
                    gameLost(y, x);
                    return;
                }
                getCellImage(y, x);
                if (AbstractFieldView.this.control.isCellZero(y, x)) {
                        openNeighborCells(y, x);
                }
            }
        }
    }

    public void gameLost(int yPos, int xPos) {
        if (yPos != - 1 && xPos != -1) {
            change(Signs.GAME_OVER, yPos, xPos);
        } else {
            this.control.setNewGameStarted(false);
        }
        openAllCells(false);
    }


    public void putFlag(int yPos, int xPos) {
        if (this.control.getFlagsRemainCount(this.minesCount) == 0) {
            return;
        }
        change(Signs.FLAG, yPos, xPos);
        this.control.setFlagHere(true, yPos, xPos);
    }

    public void takeFlagOff(int yPos, int xPos) {
        change(Signs.HIDE, yPos, xPos);
        this.control.setFlagHere(false, yPos, xPos);
    }

    public void putQuestion(int yPos, int xPos) {
        change(Signs.QUESTION, yPos, xPos);
    }

    public void takeQuestionOff(int yPos, int xPos) {
        change(Signs.HIDE, yPos, xPos);
    }
}
