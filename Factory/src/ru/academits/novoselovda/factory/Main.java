package ru.academits.novoselovda.factory;

import ru.academits.novoselovda.factory.control.Control;
import ru.academits.novoselovda.factory.view.FrameView;

public class Main {
    public static void main(String[] args) {
        FrameView view = new FrameView();
        Control control = new Control();
        control.addViewListener(view);
        view.addControlListener(control);
        view.startApplication();

    }
}
