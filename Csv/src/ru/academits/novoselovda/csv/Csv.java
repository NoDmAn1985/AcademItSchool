package ru.academits.novoselovda.csv;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Csv {
    public void fromCsvToHtml(String inputFilePath, String outputFilePath) {
        writeToHtml(outputFilePath, stringsToHtml(readFromCsvFile(inputFilePath)));
    }

    private ArrayList<String> readFromCsvFile(String inputFilePath) {
        ArrayList<String> stringArrayList = new ArrayList<>();
        try (Scanner scanner = new Scanner(new FileInputStream(inputFilePath))) {
            while (scanner.hasNextLine()) {
                stringArrayList.add(scanner.nextLine());
            }
        } catch (FileNotFoundException exception) {
            System.out.println("ОШИБКА: исходный файл не найден");
            help();
        }
        return stringArrayList;
    }

    private void writeToHtml(String outputFilePath, ArrayList<String> tableRows) {
        try (PrintWriter writer = new PrintWriter(outputFilePath)) {
            writer.println("<html>");
            writer.println("\t<head>");
            writer.println("\t\t<title>HTML table from your's csv-file</title>");
            writer.println("\t\t<meta charset=\"utf-8\">");
            writer.println("\t\t<meta name=\"GENERATOR\" content=\"IntelliJ IDEA 2017.1\">");
            writer.println("\t</head>");
            writer.println("\t<body>");
            writer.println("\t\t<table cellpadding=\"5\" border=\"1\">");
            writer.println("\t\t\t<tbody align=\"center\">");
            for (String row : tableRows) {
                writer.printf("\t\t\t\t%s%n", row);
            }
            writer.println("\t\t\t</tbody>");
            writer.println("\t\t</table>");
            writer.println("\t</body>");
            writer.println("</html>");
        }catch (IOException ioException) {
            System.out.println("ОШИБКА: нет возможности записать файл");
            help();
        }
    }

    private ArrayList<String> stringsToHtml(ArrayList<String> stringsFromCsv) {
        ArrayList<String> tableRows = new ArrayList<>();
        StringBuilder row = new StringBuilder();
        boolean isCellSurroundedByQuotes = false;
        for (String stringFromCsv : stringsFromCsv) {
            if (!isCellSurroundedByQuotes) {
                row.append("<tr><td>");
            }
            boolean isQuote = false;
            int begin = 0;
            for (int j = 0; j < stringFromCsv.length(); j++) {
                isCellSurroundedByQuotes = (stringFromCsv.charAt(begin) == '"');
                char character = stringFromCsv.charAt(j);
                switch (character) {
                    case ',':
                        if (!isCellSurroundedByQuotes || isQuote) {
                            row.append("</td><td>");
                            isQuote = false;
                            isCellSurroundedByQuotes = false;
                            begin = (j + 1 < stringFromCsv.length() - 1) ? j + 1 : 0;
                        } else {
                            row.append(character);
                        }
                        break;
                    case '"':
                        if (j != begin) {
                            if (isQuote) {
                                row.append(character);
                            }
                            isQuote = !isQuote;
                        }
                        break;
                    case '>':
                        row.append("&gt;");
                        break;
                    case '<':
                        row.append("&lt;");
                        break;
                    case '&':
                        row.append("&amp;");
                        break;
                    default:
                        row.append(character);
                }
            }
            if (!isCellSurroundedByQuotes || isQuote) {
                row.append("</td></tr>");
                tableRows.add(row.toString());
                row.delete(0, row.length());
            } else {
                row.append("<br/>");
            }
        }
        return tableRows;
    }

    public static void help() {
        System.out.println("Подсказка: для правильной работы программы необходимо ввести аргументы через пробел, где:");
        System.out.println("- первый аргумент - адрес и имя исходного csv-файла, например: in.csv");
        System.out.println("- второй аргумент - адрес и имя итогового html-файла, например: out.html");
    }
}
