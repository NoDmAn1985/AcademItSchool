package ru.academits.novoselovda.minesweeper.gui;

import com.sun.awt.AWTUtilities;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class GameOverMessage extends JFrame {
    private final String iconPath = ".\\Minesweeper\\src\\ru\\academits\\novoselovda\\minesweeper\\resources\\cell_gameover.png";
    private final String text = "Ба-бах!";
    private final Font myFont = new Font("Verdana", Font.BOLD, 70);

    private final int frameWidth = 400;
    private final int frameHeight = 100;

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
        setIconImage(new ImageIcon(iconPath).getImage());
        setTitle(this.text);
        setSize(this.frameWidth, this.frameHeight);
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(true);
        setVisible(true);
        AWTUtilities.setWindowOpacity(this, 0.5f);
        JLabel label = new JLabel(this.text);
        label.setFont(myFont);
        label.setForeground(Color.RED);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setForeground(Color.RED);
        add(label);
    }
}
