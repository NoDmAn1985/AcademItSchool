package ru.academits.novoselovda.cashmachine.view;

import ru.academits.novoselovda.cashmachine.controller.Controller;
import ru.academits.novoselovda.notes.Money;
import ru.academits.novoselovda.notes.Values;

import java.util.Scanner;

class ConsoleCashIn {
    private Controller controller;
    private ConsoleStatus status;
    private Scanner scanner;

    ConsoleCashIn(Controller controller, ConsoleStatus status) {
        this.status = status;
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    void show() {
        Money[] userMoney = new Money[Values.values().length];
        int index = 0;
        for (Values value : Values.values()) {
            userMoney[index] = new Money(value, 0);
            ++index;
        }
        System.out.println("-----------------------------------------------------");
        System.out.println("ОПЕРАЦИЯ: Приём денег");
        System.out.println("-----------------------------------------------------");
        System.out.println("До приёма денег было:");
        this.status.show();
        System.out.println("Вложите деньги в купюроприёмник:");
        one:
        while (true) {
            index = 0;
            for (Money money : userMoney) {
                System.out.printf("%d) %4d - %3d шт.%n", index, money.getValue().getCost(), money.getCount());
                ++index;
            }
            int number;
            while (true) {
                System.out.println("Введите номер купюры или \"" + index + "\" для завершения, или \"" + (index + 1) +
                        "\" для выхода в главное меню:");
                number = scanner.nextInt();
                if (number >= 0 && number < index) {
                    break;
                } else if (number == index) {
                    break one;
                } else if (number == index + 1) {
                    System.out.println("Операция была прервана");
                    return;
                }
                System.out.println("ОШИБКА: нет такого номера, попробуйте ещё раз");
            }
            int count;
            while (true) {
                System.out.println("Введите количество купюр:");
                count = scanner.nextInt();
                if (count >= 0) {
                    break;
                }
                System.out.println("ОШИБКА: число должно быть положительным, попробуйте ещё раз");
            }
            userMoney[number].add(count);
            System.out.println("-----------------------------------------------------");
        }
        System.out.println("-----------------------------------------------------");
        try {
            this.controller.addMoney(userMoney);
            System.out.println("После приёма денег:");
            this.status.show();
            System.out.println("Операция прошла успешно - деньги зачислены");
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            System.out.println("Операция была прервана");
        }
    }
}