package ru.academits.novoselovda.minesweeper.text;

import ru.academits.novoselovda.minesweeper.common.ErrorShowMessageListener;
import ru.academits.novoselovda.minesweeper.common.GameOverListener;
import ru.academits.novoselovda.minesweeper.control.Control;
import ru.academits.novoselovda.minesweeper.model.About;

import java.util.Scanner;

class Menu implements ErrorShowMessageListener, GameOverListener {
    private final String dottedLine = "------------------------------------------------------------";

    private static final int Y_CELLS_COUNT_MAX = 20;
    private static final int X_CELLS_COUNT_MAX = 49;

    private static final int BEGINNER_Y_CELLS_COUNT = 9;
    private static final int BEGINNER_X_CELLS_COUNT = 9;
    private static final int BEGINNER_MINES_COUNT = 10;

    private static final int AMATEUR_Y_CELLS_COUNT = 16;
    private static final int AMATEUR_X_CELLS_COUNT = 16;
    private static final int AMATEUR_MINES_COUNT = 40;

    private static final int PROFESSIONAL_Y_CELLS_COUNT = 16;
    private static final int PROFESSIONAL_X_CELLS_COUNT = 30;
    private static final int PROFESSIONAL_MINES_COUNT = 99;

    private Scanner scanner;

    private Control control;
    private FieldView fieldView;

    private int xCellsCount;
    private int yCellsCount;

    Menu(Control control, FieldView fieldView) {
        this.control = control;
        this.control.setErrorMessageListener(this);
        this.control.setGameOverListener(this);
        this.fieldView = fieldView;
        this.xCellsCount = this.fieldView.getXCellCount();
        this.yCellsCount = this.fieldView.getYCellCount();
        this.scanner = new Scanner(System.in);
    }

    void mainMenu() {
        while (true) {
            System.out.println(this.dottedLine);
            System.out.println("Меню:");
            System.out.println("1) Новая игра...");
            System.out.println("2) Новая игра (поле поумолчанию)");
            if (this.control.isNewGameStarted()) {
                System.out.println("3) Продолжить игру");
            } else {
                System.out.println("3) ---------------");
            }
            System.out.println("4) Рекорды");
            System.out.println("5) О программе");
            System.out.println("6) Выход");
            System.out.print("Введите номер: ");
            int number;
            try {
                number = Integer.parseInt(this.scanner.nextLine());
            } catch (NumberFormatException exception) {
                System.out.println(this.dottedLine);
                System.out.println("ОШИБКА: должна быть цифра");
                continue;
            }
            System.out.println(this.dottedLine);
            switch (number) {
                case 1:
                    if (newGameMenu()) {
                        return;
                    } else {
                        break;
                    }
                case 3:
                    if (this.control.isNewGameStarted()) {
                        return;
                    }
                    System.out.println("ОШИБКА: нет такого номера");
                    break;
                case 4:
                    showHighScore();
                    break;
                case 5:
                    showAbout();
                    break;
                case 6:
                    System.exit(0);
                case 2:
                    this.fieldView.createField();
                    return;
                default:
                    System.out.println("ОШИБКА: нет такого номера");
            }
        }
    }

    private boolean newGameMenu() {
        while (true) {
            System.out.println("Новая игра:");
            System.out.println("1) Настроить поле");
            System.out.println("2) Новая игра (новичок)");
            System.out.println("3) Новая игра (любитель)");
            System.out.println("4) Новая игра (профессионал)");
            System.out.println("5) Предыдущее меню");
            System.out.print("Введите номер: ");
            int number;
            try {
                number = Integer.parseInt(this.scanner.nextLine());
            } catch (NumberFormatException exception) {
                System.out.println(this.dottedLine);
                System.out.println("ОШИБКА: должна быть цифра");
                continue;
            }
            switch (number) {
                case 1:
                    setFieldMenu();
                    return true;
                case 2:
                    setField(BEGINNER_Y_CELLS_COUNT, BEGINNER_X_CELLS_COUNT, BEGINNER_MINES_COUNT);
                    return true;
                case 3:
                    setField(AMATEUR_Y_CELLS_COUNT, AMATEUR_X_CELLS_COUNT, AMATEUR_MINES_COUNT);
                    return true;
                case 4:
                    setField(PROFESSIONAL_Y_CELLS_COUNT, PROFESSIONAL_X_CELLS_COUNT, PROFESSIONAL_MINES_COUNT);
                    return true;
                case 5:
                    return false;
                default:
                    System.out.println("ОШИБКА: нет такого номера");
            }
        }
    }

