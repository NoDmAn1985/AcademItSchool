package ru.academits.novoselovda.cashmachine;

import ru.academits.novoselovda.notes.Money;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class CashMachine {
    private Scanner scanner;
    private int requiredSum;
    private int requiredNoteNumber;
    private boolean isWork;
    private Money[] userMoney;
    private NotesBase cashMachineBase;
    private Storage storage;
    private final int NOTES_MAX_COUNT = 200;

    public CashMachine(Money[] startMoney) {
        isWork = true;
        scanner = new Scanner(System.in);
        for (Money money : startMoney) {
            if (money != null && money.getCount() > NOTES_MAX_COUNT) {
                System.out.println("ОШИБКА: максимальное количество хранимых купюр - " + NOTES_MAX_COUNT);
                isWork = false;
            }
        }
        startMoney = sort(startMoney);
        cashMachineBase = new NotesBase(startMoney, NOTES_MAX_COUNT);
        storage = new Storage(NOTES_MAX_COUNT);
        storage.initStartDeposit(startMoney);
    }

    public ArrayList<Money> mainMenu(Money[] userMoney) {
        this.userMoney = sort(userMoney);
        while (isWork) {
            System.out.println("Выберите операцию: ");
            System.out.println("1) Приём денег");
            System.out.println("2) Выдача денег");
            System.out.println("3) Статус банкомата");
            System.out.println("4) Выключить банкомат");
            int operationNumber = scanner.nextInt();
            switch (operationNumber) {
                case 1:
                    cashIn();
                    break;
                case 2:
                    return cashOut();
                case 3:
                    status();
                    break;
                case 4:
                    return null;
                default:
                    break;
            }
            System.out.println("-----------------------------------------------------");
        }
        return null;

    }

    private void cashIn() {
        System.out.println("-----------------------------------------------------");
        System.out.println("ОПЕРАЦИЯ: Приём денег");
        System.out.println("-----------------------------------------------------");
        System.out.println("До приёма денег было:");
        cashMachineBase.status(storage);
        System.out.print("Вложите деньги в купюроприёмник и нажмите <ENTER>....");
        new Scanner(System.in).nextLine();
        System.out.println("-----------------------------------------------------");
        System.out.println("Вы вложили:");
        for (Money money : this.userMoney) {
            System.out.println(money);
        }
        System.out.println("-----------------------------------------------------");
        try {
            this.cashMachineBase.add(userMoney);
            storage.add(userMoney);
            System.out.println("После приёма денег:");
            cashMachineBase.status(storage);
            System.out.println("Операция прошла успешно - деньги зачислены");
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            System.out.println("Операция была прервана");
        }
    }

    private ArrayList<Money> cashOut() {
        System.out.println("-----------------------------------------------------");
        System.out.println("ОПЕРАЦИЯ: Выдача денег");
        System.out.println("-----------------------------------------------------");
        if (isUserWantsToCashOut()) {
            ArrayList<Money> cashForUser;
            try {
                cashForUser = storage.subtract(this.cashMachineBase.subtract(requiredSum, requiredNoteNumber));
                status();
                System.out.println("Операция прошла успешно - возьмите деньги");
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
                this.cashMachineBase.testRequiredSum(this.requiredSum);
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
                continue;
            }
            break;
        }
        System.out.println("Купюрами какого достоинства произвести выдачу:");
        System.out.print(this.cashMachineBase);
        while (true) {
            boolean isNeedToRepeat = true;
            System.out.print("Введите номер купюры: ");
            this.requiredNoteNumber = this.scanner.nextInt();
            try {
                this.cashMachineBase.testRequiredNote(this.requiredSum, this.requiredNoteNumber);
                isNeedToRepeat = false;
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
                String exceptionString = exception.getMessage();
                if (exceptionString.contains(", но остаток")) {
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
            }
            if (!isNeedToRepeat) {
                return true;
            }
        }
    }

    private Money[] sort(Money[] money) {
        int nullCount = 0;
        if (money[0] == null || money[0].getCount() == 0) {
            ++nullCount;
        }
        int i = 1;
        while (money[i] == null) {
            if (money[i] == null || money[i].getCount() == 0) {
                ++nullCount;
            }
            ++i;
        }
        for (; i < money.length; ++i) {
            if (money[i] == null || money[i].getCount() == 0) {
                ++nullCount;
                continue;
            }
            if (money[i - 1] == null || money[i - 1].getCount() == 0 ||
                    money[i].getValue().getCost() < money[i - 1].getValue().getCost()) {
                Money temp = money[i];
                int j = i - 1;
                while (j >= 0) {
                    if (money[j] == null || money[j].getCount() == 0 ||
                            temp.getValue().getCost() < money[j].getValue().getCost()) {
                        money[j + 1] = money[j];
                    } else {
                        break;
                    }
                    --j;
                }
                money[j + 1] = temp;
            }
        }
        if (nullCount > 0) {
            money = Arrays.copyOf(money, money.length - nullCount);
        }
        return money;
    }

    private void status() {
        System.out.println("-----------------------------------------------------");
        System.out.println("ОПЕРАЦИЯ: Статус банкомата");
        System.out.println("-----------------------------------------------------");
        cashMachineBase.status(storage);
    }
}