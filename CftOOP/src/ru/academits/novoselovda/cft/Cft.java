package ru.academits.novoselovda.cft;

import java.io.IOException;
import java.util.ArrayList;

public class Cft {
    private String inputFilePath;
    private String outputFilePath;
    private boolean isIntegerInInputFile;
    private boolean isSortingUp;

    public Cft(String[] args) {
        if (args.length == 4) {
            if (!args[2].equals("-i") && !args[2].equals("-s")) {
                String message = String.format("ОШИБКА: аргумент типа данных (%s) указан неверно!", args[2]);
                throw new IllegalArgumentException(message);
            } else if (!args[3].equals("-a") && !args[3].equals("-d")) {
                String message = String.format("ОШИБКА: аргумент способа сортировки (%s) указан неверно!", args[3]);
                throw new IllegalArgumentException(message);
            } else {
                inputFilePath = args[0];
                outputFilePath = args[1];
                isIntegerInInputFile = args[2].equals("-i");
                isSortingUp = args[3].equals("-a");
            }
        } else if (args.length > 4) {
            throw new IllegalArgumentException("ОШИБКА: слишком много аргументов!");
        } else {
            throw new IllegalArgumentException("ОШИБКА: слишком мало аргументов!");
        }
    }

    public void sort() throws IOException {
        WorkWithFiles workWithFiles = new WorkWithFiles(inputFilePath, outputFilePath);
        if (isIntegerInInputFile) {
            ArrayList<Integer> array = workWithFiles.readIntegerFromInputFile();
            Sort.byInsertion(array, isSortingUp);
            workWithFiles.writeToOutputFile(array);
        } else {
            ArrayList<String> array = workWithFiles.readStringsFromInputFile();
            Sort.byInsertion(array, isSortingUp);
            workWithFiles.writeToOutputFile(array);
        }
        System.out.println("Выполнено! Результат сохранён в " + outputFilePath);
    }
}
