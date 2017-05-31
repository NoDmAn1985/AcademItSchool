package ru.academits.novoselovda.minesweeper.text;

import ru.academits.novoselovda.minesweeper.common.AbstractFieldView;
import ru.academits.novoselovda.minesweeper.common.Signs;
import ru.academits.novoselovda.minesweeper.control.Control;

public class FieldView extends AbstractFieldView {
    private static final char VERTICAL_LINE = '\u2506';
    private static final char LINER = '\u2219';
    private String horizontalLine;

    private static final char CELL_ZERO = '\u2B1A';
    private static final char CELL_1 = '\u2460';
    private static final char CELL_2 = '\u2461';
    private static final char CELL_3 = '\u2462';
    private static final char CELL_4 = '\u2463';
    private static final char CELL_5 = '\u2464';
    private static final char CELL_6 = '\u2465';
    private static final char CELL_7 = '\u2466';
    private static final char CELL_8 = '\u2467';
    private static final char CELL_MINE = '\u2668';
    private static final char CELL_FLAG = '\u26A0';
    private static final char CELL_WRONG_FLAG = '\u2A3B';
    private static final char CELL_GAME_OVER = '\u2620';
    private static final char CELL_HIDE = '\u25A0';
    private static final char CELL_CHOSEN = '\u25A3';

    private char[][] map;

    FieldView(Control control) {
        this.control = control;
        getMinValues();
    }

    void showAllField() {
        System.out.println("Время прошло: " + this.control.getTime() + " секунд");
        System.out.println("Флагов осталось: " + this.control.getFlagsRemainCount(this.minesCount) + " шт.");
        System.out.printf("%2s %s", " ", VERTICAL_LINE);
        for (int i = 0; i < this.xCellsCount; i++) {
            System.out.printf("%2d%2s", (i + 1), VERTICAL_LINE);
        }
        System.out.println();
        System.out.println(this.horizontalLine);
        for (int y = 0; y < this.yCellsCount; ++y) {
            System.out.printf("%2d %s", (y + 1), VERTICAL_LINE);
            for (int x = 0; x < this.xCellsCount; ++x) {
                System.out.printf(" %s%s%s", this.map[y][x], LINER, VERTICAL_LINE);
            }
            System.out.println(" " + (y + 1));
            System.out.println(this.horizontalLine);
        }
        System.out.printf("%2s %s", " ", VERTICAL_LINE);
        for (int i = 0; i < this.xCellsCount; i++) {
            System.out.printf("%2d%2s", (i + 1), VERTICAL_LINE);
        }
        System.out.println();
    }

    @Override
    protected void change(Signs signs, int y, int x) {
        switch (signs) {
            case ZERO:
                this.map[y][x] = CELL_ZERO;
                break;
            case ONE:
                this.map[y][x] = CELL_1;
                break;
            case TWO:
                this.map[y][x] = CELL_2;
                break;
            case THREE:
                this.map[y][x] = CELL_3;
                break;
            case FOUR:
                this.map[y][x] = CELL_4;
                break;
            case FIVE:
                this.map[y][x] = CELL_5;
                break;
            case SIX:
                this.map[y][x] = CELL_6;
                break;
            case SEVEN:
                this.map[y][x] = CELL_7;
                break;
            case EIGHT:
                this.map[y][x] = CELL_8;
                break;
            case MINE:
                this.map[y][x] = CELL_MINE;
                break;
            case FLAG:
                this.map[y][x] = CELL_FLAG;
                break;
            case WRONG_FLAG:
                this.map[y][x] = CELL_WRONG_FLAG;
                break;
            case GAME_OVER:
                this.map[y][x] = CELL_GAME_OVER;
                break;
            default:
                this.map[y][x] = CELL_HIDE;
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
        this.map[yPos][xPos] = CELL_CHOSEN;
    }

    void hideCell(int yPos, int xPos) {
        change(Signs.HIDE, yPos, xPos);
    }
}
