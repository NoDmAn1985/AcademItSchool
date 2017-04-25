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
        for (int i = 0; i < this.controller.getCashMachineNotesCount(); i++) {
            System.out.println(i + ") " + this.controller.getCashMachineNoteValue(i) + " - " +
                    this.controller.getCashMachineNoteCount(i) + " шт.");
        }
        System.out.println("Итого в банкомате: " + this.controller.getCashMachineSum());
    }
}