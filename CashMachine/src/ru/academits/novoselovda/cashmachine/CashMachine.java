package ru.academits.novoselovda.cashmachine;

import ru.academits.novoselovda.notes.BundleOfMoney;
import ru.academits.novoselovda.notes.Note;

import java.util.Scanner;

public class CashMachine {
    private Scanner scanner;
    private Machine machine;
    private int requiredSum;
    private Note.Values requiredNote;
    private boolean isWork;

    private NotesBase cashMachineBase;
    private NotesBase userBase;

    private final int NOTES_MAX_COUNT = 200;

    public CashMachine(BundleOfMoney[] startMoney) {
        isWork = true;
        scanner = new Scanner(System.in);
        for (BundleOfMoney bundle : startMoney) {
            if (bundle != null && bundle.getCount() > NOTES_MAX_COUNT) {
                System.out.println("ОШИБКА: максимальное количество хранимых купюр - " + NOTES_MAX_COUNT);
                isWork = false;
            }
        }
        cashMachineBase = new NotesBase(startMoney, NOTES_MAX_COUNT);
        machine = new Machine(startMoney);
    }

    public BundleOfMoney[] mainMenu() {
        BundleOfMoney[] cashForUser = null;
        while (isWork) {
            System.out.println("Выберите операцию: ");
            System.out.println("1) Приём денег");
            System.out.println("2) Выдача денег");
            System.out.println("3) Статус банкомата");
            System.out.println("4) Выключить банкомат");
            int operationNumber = scanner.nextInt();
            switch (operationNumber) {
                case 1:
                    this.userBase = new NotesBase(NOTES_MAX_COUNT);
                    if (isUserWantToCashIn()) {
                        BundleOfMoney[] userPurse = this.userBase.toBundle();
                        try {
                            this.cashMachineBase.add(this.userBase);
                            machine.cashIn(userPurse);
                            status();
                            System.out.println("Операция прошла успешно - деньги зачислены");
                        } catch (IllegalArgumentException exception) {
                            System.out.println(exception.getMessage());
                            System.out.println("Операция была прервана");
                        }
                    } else if (!isUserWantToCashIn()) {
                        System.out.println("Операция была прервана");
                    }
                    break;
                case 2:
                    if (isUserWantToCashOut()) {
                        System.out.println(this.requiredSum);
                        System.out.println(this.requiredNote);
                        this.userBase = new NotesBase(this.cashMachineBase, this.requiredSum, this.requiredNote);
                        this.cashMachineBase.subtract(userBase);
                        try {
                            cashForUser = machine.cashOut(userBase, requiredSum, requiredNote);
                            status();
                            System.out.println("Операция прошла успешно - возьмите деньги");
                        } catch (IllegalArgumentException exception) {
                            System.out.println(exception.getMessage());
                            System.out.println("Операция была прервана");
                        }
                    } else if (!isUserWantToCashOut()) {
                        System.out.println("Операция была прервана");
                    }
                    break;
                case 3:
                    status();
                    break;
                default:
                    return cashForUser;
            }
            System.out.println("-----------------------------------------------------");
        }
        return cashForUser;
    }

    private boolean isUserWantToCashIn() {
        System.out.println("-----------------------------------------------------");
        System.out.println("ОПЕРАЦИЯ: Приём денег");
        System.out.println("-----------------------------------------------------");
        System.out.println("Какие купюры и в каком количестве вы хотите положить:");
        System.out.print(this.userBase.toStringWithCount());
        while (true) {
            System.out.print("Введите номер купюры: ");
            int number = scanner.nextInt();
            if (number < 0 || number >= userBase.getSize()) {
                System.out.println("ОШИБКА: нет такого номера");
                continue;
            }
            System.out.print("Введите количество данных купюр: ");
            int tempNotesCount = scanner.nextInt();
            if (tempNotesCount < 0) {
                System.out.println("ОШИБКА: количество должно быть больше нуля");
                continue;
            }
            userBase.setCount(number, tempNotesCount);
            System.out.println("-----------------------------------------------------");
            System.out.print(this.userBase.toStringWithCount());
            System.out.println("Введите:");
            System.out.println("1) Для выполнения данной операции");
            System.out.println("2) Для изменения данных");
            System.out.println("3) Для сброса значений");
            System.out.println("4) Для выхода в предыдущее меню");
            int operation = this.scanner.nextInt();
            switch (operation) {
                case 1:
                    return true;
                case 2:
                    continue;
                case 3:
                    this.userBase.clear();
                    continue;
                default:
                    return false;
            }
        }
    }

    private boolean isUserWantToCashOut() {
        System.out.println("-----------------------------------------------------");
        System.out.println("ОПЕРАЦИЯ: Выдача денег");
        System.out.println("-----------------------------------------------------");
        while (true) {
            System.out.print("Введите требуемую сумму: ");
            this.requiredSum = scanner.nextInt();
            try {
                this.cashMachineBase.testRequiredSum(requiredSum);
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
                continue;
            }
            break;
        }
        System.out.println("Купюрами какого достоинства произвести выдачу:");
        System.out.println(cashMachineBase);
        while (true) {
            boolean isNeedToRepeat = false;
            System.out.print("Введите номер купюры: ");
            int number = this.scanner.nextInt();
            this.requiredNote = this.cashMachineBase.getValueName(number);
            try {
                this.cashMachineBase.testRequiredNote(requiredSum, number);
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
                return false;
            }
            try {
                this.cashMachineBase.testOnOpportunity(requiredSum, number);
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
                return false;
            } catch (RuntimeException exception2) {
                System.out.println(exception2.getMessage());
                System.out.println("но остаток (" + (this.requiredSum -
                        this.cashMachineBase.getCount(number) * this.cashMachineBase.getValueName(number).getValue())
                        + ") можно выдать купюрами более низкого достоинства");
                System.out.println("1) выдавать остаток купюрами более низкого достоинства");
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
                    }
                    if (isNeedToRepeat) {
                        break;
                    }
                }
            }
            if (isNeedToRepeat) {
                continue;
            }
            return true;
        }
    }

    private void status() {
        System.out.println("-----------------------------------------------------");
        System.out.println("ОПЕРАЦИЯ: Статус банкомата");
        System.out.println("-----------------------------------------------------");
        machine.status();
        System.out.println("-----< Статус системы >----------------------------");
        System.out.print(this.cashMachineBase.toStringWithCount());
        System.out.println("ИТОГО в банкомате: " + this.cashMachineBase.getSum());
    }
}