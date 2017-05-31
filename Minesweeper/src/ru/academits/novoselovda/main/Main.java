package ru.academits.novoselovda.main;

import ru.academits.novoselovda.minesweeper.common.View;
import ru.academits.novoselovda.minesweeper.control.Control;
import ru.academits.novoselovda.minesweeper.gui.FrameView;
import ru.academits.novoselovda.minesweeper.text.ConsoleView;

public class Main {
    public static void main(String[] args) {
        Control control = new Control();
//        View view = new FrameView(control);
        View view = new ConsoleView(control);
        view.startApplication();
    }
}
