package ru.academits.novoselovda.cft;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Cft {
    private static String fileNameInput;
    private static String fileNameOutput;
    private static String typeOfInputData;
    private static String typeOfSort;

    public static void sort(String[] args) {
        if (args.length == 4) {
            if (!args[2].equals("-i") && !args[2].equals("-s")) {
                System.out.printf("ОШИБКА: аргумент типа данных (%s) указан неверно!%n", args[2]);
            } else if (!args[3].equals("-a") && !args[3].equals("-d")) {
                System.out.printf("ОШИБКА: аргумент способа сортировки (%s) указан неверно!%n", args[3]);
            } else {
                fileNameInput = args[0];
                fileNameOutput = args[1];
                typeOfInputData = args[2];
                typeOfSort = args[3];
                try {
                    writeToOutputFile(readFromInputFile());
                } catch (NullPointerException exception) {
                    System.out.println("нет данных для сортировки");
                }
                return;
            }
        } else if (args.length > 4) {
            System.out.println("ОШИБКА: слишком много аргументов!");
        } else {
            System.out.println("ОШИБКА: слишком мало аргументов!");
        }
        System.out.println("Для сортировки данных в исходном файле необходимо ввести следующие параметры через пробел, например:");
        System.out.println("in.txt out.txt -i -a");
        System.out.println("\t1) in.txt - имя и расширение исходного файла,");
        System.out.println("\t2) out.txt - имя и расширение итогового файла,");
        System.out.println("\t3) -i - аргумент типа данных (\"-i\" - для целых чисел, \"-s\" - для строк)");
        System.out.println("\t4) -a - аргумент типа сортировки (\"-a\" - для сортировки повозрастанию, \"-d\" - поубыванию)");
    }

    private static ArrayList readFromInputFile() {
        try (Scanner scanner = new Scanner(new FileInputStream(fileNameInput))) {
            switch (typeOfInputData) {
                case "-i":
                    ArrayList<Integer> arrayOfInts = new ArrayList<>();
                    while (scanner.hasNextInt()) {
                        arrayOfInts.add(scanner.nextInt());
                    }
                    insertionSortInts(arrayOfInts);
                    return arrayOfInts;
                case "-s":
                    ArrayList<String> arrayOfStrings = new ArrayList<>();
                    while (scanner.hasNextLine()) {
                        arrayOfStrings.add(scanner.nextLine());
                    }
                    insertionSortStrings(arrayOfStrings);
                    return arrayOfStrings;
            }
        } catch (FileNotFoundException exception) {
            System.out.printf("ОШИБКА: исходный файл (%s) не найден!%n", fileNameInput);
        }
        return null;
    }

    private static void writeToOutputFile(ArrayList arrayList) {
        try (PrintWriter writer = new PrintWriter(fileNameOutput)) {
            for (Object element : arrayList) {
                writer.println(element);

            }
        } catch (IOException exception) {
            System.out.printf("ОШИБКА: конечный файл (%s) нельзя записать%n", fileNameOutput);
        }
    }

    private static void insertionSortStrings(ArrayList<String> arrayOfStrings) {
        for (int i = 1; i < arrayOfStrings.size(); i++) {
            if ((typeOfSort.equals("-a") && arrayOfStrings.get(i).compareTo(arrayOfStrings.get(i - 1)) < 0)
                    || (typeOfSort.equals("-d") && arrayOfStrings.get(i).compareTo(arrayOfStrings.get(i - 1)) > 0)) {
                String temp = arrayOfStrings.get(i);
                int j;
                for (j = i - 1; j >= 0; j--) {
                    if ((typeOfSort.equals("-a") && temp.compareTo(arrayOfStrings.get(j)) < 0)
                            || (typeOfSort.equals("-d") && temp.compareTo(arrayOfStrings.get(j)) > 0)) {
                        arrayOfStrings.set(j + 1, arrayOfStrings.get(j));
                    } else {
                        break;
                    }
                }
                arrayOfStrings.set(j + 1, temp);
            }
        }
    }

    private static void insertionSortInts(ArrayList<Integer> arrayOfInts) {
        for (int i = 1; i < arrayOfInts.size(); i++) {
            if ((typeOfSort.equals("-a") && arrayOfInts.get(i) < arrayOfInts.get(i - 1))
                    || (typeOfSort.equals("-d") && arrayOfInts.get(i) > arrayOfInts.get(i - 1))) {
                int temp = arrayOfInts.get(i);
                int j;
                for (j = i - 1; j >= 0; j--) {
                    if ((typeOfSort.equals("-a") && temp < arrayOfInts.get(j))
                            || (typeOfSort.equals("-d") && temp > arrayOfInts.get(j))) {
                        arrayOfInts.set(j + 1, arrayOfInts.get(j));
                    } else {
                        break;
                    }
                }
                arrayOfInts.set(j + 1, temp);
            }
        }
    }
}
