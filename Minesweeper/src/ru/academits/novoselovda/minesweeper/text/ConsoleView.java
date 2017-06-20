package ru.academits.novoselovda.minesweeper.text;

import ru.academits.novoselovda.minesweeper.common.View;
import ru.academits.novoselovda.minesweeper.control.Control;

public class ConsoleView implements View {
    private Control control;
    private FieldView fieldView;
    private Menu menu;

    public ConsoleView(Control control) {
        this.control = control;
        this.fieldView = new FieldView(this.control);
        this.menu = new Menu(this.control, this.fieldView);
    }

    @Override
    public void startApplication() {
        while (true) {
            this.menu.mainMenu();
            this.fieldView.showAllField();
            while (true) {
                this.menu.userMove();
                if (this.control.isGameEnds()) {
                    break;
                }
                this.fieldView.showAllField();
            }
        }
    }
}
