package ru.acdemits.novoselovda.main;

import ru.academits.novoselovda.cft.Cft;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            Cft cft = new Cft(args);
            cft.sort();
        } catch (IOException | IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            System.out.println("Для сортировки данных в исходном файле необходимо"
                    + " ввести следующие параметры через пробел");
            System.out.println("например: in.txt out.txt -i -a");
            System.out.println("\t1) in.txt - имя и расширение исходного файла,");
            System.out.println("\t2) out.txt - имя и расширение итогового файла,");
            System.out.println("\t3) -i - аргумент типа данных (\"-i\" - для целых чисел, \"-s\" - для строк),");
            System.out.println("\t4) -a - аргумент типа сортировки (\"-a\" - для сортировки повозрастанию, "
                    + "\"-d\" - поубыванию)");
        }

    }
}
