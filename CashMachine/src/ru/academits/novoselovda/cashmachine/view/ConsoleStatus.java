package ru.academits.novoselovda.cashmachine.view;

import ru.academits.novoselovda.cashmachine.controller.Controller;

class ConsoleStatus {
    private Controller controller;

    ConsoleStatus(Controller controller) {
        this.controller = controller;
    }

    void show() {
        System.out.println("-----------------------------------------------------");
        System.out.println("ОПЕРАЦИЯ: Статус банкомата");
        System.out.println("-----------------------------------------------------");
        System.out.println(controller.status());
    }
}