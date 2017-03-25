package ru.academits.novoselovda.main;

import ru.academits.novoselovda.csv.Csv;

public class Main {
    public static void main(String[] args) {
        if (args.length == 2) {
            Csv csv = new Csv();
            csv.fromCsvToHtml(args[0], args[1]);
            System.out.println("Выполнено! Результат сохранён в " + args[1]);
        } else {
            System.out.println("ОШИБКА: неверное количество аргументов");
            Csv.help();
        }
    }
}
