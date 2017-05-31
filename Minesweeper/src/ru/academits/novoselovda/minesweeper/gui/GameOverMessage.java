package ru.academits.novoselovda.minesweeper.gui;

import com.sun.awt.AWTUtilities;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

class GameOverMessage extends JFrame {
    private static final String ICON_PATH = ".\\Minesweeper\\src\\ru\\academits\\novoselovda\\minesweeper\\resources\\cell_gameover.png";
    private static final String TEXT = "Ба-бах!";
    private static final Font MY_FONT = new Font("Verdana", Font.BOLD, 70);

    private static final int FRAME_WIDTH = 400;
    private static final int FRAME_HEIGHT = 100;

    void showIt() {
        create();
        Timer myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                dispose();
            }
        }, 500, 500);
    }

    private void create() {
        setIconImage(new ImageIcon(ICON_PATH).getImage());
        setTitle(TEXT);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(true);
        setVisible(true);
        AWTUtilities.setWindowOpacity(this, 0.5f);
        JLabel label = new JLabel(TEXT);
        label.setFont(MY_FONT);
        label.setForeground(Color.RED);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setForeground(Color.RED);
        add(label);
    }
}
