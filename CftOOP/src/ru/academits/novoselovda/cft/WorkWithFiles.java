package ru.academits.novoselovda.cft;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

class WorkWithFiles {
    private String inputFilePath;
    private String outputFilePath;

    WorkWithFiles(String inputFilePath, String outputFilePath) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
    }

    ArrayList<Integer> readIntegerFromInputFile() throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new FileInputStream(inputFilePath))) {
            ArrayList<Integer> arrayFromFile = new ArrayList<>();
            while (scanner.hasNextInt()) {
                arrayFromFile.add(scanner.nextInt());
            }
            return arrayFromFile;
        } catch (FileNotFoundException exception) {
            String message = String.format("ОШИБКА: исходный файл (%s) не найден!", inputFilePath);
            throw new FileNotFoundException(message);
        }
    }

    ArrayList<String> readStringsFromInputFile() throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new FileInputStream(inputFilePath))) {
            ArrayList<String> arrayFromFile = new ArrayList<>();
            while (scanner.hasNextLine()) {
                arrayFromFile.add(scanner.nextLine());
            }
            return arrayFromFile;
        } catch (FileNotFoundException exception) {
            String message = String.format("ОШИБКА: исходный файл (%s) не найден!", inputFilePath);
            throw new FileNotFoundException(message);
        }
    }

    void writeToOutputFile(ArrayList<?> arrayList) throws IOException {
        try (PrintWriter writer = new PrintWriter(outputFilePath)) {
            for (Object element : arrayList) {
                writer.println(element);
            }
        } catch (IOException exception) {
            String message = String.format("ОШИБКА: конечный файл (%s) нельзя записать", outputFilePath);
            throw new IOException(message);
        }
    }
}