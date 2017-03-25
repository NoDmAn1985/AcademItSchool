package ru.academits.novoselovda.cft;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Cft {
    private String inputFilePath;
    private String outputFilePath;
    private boolean isIntegerInInputFile;
    private boolean isSortingUp;
    final private String HELP =
            String.format("Для сортировки данных в исходном файле необходимо ввести следующие параметры через пробел,%n" +
                    "например: in.txt out.txt -i -a%n" +
                    "\t1) in.txt - имя и расширение исходного файла,%n" +
                    "\t2) out.txt - имя и расширение итогового файла,%n" +
                    "\t3) -i - аргумент типа данных (\"-i\" - для целых чисел, \"-s\" - для строк)%n" +
                    "\t4) -a - аргумент типа сортировки (\"-a\" - для сортировки повозрастанию, \"-d\" - поубыванию)");

    public Cft(String[] args) {
        if (args.length == 4) {
            if (!args[2].equals("-i") && !args[2].equals("-s")) {
                String message = String.format("ОШИБКА: аргумент типа данных (%s) указан неверно!%n%s", args[2], HELP);
                throw new IllegalArgumentException(message);
            } else if (!args[3].equals("-a") && !args[3].equals("-d")) {
                String message = String.format("ОШИБКА: аргумент способа сортировки (%s) указан неверно!%n%s", args[3], HELP);
                throw new IllegalArgumentException(message);
            } else {
                inputFilePath = args[0];
                outputFilePath = args[1];
                isIntegerInInputFile = args[2].equals("-i");
                isSortingUp = args[3].equals("-a");
            }
        } else if (args.length > 4) {
            System.out.println("ОШИБКА: слишком много аргументов!");
            System.out.println(HELP);
        } else {
            String message = String.format("ОШИБКА: аргументов нет!%n%s", HELP);
            throw new IllegalArgumentException(message);
        }
    }

    public void sort() throws IOException {
        if (isIntegerInInputFile) {
            ArrayList<Integer> array = readIntegerFromInputFile();
            if (isSortingUp) {
                insertionSortStrings(array, new IntegerComparatorUp());
            } else {
                insertionSortStrings(array, new IntegerComparatorDown());
            }
            writeToOutputFile(array);
        } else {
            ArrayList<String> array = readStringsFromInputFile();
            if (isSortingUp) {
                insertionSortStrings(array, new StringComparatorUp());
            } else {
                insertionSortStrings(array, new StringComparatorDown());
            }
            writeToOutputFile(array);
        }
        System.out.println("Выполнено! Результат сохранён в " + outputFilePath);
    }

    private ArrayList<Integer> readIntegerFromInputFile() throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new FileInputStream(inputFilePath))) {
            ArrayList<Integer> arrayFromFile = new ArrayList<>();
            while (scanner.hasNextInt()) {
                arrayFromFile.add(scanner.nextInt());
            }
            return arrayFromFile;
        } catch (FileNotFoundException exception) {
            String message = String.format("ОШИБКА: исходный файл (%s) не найден!%n%s", inputFilePath, HELP);
            throw new FileNotFoundException(message);
        }
    }

    private ArrayList<String> readStringsFromInputFile() throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new FileInputStream(inputFilePath))) {
            ArrayList<String> arrayFromFile = new ArrayList<>();
            while (scanner.hasNextLine()) {
                arrayFromFile.add(scanner.nextLine());
            }
            return arrayFromFile;
        } catch (FileNotFoundException exception) {
            String message = String.format("ОШИБКА: исходный файл (%s) не найден!%n%s", inputFilePath, HELP);
            throw new FileNotFoundException(message);
        }
    }

    private void writeToOutputFile(ArrayList<?> arrayList) throws IOException {
        try (PrintWriter writer = new PrintWriter(outputFilePath)) {
            for (Object element : arrayList) {
                writer.println(element);
            }
        } catch (IOException exception) {
            String message = String.format("ОШИБКА: конечный файл (%s) нельзя записать%n", outputFilePath);
            throw new IOException(message);
        }
    }

    private static <T> void insertionSortStrings(ArrayList<T> array, Comparator<T> comparator) {
        for (int i = 1; i < array.size(); i++) {
            if (comparator.compare(array.get(i), array.get(i - 1)) < 0) {
                T temp = array.get(i);
                int j;
                for (j = i - 1; j >= 0; j--) {
                    if (comparator.compare(temp, array.get(j)) < 0) {
                        array.set(j + 1, array.get(j));
                    } else {
                        break;
                    }
                }
                array.set(j + 1, temp);
            }
        }
    }
}
