package ru.academits.novoselovda.cashmachine.view;

import ru.academits.novoselovda.cashmachine.controller.Controller;
import ru.academits.novoselovda.notes.Money;
import ru.academits.novoselovda.notes.Values;

import java.util.ArrayList;
import java.util.Scanner;

class ConsoleCashOut {
    private Controller controller;
    private ConsoleStatus status;
    private Scanner scanner;
    private int requiredSum;
    private int requiredNoteNumber;

    ConsoleCashOut(Controller controller, ConsoleStatus status) {
        this.status = status;
        this.controller = controller;
        scanner = new Scanner(System.in);
    }

    ArrayList<Money> show() {
        System.out.println("-----------------------------------------------------");
        System.out.println("ОПЕРАЦИЯ: Выдача денег");
        System.out.println("-----------------------------------------------------");
        if (isUserWantsToCashOut()) {
            ArrayList<Money> cashForUser;
            try {
                cashForUser = this.controller.getCashOut(this.requiredSum, Values.values()[this.requiredNoteNumber]);
                status.show();
                System.out.println("-----------------------------------------------------");
                System.out.println("Операция прошла успешно - возьмите деньги:");
                for (Money money : cashForUser) {
                    System.out.printf("- %4d - %3d шт.%n", money.getValue().getCost(), money.getCount());
                }
                return cashForUser;
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
            }
        }
        System.out.println("Операция была прервана");
        return null;
    }

    private boolean isUserWantsToCashOut() {
        while (true) {
            System.out.print("Введите требуемую сумму: ");
            this.requiredSum = scanner.nextInt();
            try {
                this.controller.testRequiredSum(this.requiredSum);
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
                continue;
            }
            break;
        }
        System.out.println("Купюрами какого достоинства произвести выдачу:");

        for (int i = 0; i < this.controller.getCashMachineDeposit().length; i++) {
            if (this.controller.getCashMachineDeposit()[i].getCount() != 0) {
                System.out.println(i + ") " + this.controller.getCashMachineDeposit()[i].getValue().getCost());
            }
        }

        while (true) {
            boolean isNeedToRepeat = true;
            System.out.print("Введите номер купюры: ");
            this.requiredNoteNumber = this.scanner.nextInt();
            try {
                this.controller.testRequiredNote(this.requiredSum, this.requiredNoteNumber);
                isNeedToRepeat = false;
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
            } catch (ArithmeticException exception) {
                System.out.println(exception.getMessage());
                System.out.println("1) выдавать остаток купюрами более низкого номинала");
                System.out.println("2) изменить запрашиваемую купюру");
                System.out.println("3) отменить операцию и выйти в основное меню");
                while (true) {
                    System.out.println("Введите номер ответа:");
                    int answer = this.scanner.nextInt();
                    switch (answer) {
                        case 1:
                            return true;
                        case 2:
                            isNeedToRepeat = true;
                            break;
                        case 3:
                            return false;
                        default:
                            System.out.println("ОШИБКА: нет такого номера");
                            isNeedToRepeat = false;
                    }
                    if (isNeedToRepeat) {
                        break;
                    }
                }
            }
            if (!isNeedToRepeat) {
                return true;
            }
        }
    }
}