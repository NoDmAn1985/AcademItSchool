package ru.academits.novoselovda.minesweeper.text;

import ru.academits.novoselovda.minesweeper.common.AbstractFieldView;
import ru.academits.novoselovda.minesweeper.common.Signs;
import ru.academits.novoselovda.minesweeper.control.Control;

public class FieldView extends AbstractFieldView {
    private final char verticalLine = '\u2506';
    private final char liner = '\u2219';
    private String horizontalLine;

    private final char cellZero = '\u2B1A';
    private final char cell1 = '\u2460';
    private final char cell2 = '\u2461';
    private final char cell3 = '\u2462';
    private final char cell4 = '\u2463';
    private final char cell5 = '\u2464';
    private final char cell6 = '\u2465';
    private final char cell7 = '\u2466';
    private final char cell8 = '\u2467';
    private final char cellMine = '\u2668';
    private final char cellFlag = '\u26A0';
    private final char cellWrongFlag = '\u2A3B';
    private final char cellGameOver = '\u2620';
    private final char cellHide = '\u25A0';
    private final char cellChosen = '\u25A3';

    private char[][] map;

    private int time;

    FieldView(Control control) {
        this.control = control;
        getMinValues();
    }

    void showAllField() {
        this.time = this.control.getTime();
        System.out.println("Время прошло: " + this.time + " секунд");
        System.out.println("Флагов осталось: " + this.control.getFlagsRemainCount(this.minesCount) + " шт.");
        System.out.printf("%2s %s", " ", this.verticalLine);
        for (int i = 0; i < this.xCellsCount; i++) {
            System.out.printf("%2d%2s", (i + 1), this.verticalLine);
        }
        System.out.println();
        System.out.println(this.horizontalLine);
        for (int y = 0; y < this.yCellsCount; ++y) {
            System.out.printf("%2d %s", (y + 1), this.verticalLine);
            for (int x = 0; x < this.xCellsCount; ++x) {
                System.out.printf(" %s%s%s", this.map[y][x], this.liner, this.verticalLine);
            }
            System.out.println(" " + (y + 1));
            System.out.println(this.horizontalLine);
        }
        System.out.printf("%2s %s", " ", this.verticalLine);
        for (int i = 0; i < this.xCellsCount; i++) {
            System.out.printf("%2d%2s", (i + 1), this.verticalLine);
        }
        System.out.println();
    }

    @Override
    protected void change(Signs signs, int y, int x) {
        switch (signs) {
            case ZERO:
                this.map[y][x] = this.cellZero;
                break;
            case ONE:
                this.map[y][x] = this.cell1;
                break;
            case TWO:
                this.map[y][x] = this.cell2;
                break;
            case THREE:
                this.map[y][x] = this.cell3;
                break;
            case FOUR:
                this.map[y][x] = this.cell4;
                break;
            case FIVE:
                this.map[y][x] = this.cell5;
                break;
            case SIX:
                this.map[y][x] = this.cell6;
                break;
            case SEVEN:
                this.map[y][x] = this.cell7;
                break;
            case EIGHT:
                this.map[y][x] = this.cell8;
                break;
            case MINE:
                this.map[y][x] = this.cellMine;
                break;
            case FLAG:
                this.map[y][x] = this.cellFlag;
                break;
            case WRONG_FLAG:
                this.map[y][x] = this.cellWrongFlag;
                break;
            case GAME_OVER:
                this.map[y][x] = this.cellGameOver;
                break;
            default:
                this.map[y][x] = this.cellHide;
        }
    }

    void setParameters(int yCellsCount, int xCellsCount, int minesCount) {
        this.yCellsCount = yCellsCount;
        this.xCellsCount = xCellsCount;
        this.minesCount = minesCount;
    }

    void createField() {
        this.map = new char[this.yCellsCount][this.xCellsCount];
        for (int y = 0; y < this.yCellsCount; ++y) {
            for (int x = 0; x < this.xCellsCount; ++x) {
                change(Signs.HIDE, y, x);
            }
        }
        this.control.restart();
        createHorizontalLine();
    }

    private void createHorizontalLine() {
        char boldPlus = '\u254B';
        StringBuilder sb = new StringBuilder();

        sb.append("---");
        int length = this.xCellsCount + 1;
        for (int i = 0; i < length; i++) {
            sb.append(boldPlus).append("---");
        }
        this.horizontalLine = sb.toString();
    }

    int getXCellCount() {
        return this.xCellsCount;
    }

    int getYCellCount() {
        return this.yCellsCount;
    }

    void setCellChosen(int yPos, int xPos) {
        this.map[yPos][xPos] = this.cellChosen;
    }

    void hideCell(int yPos, int xPos) {
        change(Signs.HIDE, yPos, xPos);
    }
}