    @Override
    public void saveScore() {
        String defaultName = this.control.getHighScore().getUserName();
        System.out.print("Введи своё имя или нажми << ENTER >> (чтобы остаться \"" +
                defaultName + "\"): ");
        String userName = this.scanner.nextLine();
        if (userName.length() < 1) {
            userName = defaultName;
        }
        this.control.saveScore(userName);
    }

    private void setFieldMenu() {
        int yCellsCount;
        int xCellsCount;
        int minesCount;
        System.out.println(this.dottedLine);
        int yMin = this.control.getMinYCounts();
        System.out.println("Введите количество ячеек:");
        System.out.print("- по вертикали (от : " + yMin + " до " + Y_CELLS_COUNT_MAX + "):");
        while (true) {
            try {
                yCellsCount = Integer.parseInt(this.scanner.nextLine());
            } catch (NumberFormatException exception) {
                System.out.println(this.dottedLine);
                System.out.println("ОШИБКА: должна быть цифра");
                continue;
            }
            if (yCellsCount >= yMin && yCellsCount <= Y_CELLS_COUNT_MAX) {
                break;
            }
            System.out.println("ОШИБКА: неверные данные");
        }
        int xMin = this.control.getMinXCounts();
        System.out.print("- по горизонтали (от : " + xMin + " до " + X_CELLS_COUNT_MAX + "):");
        while (true) {
            try {
                xCellsCount = Integer.parseInt(this.scanner.nextLine());
            } catch (NumberFormatException exception) {
                System.out.println(this.dottedLine);
                System.out.println("ОШИБКА: должна быть цифра");
                continue;
            }
            if (xCellsCount >= xMin && xCellsCount <= X_CELLS_COUNT_MAX) {
                break;
            }
            System.out.println("ОШИБКА: неверные данные");
        }
        int minesMin = this.control.getMinMinesCounts();
        int minesMax = (int) (xCellsCount * yCellsCount * 0.7);
        while (true) {
            System.out.println("Введите количество мин (от : " + minesMin + " до " + minesMax + "):");
            try {
                minesCount = Integer.parseInt(this.scanner.nextLine());
            } catch (NumberFormatException exception) {
                System.out.println(this.dottedLine);
                System.out.println("ОШИБКА: должна быть цифра");
                continue;
            }
            if (minesCount >= minesMin && minesCount <= minesMax) {
                break;
            }
            System.out.println("ОШИБКА: неверные данные");
        }
        setField(yCellsCount, xCellsCount, minesCount);
    }

    private void setField(int yCellsCount, int xCellsCount, int minesCount) {
        this.xCellsCount = xCellsCount;
        this.yCellsCount = yCellsCount;
        this.fieldView.setParameters(this.yCellsCount, this.xCellsCount, minesCount);
        this.fieldView.createField();
    }

    @Override
    public void showHighScore() {
        System.out.println("Таблица рекордов");
        System.out.print(this.control.getHighScoreList());
        System.out.print("нажмите <<ENTER>> для выхода в меню");
        this.scanner.nextLine();
    }

    private void showAbout() {
        System.out.println("О программе");
        System.out.println(new About().toString());
        System.out.print("нажмите <<ENTER>> для выхода в меню");
        this.scanner.nextLine();
    }

