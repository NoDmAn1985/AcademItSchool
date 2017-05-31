package ru.academits.novoselovda.minesweeper.gui;

import ru.academits.novoselovda.minesweeper.common.Signs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;

class MyButton extends JButton {
    private static final String PATH = ".\\Minesweeper\\src\\ru\\academits\\novoselovda\\minesweeper\\resources\\";
    private int yPos;
    private int xPos;
    private boolean isQuestionHere;

    MyButton(MouseListener mouseListener, int yPos, int xPos) {
        if (mouseListener != null) {
            addMouseListener(mouseListener);
        }
        this.yPos = yPos;
        this.xPos = xPos;
    }

    void change(Signs sign) {
        boolean isButtonOn = false;
        String iconPath;
        switch (sign) {
            case ZERO:
                iconPath = null;
                break;
            case ONE:
                iconPath = "cell1.png";
                break;
            case TWO:
                iconPath = "cell2.png";
                break;
            case THREE:
                iconPath = "cell3.png";
                break;
            case FOUR:
                iconPath = "cell4.png";
                break;
            case FIVE:
                iconPath = "cell5.png";
                break;
            case SIX:
                iconPath = "cell6.png";
                break;
            case SEVEN:
                iconPath = "cell7.png";
                break;
            case EIGHT:
                iconPath = "cell8.png";
                break;
            case MINE:
                iconPath = "cell_bomb.png";
                break;
            case FLAG:
                isButtonOn = true;
                iconPath = "cell_flag.png";
                this.isQuestionHere = false;
                break;
            case WRONG_FLAG:
                isButtonOn = true;
                iconPath = "cell_wrongflag.png";
                break;
            case QUESTION:
                isButtonOn = true;
                iconPath = "question.png";
                this.isQuestionHere = true;
                break;
            case GAME_OVER:
                iconPath = "cell_gameover.png";
                break;
            default:
                isButtonOn = true;
                iconPath = null;
                this.isQuestionHere = false;
        }
        if (iconPath != null) {
            Image image = new ImageIcon((PATH + iconPath)).getImage();
            int iconSize = this.getWidth() - 5;
            Image newImage = image.getScaledInstance(iconSize, iconSize, java.awt.Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(newImage));
            setMargin(new Insets(0, 0, 0, 0));
        } else {
            setIcon(null);
        }
        setContentAreaFilled(isButtonOn);
        setFocusPainted(false);
    }

    int getYPos() {
        return yPos;
    }

    int getXPos() {
        return xPos;
    }

    boolean isQuestionHere() {
        return this.isQuestionHere;
    }
}
