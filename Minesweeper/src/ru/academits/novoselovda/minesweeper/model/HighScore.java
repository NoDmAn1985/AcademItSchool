package ru.academits.novoselovda.minesweeper.model;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class HighScore {
    private String path = ".\\Minesweeper\\src\\ru\\academits\\novoselovda\\minesweeper\\resources\\highscore.txt";
    private String userName;
    private final int maxLettersInUserName = 10;
    private final int maxRecords = 20;

    public HighScore() {
        this.userName = System.getProperty("user.name");
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        if (userName.length() > this.maxLettersInUserName) {
            this.userName = userName.substring(0, this.maxLettersInUserName);
        } else {
            this.userName = userName;
        }
    }

    public void save(int yCellCounts, int xCellCounts, int minesCount, int time) {
        int score = (int) (10000000.0 * minesCount / (yCellCounts * xCellCounts * (time + 100)));
        ArrayList<String> topInformation = new ArrayList<>();
        ArrayList<Integer> topScore = new ArrayList<>();
        int index = 0;

        try (Scanner reader = new Scanner(new FileInputStream(this.path))) {
            while (reader.hasNext()) {
                String text = reader.nextLine();
                try {
                    topScore.add(Integer.parseInt(text.substring(0, text.indexOf('_'))));
                } catch (NumberFormatException exception) {
                    System.out.println("ОШИБКА: файл рекордов повреждён - см. " + this.path);
                    exception.printStackTrace();
                    return;
                }
                topInformation.add(text);
                index = getIndex(0, topScore.size() - 1, topScore, score);
            }
        } catch (FileNotFoundException exception) {
        }

        if (topScore.size() == this.maxRecords && score < topScore.get(this.maxRecords - 1)) {
            return;
        }

        String date = new SimpleDateFormat("yyyy.MM.dd HH:mm").format(new Date());

        String newLine = String.format("%d_%-10s (%2d х %2d клеток, %3d мин, за %3d сек.) [%s] - %d очков",
                score, this.userName, yCellCounts, xCellCounts, minesCount, time, date, score);
        topInformation.add(index, newLine);

        try (PrintWriter writer = new PrintWriter(new FileWriter(this.path))) {
            int i = 1;
            for (String element : topInformation) {
                writer.println(element);
                if (i == 20) {
                    break;
                }
                ++i;
            }
        } catch (IOException exception) {
            System.out.println("ОШИБКА: файл (" + this.path + ") не доступен для записи");
            exception.printStackTrace();
        }
    }

    public String show() {
        StringBuilder sb = new StringBuilder();
        try (Scanner reader = new Scanner(new FileInputStream(this.path))) {
            int index = 1;
            while (reader.hasNext()) {
                String text = reader.nextLine();
                sb.append(String.format("%2d) %s%n", index, text.substring(text.indexOf('_') + 1, text.length())));
                ++index;
            }
        } catch (FileNotFoundException exception) {
            System.out.println("ОШИБКА: потерян файл (" + this.path + ")");
            exception.printStackTrace();
        }
        return sb.toString();
    }


    private int getIndex(int left, int right, ArrayList<Integer> array, int score) {
        if (score > array.get(left)) {
            return left;
        }
        if (left == right || score == array.get(left)) {
            return left + 1;
        } else if (score <= array.get(right)) {
            return right + 1;
        }
        int index = (left + right) / 2;
        int average = array.get(index);
        if (score > average) {
            return getIndex(left, index - 1, array, score);
        } else if (score < average) {
            return getIndex(index + 1, right, array, score);
        } else {
            return index + 1;
        }
    }
}
