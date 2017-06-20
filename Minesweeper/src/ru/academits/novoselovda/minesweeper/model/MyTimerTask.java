package ru.academits.novoselovda.minesweeper.model;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.TimerTask;

public class MyTimerTask extends TimerTask {
    private static final int TIME_LIMIT = 999;
    private static final int TIME_WITH_RED_DIGITS = 900;
    private Timer timer;
    private JTextField textField;
    private boolean isColorChanged = false;
    private boolean isTimerOnPause = false;

    public MyTimerTask(JTextField textField) {
        this.timer = new Timer();
        this.textField = textField;
        this.textField.setForeground(Color.WHITE);
    }

    @Override
    public void run() {
        if (this.isTimerOnPause) {
            return;
        }
        int seconds = this.timer.getTime();
        textField.setText(new DecimalFormat("000").format(seconds));
        if (seconds >= TIME_LIMIT) {
            pause();
        } else if (!this.isColorChanged && seconds > TIME_WITH_RED_DIGITS) {
            this.textField.setForeground(Color.RED);
            this.isColorChanged = true;
        }
    }

    public void pause() {
        this.isTimerOnPause = true;
    }
}
