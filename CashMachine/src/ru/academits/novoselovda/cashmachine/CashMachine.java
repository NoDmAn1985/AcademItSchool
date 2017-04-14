package ru.academits.novoselovda.cashmachine;

import ru.academits.novoselovda.notes.Notes;

import java.util.Arrays;
import java.util.Scanner;

public class CashMachine {
    private Scanner scanner;
    private int[] notesCount;
    private int maxCount;
    private Machine machine;
    private int requiredSum;
    private Notes.Values requiredNote;
    private boolean isWork;

    public CashMachine(Notes[] moneyStart) {
        isWork = true;
        this.maxCount = Notes.Values.values().length;
        this.notesCount = new int[this.maxCount];
        scanner = new Scanner(System.in);
        try {
            machine = new Machine(moneyStart);
        } catch (IllegalArgumentException exception) {
            isWork = false;
            System.out.println(exception.getMessage());
        }
    }

    public void mainMenu() {
        while (isWork) {
            System.out.println("Выберите операцию: ");
            System.out.println("1) Приём денег");
            System.out.println("2) Выдача денег");
            System.out.println("3) Статус банкомата");
            System.out.println("4) Выключить");
            int operationNumber = scanner.nextInt();
            switch (operationNumber) {
                case 1:
                    if (isUserWantToCashIn()) {
                        System.out.println(this.maxCount);
                        Notes[] userNotes = new Notes[this.maxCount];
                        int index = 0;
                        for (Notes.Values note : Notes.Values.values()) {
                            userNotes[index] = new Notes(note, notesCount[index]);
                            ++index;
                        }
                        try {
                            machine.cashIn(userNotes);
                            machine.status();
                        } catch (IllegalArgumentException exception) {
                            System.out.println(exception.getMessage());
                        }
                    }
                    System.out.println("Операция прошла успешно - деньги зачислены");
                    break;
                case 2:
                    if (isUserWantToCashOut()) {
                        try {
                            machine.cashOut(requiredSum, requiredNote);
                            machine.status();
                        } catch (IllegalArgumentException exception) {
                            System.out.println(exception.getMessage());
                        }
                    }
                    System.out.println("Операция прошла успешно - возьмите деньги");
                    break;
                case 3:
                    machine.status();
                    break;
                default:
                    return;
            }
            System.out.println("-----------------------------------------------------");
        }
    }

    private boolean isUserWantToCashIn() {
        System.out.println("-----------------------------------------------------");
        System.out.println("ОПЕРАЦИЯ: Приём денег");
        while (true) {
            System.out.println("-----------------------------------------------------");
            System.out.println("Какие купюры и в каком количестве вы хотите положить:");
            int index = 0;
            for (Notes.Values note : Notes.Values.values()) {
                System.out.println(index + ") " + note.getValue() + "\t\t - " + this.notesCount[index] + " шт.");
                ++index;
            }
            System.out.print("Введите номер купюры: ");
            int number = scanner.nextInt();
            if (number < 0 || number > index - 1) {
                System.out.println("ОШИБКА: нет такого номера");
                continue;
            }
            System.out.print("Введите количество данных купюр: ");
            int tempNotesCount = scanner.nextInt();
            if (tempNotesCount < 0) {
                System.out.println("ОШИБКА: количество должно быть больше нуля");
                continue;
            }
            notesCount[number] = tempNotesCount;
            System.out.println("Введите:");
            System.out.println("1) Для выхода в предыдущее меню");
            System.out.println("2) Для выполнения данной операции");
            System.out.println("3) Для сброса значений");
            System.out.println("4) Для изменения данных");
            int operation = scanner.nextInt();
            switch (operation) {
                case 1:
                    return false;
                case 2:
                    return true;
                case 3:
                    Arrays.fill(notesCount, 0);
                    continue;
                default:
            }
        }
    }

    private boolean isUserWantToCashOut() {
        System.out.println("-----------------------------------------------------");
        System.out.println("ОПЕРАЦИЯ: Выдача денег");
        while (true) {
            System.out.println("-----------------------------------------------------");
            System.out.print("Введите требуемую сумму: ");
            this.requiredSum = scanner.nextInt();
            if (requiredSum < 0) {
                System.out.println("ОШИБКА: запрашиваемая сумма должна быть положительной");
                continue;
            }
            if (requiredSum % machine.MIN_NOTES_VALUE > 0) {
                System.out.println("ОШИБКА: запрашиваемая сумма должна быть кратна " + machine.MIN_NOTES_VALUE);
                continue;
            }
            System.out.println("Купюрами какого достоинства произвести выдачу:");
            int index = 0;
            for (Notes notes : machine.getMachineNotes()) {
                System.out.println(index + ") " + notes.getValueInt());
                ++index;
            }
            System.out.print("Введите номер купюры: ");
            int number = scanner.nextInt();
            if (number < 0 || number > index - 2) {
                System.out.println("ОШИБКА: нет такого номера");
                continue;
            }
            int i = 0;
            for (Notes notes : machine.getMachineNotes()) {
                if (i == number) {
                    requiredNote = notes.getValueName();
                }
                ++i;
            }
            if (requiredSum < requiredNote.getValue()) {
                System.out.println("ОШИБКА: запрашиваемая сумма меньше запрашивемой купюры");
                continue;
            }
            System.out.println("Введите:");
            System.out.println("1) Для выхода в предыдущее меню");
            System.out.println("2) Для выполнения данной операции");
            System.out.println("3) Для изменения данных введите");
            int operation = scanner.nextInt();
            switch (operation) {
                case 1:
                    return false;
                case 2:
                    return true;
                default:
                    break;
            }
        }
    }
}
