package ru.academits.novoselovda.cashmachine.view;

import ru.academits.novoselovda.cashmachine.controller.Controller;
import ru.academits.novoselovda.notes.Money;

class ConsoleStatus {
    private Controller controller;

    ConsoleStatus(Controller controller) {
        this.controller = controller;
    }

    void show() {
        System.out.println("-----------------------------------------------------");
        System.out.println("ОПЕРАЦИЯ: Статус банкомата");
        System.out.println("-----------------------------------------------------");
        Money[] machineDeposit = this.controller.getCashMachineDeposit();

        for (int i = 0; i < machineDeposit.length; i++) {
            System.out.printf("%d) %4d - %3d шт.%n", i, machineDeposit[i].getValue().getCost(),
                    machineDeposit[i].getCount());
        }

        System.out.println("Итого в банкомате: " + this.controller.getCashMachineSum());
    }
}