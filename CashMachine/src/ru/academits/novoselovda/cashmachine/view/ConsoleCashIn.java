package ru.academits.novoselovda.cashmachine.view;

import ru.academits.novoselovda.cashmachine.controller.Controller;

import java.util.Scanner;

class ConsoleCashIn {
    private Controller controller;
    private ConsoleStatus status;

    ConsoleCashIn(Controller controller, ConsoleStatus status) {
        this.status = status;
        this.controller = controller;
    }

    void show() {
        System.out.println("-----------------------------------------------------");
        System.out.println("ОПЕРАЦИЯ: Приём денег");
        System.out.println("-----------------------------------------------------");
        System.out.println("До приёма денег было:");
        this.status.show();
        System.out.print("Вложите деньги в купюроприёмник и нажмите <ENTER>....");
        new Scanner(System.in).nextLine();
        System.out.println("-----------------------------------------------------");
        System.out.println("Вы вложили:");
        System.out.print(this.controller.showUserMoney());
        System.out.println("-----------------------------------------------------");
        try {
            this.controller.addMoney();
            System.out.println("После приёма денег:");
            this.status.show();
            System.out.println("Операция прошла успешно - деньги зачислены");
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            System.out.println("Операция была прервана");
        }
    }
}