package ru.academits.novoselovda.cashmachine.view;

import ru.academits.novoselovda.cashmachine.controller.Controller;
import ru.academits.novoselovda.notes.Money;

import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleMainMenu {
    private Controller controller;
    private ConsoleStatus status;
    private Scanner scanner;

    public ConsoleMainMenu(Controller controller) {
        this.controller = controller;
        this.status = new ConsoleStatus(controller);
        scanner = new Scanner(System.in);
    }

    public ArrayList<Money> show() {
        while (true) {
            System.out.println("Выберите операцию: ");
            System.out.println("1) Приём денег");
            System.out.println("2) Выдача денег");
            System.out.println("3) Статус банкомата");
            System.out.println("4) Выключить банкомат");
            int operationNumber = scanner.nextInt();
            switch (operationNumber) {
                case 1:
                    new ConsoleCashIn(controller, status).show();
                    break;
                case 2:
                    return new ConsoleCashOut(controller, status).show();
                case 3:
                    status.show();
                    break;
                case 4:
                    return null;
                default:
                    break;
            }
            System.out.println("-----------------------------------------------------");
        }
    }
}