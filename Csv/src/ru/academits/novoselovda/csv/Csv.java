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
        }
        return stringArrayList;
    }

    private void writeToHtml(String outputFilePath, ArrayList<String> tableRows) {
        try (PrintWriter writer = new PrintWriter(outputFilePath)) {
            writer.println("<html>");
            writer.println("<body>");
            writer.println("<table cellpadding=\"5\" border=\"1\">");
            writer.println("<tbody align=\"center\">");
            for (String row : tableRows) {
                writer.println(row);
            }
            writer.println("</tbody>");
            writer.println("</table>");
            writer.println("</body>");
            writer.println("</html>");
        } catch (IOException exception) {
            System.out.println("ОШИБКА: нет возможности записать файл");
        }
    }

    private ArrayList<String> stringsToHtml(ArrayList<String> stringsFromCsv) {
        ArrayList<String> tableRows = new ArrayList<>();
        StringBuilder row = new StringBuilder();
        boolean isCellSurroundedByQuotes = false;
        for (int i = 0; i < stringsFromCsv.size(); i++) {
            if (!isCellSurroundedByQuotes) {
                row.append("<tr><td>");
            }
            boolean isQuote = false;
            int begin = 0;
            for (int j = 0; j < stringsFromCsv.get(i).length(); j++) {
                isCellSurroundedByQuotes = (stringsFromCsv.get(i).charAt(begin) == '"');
                char character = stringsFromCsv.get(i).charAt(j);
                switch (character) {
                    case ',':
                        if (!isCellSurroundedByQuotes || isQuote) {
                            row.append("</td><td>");
                            isQuote = false;
                            isCellSurroundedByQuotes = false;
                            begin = (j + 1 < stringsFromCsv.get(i).length() - 1) ? j + 1 : 0;
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
}
