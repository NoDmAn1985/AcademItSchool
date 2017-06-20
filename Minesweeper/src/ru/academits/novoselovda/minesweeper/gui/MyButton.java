package ru.academits.novoselovda.minesweeper.gui;

import ru.academits.novoselovda.minesweeper.common.Signs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;

class MyButton extends JButton {
    private int yPos;
    private int xPos;
    private boolean isQuestionHere;
    private IconManager iconManager;

    MyButton(MouseListener mouseListener, int yPos, int xPos, IconManager iconManager) {
        if (mouseListener != null) {
            addMouseListener(mouseListener);
        }
        this.yPos = yPos;
        this.xPos = xPos;
        this.iconManager = iconManager;
    }

    void change(Signs sign) {
        boolean isButtonOn = false;
        ImageIcon icon;
        switch (sign) {
            case ZERO:
                icon = null;
                break;
            case ONE:
                icon = this.iconManager.getCell1Icon();
                break;
            case TWO:
                icon = this.iconManager.getCell2Icon();
                break;
            case THREE:
                icon = this.iconManager.getCell3Icon();
                break;
            case FOUR:
                icon = this.iconManager.getCell4Icon();
                break;
            case FIVE:
                icon = this.iconManager.getCell5Icon();
                break;
            case SIX:
                icon = this.iconManager.getCell6Icon();
                break;
            case SEVEN:
                icon = this.iconManager.getCell7Icon();
                break;
            case EIGHT:
                icon = this.iconManager.getCell8Icon();
                break;
            case MINE:
                icon = this.iconManager.getCellMineIcon();
                break;
            case FLAG:
                isButtonOn = true;
                icon = this.iconManager.getCellFlagIcon();
                this.isQuestionHere = false;
                break;
            case WRONG_FLAG:
                isButtonOn = true;
                icon = this.iconManager.getCellWrongFlagIcon();
                break;
            case QUESTION:
                isButtonOn = true;
                icon = this.iconManager.getCellQuestionIcon();
                this.isQuestionHere = true;
                break;
            case GAME_OVER:
                icon = this.iconManager.getCellGameOverIcon();
                break;
            default:
                isButtonOn = true;
                icon = null;
                this.isQuestionHere = false;
        }
        if (icon != null) {
            setIcon(icon);
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
