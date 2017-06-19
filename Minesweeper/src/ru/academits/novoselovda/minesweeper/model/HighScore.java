package ru.academits.novoselovda.minesweeper.model;

import ru.academits.novoselovda.minesweeper.common.ErrorShowMessageListener;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class HighScore {
    private static final String PATH = ".\\Minesweeper\\src\\ru\\academits\\novoselovda\\minesweeper\\resources\\highscore.txt";
    private static final String TITLE = "Рекорды";
    private static final int MAX_LETTERS_IN_USER_NAME = 10;
    private static final int MAX_RECORDS = 20;

    private String userName;

    private ErrorShowMessageListener listener;

    public HighScore() {
        this.userName = System.getProperty("user.name");
    }

    public String getUserName() {
        return this.userName;
    }

    public String getTitle() {
        return TITLE;
    }

    private void setUserName(String userName) {
        if (userName == null) {
            return;
        }
        if (userName.length() > MAX_LETTERS_IN_USER_NAME) {
            this.userName = userName.substring(0, MAX_LETTERS_IN_USER_NAME);
        } else {
            this.userName = userName;
        }
    }

    public void save(String userName, int yCellCounts, int xCellCounts, int minesCount, int time) {
        setUserName(userName);
        int score = (int) (10000000.0 * minesCount / (yCellCounts * xCellCounts * (time + 100)));
        ArrayList<String> topInformation = new ArrayList<>();
        ArrayList<Integer> topScore = new ArrayList<>();
        int index;

        try (Scanner reader = new Scanner(new FileInputStream(PATH))) {
            while (reader.hasNext()) {
                String text = reader.nextLine();
                boolean isException = false;
                try {
                    topScore.add(Integer.parseInt(text.substring(0, text.indexOf('_'))));
                } catch (NumberFormatException | StringIndexOutOfBoundsException exception) {
                    topScore.clear();
                    topInformation.clear();
                    isException = true;
                }
                if (!isException) {
                    topInformation.add(text);
                }
            }
        } catch (FileNotFoundException exception) {
            //нет файла, и ладно, создадим новый;
        }

        if (topScore.size() == 0) {
            index = 0;
        } else if (topScore.size() == MAX_RECORDS && score < topScore.get(MAX_RECORDS - 1)) {
            index = MAX_RECORDS - 1;
        } else {
            index = getIndex(topScore, score);
        }

        String date = new SimpleDateFormat("yyyy.MM.dd HH:mm").format(new Date());

        String newLine = String.format("%d_%-10s (%2d х %2d клеток, %3d мин, за %3d сек.) [%s] - %d очков",
                score, this.userName, yCellCounts, xCellCounts, minesCount, time, date, score);
        topInformation.add(index, newLine);

        try (PrintWriter writer = new PrintWriter(new FileWriter(PATH))) {
            int i = 1;
            for (String element : topInformation) {
                writer.println(element);
                if (i == MAX_RECORDS) {
                    break;
                }
                ++i;
            }
        } catch (IOException exception) {
            this.listener.needShowErrorMessage("ОШИБКА: файл (" + PATH + ") недоступен для записи");
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
            sb.append("ОШИБКА: потерян файл (" + PATH + ")");
        }
        return sb.toString();
    }


    private int getIndex(ArrayList<Integer> array, int score) {
        for (int i = 0; i < array.size(); i++) {
            if (score > array.get(i)) {
                return i;
            }
        }
        return array.size();
    }

    public void setListener(ErrorShowMessageListener listener) {
        this.listener = listener;
    }
}