    void userMove() {
        System.out.println(this.dottedLine);
        int y;
        int x;
        do {
            while (true) {
                System.out.println("Введите координаты хода (или \"0\" - для вызова меню):");
                System.out.print("- по вертикали: ");
                try {
                    y = Integer.parseInt(this.scanner.nextLine()) - 1;
                } catch (NumberFormatException exception) {
                    System.out.println(this.dottedLine);
                    System.out.println("ОШИБКА: должна быть цифра");
                    continue;
                }
                if (y >= 0 && y < yCellsCount) {
                    break;
                }
                if (y == -1) {
                    mainMenu();
                    if (this.control.isItFirstMove()) {
                        return;
                    }
                    System.out.println(this.dottedLine);
                    this.fieldView.showAllField();
                } else {
                    System.out.println("ОШИБКА: неверная координата");
                }
            }
            while (true) {
                System.out.print("- по горизонтали: ");
                try {
                    x = Integer.parseInt(this.scanner.nextLine()) - 1;
                } catch (NumberFormatException exception) {
                    System.out.println(this.dottedLine);
                    System.out.println("ОШИБКА: должна быть цифра");
                    continue;
                }                if (x >= 0 && x < xCellsCount) {
                    break;
                }
                if (x == -1) {
                    mainMenu();
                    if (this.control.isItFirstMove()) {
                        return;
                    }
                    System.out.println(this.dottedLine);
                    this.fieldView.showAllField();
                    System.out.println("Введите координаты хода (или \"0\" - для вызова меню):");
                } else {
                    System.out.println("ОШИБКА: неверная координата");
                }
            }
            if (this.control.isItFirstMove() || !this.control.isCellShown(y, x)) {
                break;
            }
            if (this.control.isAllNeighboringFlagsPutted(y, x)) {
                this.fieldView.openNeighborCells(y, x);
                return;
            } else {
                System.out.println("ОШИБКА: ячейка уже открыта и не все флаги проставлены вокруг");
            }
        } while (true);
        this.fieldView.setCellChosen(y, x);
        System.out.println(this.dottedLine);
        this.fieldView.showAllField();
        System.out.println("Выберите что сделать с этой ячейкой:");
        System.out.println("1) открыть");
        if (!this.control.isItFirstMove()) {
            System.out.println("2) поставить флаг");
            System.out.println("3) снять флаг");
        }
        System.out.println("4) ничего, выбрать другую");
        int number;
        while (true) {
            System.out.print("Введите номер: ");
            try {
                number = Integer.parseInt(this.scanner.nextLine());
            } catch (NumberFormatException exception) {
                System.out.println(this.dottedLine);
                System.out.println("ОШИБКА: должна быть цифра");
                continue;
            }
            if (number == 1) {
                this.fieldView.openThisCell(y, x);
                break;
            } else if (!this.control.isItFirstMove() && number == 2) {
                this.fieldView.putFlag(y, x);
                break;
            } else if (!this.control.isItFirstMove() && number == 3) {
                this.fieldView.takeFlagOff(y, x);
                break;
            } else if (number == 4) {
                if (this.control.isFlagHere(y, x)) {
                    this.fieldView.takeFlagOff(y, x);
                    this.fieldView.putFlag(y, x);
                } else {
                    this.fieldView.hideCell(y, x);
                }
                break;
            }
            System.out.println("ОШИБКА: неверный номер");
        }
    }

    @Override
    public void lost(int yPos, int xPos) {
        System.out.println(this.dottedLine);
        System.out.println("П Р О И Г Р Ы Ш Ь .");
        System.out.println(this.dottedLine);
        this.fieldView.gameLost(yPos, xPos);
        this.fieldView.showAllField();
        System.out.println(this.dottedLine);
    }

    @Override
    public void win() {
        System.out.println(this.dottedLine);
        System.out.println("П О Б Е Д А ! ! !");
        System.out.println(this.dottedLine);
        this.fieldView.openAllCells(true);
        this.fieldView.showAllField();
        System.out.println(this.dottedLine);
    }

    @Override
    public void needShowErrorMessage(String errorMessage) {
        System.out.println(this.dottedLine);
        System.out.println(errorMessage);
        System.out.println(this.dottedLine);
    }
}