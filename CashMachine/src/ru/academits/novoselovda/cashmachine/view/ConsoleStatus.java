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
        for (int i = 0; i < this.controller.getCashMachineDeposit().length; i++) {
            System.out.printf("%d) %4d - %3d шт.%n", i, this.controller.getCashMachineDeposit()[i].getValue().getCost(),
                    this.controller.getCashMachineDeposit()[i].getCount());
        }
        System.out.println("Итого в банкомате: " + this.controller.getCashMachineSum());
    }
}