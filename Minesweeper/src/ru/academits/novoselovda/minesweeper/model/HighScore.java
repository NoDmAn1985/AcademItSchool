package ru.academits.novoselovda.minesweeper.model;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class HighScore {
    private static final String PATH = ".\\Minesweeper\\src\\ru\\academits\\novoselovda\\minesweeper\\resources\\highscore.txt";
    private static final int MAX_LETTERS_IN_USER_NAME = 10;
    private static final int MAX_RECORDS = 20;

    private String userName;

    public HighScore() {
        this.userName = System.getProperty("user.name");
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        if (userName.length() > MAX_LETTERS_IN_USER_NAME) {
            this.userName = userName.substring(0, MAX_LETTERS_IN_USER_NAME);
        } else {
            this.userName = userName;
        }
    }

    public void save(int yCellCounts, int xCellCounts, int minesCount, int time) {
        int score = (int) (10000000.0 * minesCount / (yCellCounts * xCellCounts * (time + 100)));
        ArrayList<String> topInformation = new ArrayList<>();
        ArrayList<Integer> topScore = new ArrayList<>();
        int index = 0;

        try (Scanner reader = new Scanner(new FileInputStream(PATH))) {
            while (reader.hasNext()) {
                String text = reader.nextLine();
                try {
                    topScore.add(Integer.parseInt(text.substring(0, text.indexOf('_'))));
                } catch (NumberFormatException exception) {
                    System.out.println("ОШИБКА: файл рекордов повреждён - см. " + PATH);
                    exception.printStackTrace();
                    return;
                }
                topInformation.add(text);
                index = getIndex(0, topScore.size() - 1, topScore, score);
            }
        } catch (FileNotFoundException exception) {
        }

        if (topScore.size() == MAX_RECORDS && score < topScore.get(MAX_RECORDS - 1)) {
            return;
        }

        String date = new SimpleDateFormat("yyyy.MM.dd HH:mm").format(new Date());

        String newLine = String.format("%d_%-10s (%2d х %2d клеток, %3d мин, за %3d сек.) [%s] - %d очков",
                score, this.userName, yCellCounts, xCellCounts, minesCount, time, date, score);
        topInformation.add(index, newLine);

        try (PrintWriter writer = new PrintWriter(new FileWriter(PATH))) {
            int i = 1;
            for (String element : topInformation) {
                writer.println(element);
                if (i == 20) {
                    break;
                }
                ++i;
            }
        } catch (IOException exception) {
            System.out.println("ОШИБКА: файл (" + PATH + ") не доступен для записи");
            exception.printStackTrace();
        }
    }

    public String show() {
        StringBuilder sb = new StringBuilder();
        try (Scanner reader = new Scanner(new FileInputStream(PATH))) {
            int index = 1;
            while (reader.hasNext()) {
                String text = reader.nextLine();
                sb.append(String.format("%2d) %s%n", index, text.substring(text.indexOf('_') + 1, text.length())));
                ++index;
            }
        } catch (FileNotFoundException exception) {
            System.out.println("ОШИБКА: потерян файл (" + PATH + ")");
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
