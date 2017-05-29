package ru.academits.novoselovda.minesweeper.text;

import ru.academits.novoselovda.minesweeper.control.Control;
import ru.academits.novoselovda.minesweeper.model.About;

import java.util.Scanner;

class Menu {
    private final String dottedLine = "------------------------------------------------------------";

    private final int yMax = 20;
    private final int xMax = 49;

    private final int beginnerYCellsCount = 9;
    private final int beginnerXCellsCount = 9;
    private final int beginnerMinesCount = 10;

    private final int amateurYCellsCount = 16;
    private final int amateurXCellsCount = 16;
    private final int amateurMinesCount = 40;

    private final int professionalYCellsCount = 16;
    private final int professionalXCellsCount = 30;
    private final int professionalMinesCount = 99;

    private Control control;
    private FieldView fieldView;

    private int xCellsCount;
    private int yCellsCount;

    Menu(Control control, FieldView fieldView) {
        this.control = control;
        this.fieldView = fieldView;
        this.xCellsCount = this.fieldView.getXCellCount();
        this.yCellsCount = this.fieldView.getYCellCount();
    }

    void mainMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println(this.dottedLine);
            System.out.println("Меню:");
            System.out.println("1) Новая игра...");
            System.out.println("2) Новая игра (поле поумолчанию)");
            if (this.control.isNewGameStarted()) {
                System.out.println("3) Продолжить игру");
            }
            System.out.println("4) Рекорды");
            System.out.println("5) Сменить имя (" + this.control.getHighScore().getUserName() + ")");
            System.out.println("6) О программе");
            System.out.println("7) Выход");
            System.out.print("Введите номер: ");
            int number = scanner.nextInt();
            System.out.println(this.dottedLine);
            switch (number) {
                case 1:
                    if (newGameMenu()) {
                        return;
                    } else {
                        break;
                    }
                case 3:
                    return;
                case 4:
                    showHighScore();
                    break;
                case 5:
                    changeUserName();
                    break;
                case 6:
                    showAbout();
                    break;
                case 7:
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
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Новая игра:");
            System.out.println("1) Настроить поле");
            System.out.println("2) Новая игра (новичок)");
            System.out.println("3) Новая игра (любитель)");
            System.out.println("4) Новая игра (профессионал)");
            System.out.println("5) Предыдущее меню");
            System.out.print("Введите номер: ");
            int number = scanner.nextInt();
            switch (number) {
                case 1:
                    setFieldMenu();
                    return true;
                case 2:
                    setField(this.beginnerYCellsCount, this.beginnerXCellsCount, this.beginnerMinesCount);
                    return true;
                case 3:
                    setField(this.amateurYCellsCount, this.amateurXCellsCount, this.amateurMinesCount);
                    return true;
                case 4:
                    setField(this.professionalYCellsCount, this.professionalXCellsCount, this.professionalMinesCount);
                    return true;
                case 5:
                    return false;
                default:
                    System.out.println("ОШИБКА: нет такого номера");
            }
        }
    }

    private void changeUserName() {
        System.out.print("Введи своё имя или нажми << ENTER >> (чтобы остаться Anonymous): ");
        String userName = new Scanner(System.in).nextLine();
        if (userName.length() < 1 || !Character.isLetter(userName.charAt(0))) {
            userName = "Anonymous";
        }
        this.control.setUserName(userName);
    }

    private void setFieldMenu() {
        int yCellsCount;
        int xCellsCount;
        int minesCount;
        System.out.println(this.dottedLine);
        Scanner scanner = new Scanner(System.in);
        int yMin = this.control.getMinYCounts();
        System.out.println("Введите количество ячеек:");
        System.out.print("- по вертикали (от : " + yMin + " до " + this.yMax + "):");
        while (true) {
            yCellsCount = scanner.nextInt();
            if (yCellsCount >= yMin && yCellsCount <= this.yMax) {
                break;
            }
            System.out.println("ОШИБКА: неверные данные");
        }
        int xMin = this.control.getMinXCounts();
        System.out.print("- по горизонтали (от : " + xMin + " до " + this.xMax + "):");
        while (true) {
            xCellsCount = scanner.nextInt();
            if (xCellsCount >= xMin && xCellsCount <= this.xMax) {
                break;
            }
            System.out.println("ОШИБКА: неверные данные");
        }
        int minesMin = this.control.getMinMinesCounts();
        int minesMax = (int) (xCellsCount * yCellsCount * 0.7);
        while (true) {
            System.out.println("Введите количество мин (от : " + minesMin + " до " + minesMax + "):");
            minesCount = scanner.nextInt();
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

    private void showHighScore() {
        System.out.println("Таблица рекордов");
        System.out.print(this.control.getHighScoreList());
        System.out.print("нажмите <<ENTER>> для выхода в меню");
        new Scanner(System.in).nextLine();
    }

    private void showAbout() {
        System.out.println("О программе");
        System.out.println(new About().toString());
        System.out.print("нажмите <<ENTER>> для выхода в меню");
        new Scanner(System.in).nextLine();
    }

    void userMove() {
        System.out.println(this.dottedLine);
        Scanner scanner = new Scanner(System.in);
        int y;
        int x;
        do {
            while (true) {
                System.out.println("Введите координаты хода (или \"0\" - для вызова меню):");
                System.out.print("- по вертикали: ");
                y = scanner.nextInt() - 1;
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
                x = scanner.nextInt() - 1;
                if (x >= 0 && x < xCellsCount) {
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
            System.out.println("ОШИБКА: ячейка уже открыта");
        } while (true);
        this.fieldView.setCellChosen(y, x);
        System.out.println(this.dottedLine);
        this.fieldView.showAllField();
        System.out.println("Выберите что сделать с этой ячейкой:");
        System.out.println("1) открыть");
        System.out.println("2) поставить флаг");
        System.out.println("3) снять флаг");
        System.out.println("4) ничего, выбрать другую");
        int number;
        while (true) {
            number = scanner.nextInt();
            if (number == 1) {
                this.fieldView.openThisCell(y, x);
                break;
            } else if (number == 2) {
                this.fieldView.putFlag(y, x);
                break;
            } else if (number == 3) {
                this.fieldView.takeFlagOff(y, x);
                break;
            } else if (number == 4) {
                this.fieldView.hideCell(y, x);
                break;
            }
            System.out.println("ОШИБКА: неверный номер");
        }
    }

    void gameLost() {
        System.out.println(this.dottedLine);
        System.out.println("П Р О И Г Р Ы Ш Ь .");
        System.out.println(this.dottedLine);

    }

    void gameWin() {
        System.out.println(this.dottedLine);
        System.out.println("П О Б Е Д А ! ! !");
        System.out.println(this.dottedLine);
    }
}