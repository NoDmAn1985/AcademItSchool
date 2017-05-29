package ru.academits.novoselovda.minesweeper.model;

import ru.academits.novoselovda.minesweeper.common.FieldPanelListener;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.TimerTask;

public class MyTimerTask extends TimerTask {
    private final int timeLimit = 999;
    private final int timeWithRedDigits = 900;
    private FieldPanelListener fieldListener;
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
        if (seconds >= this.timeLimit) {
            MyTimerTask.this.fieldListener.needGameOver();
        } else if (!this.isColorChanged && seconds > this.timeWithRedDigits) {
            this.textField.setForeground(Color.RED);
            this.isColorChanged = true;
        }
    }

    public void pause() {
        this.isTimerOnPause = true;
    }

    public void addListener(FieldPanelListener fieldListener) {
        this.fieldListener = fieldListener;
    }

}
