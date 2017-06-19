package ru.academits.novoselovda.minesweeper.gui;

import ru.academits.novoselovda.minesweeper.common.Signs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;

class MyButton extends JButton {
    private int yPos;
    private int xPos;
    private boolean isQuestionHere;
    private IconManger iconManger;

    MyButton(MouseListener mouseListener, int yPos, int xPos, IconManger iconManger) {
        if (mouseListener != null) {
            addMouseListener(mouseListener);
        }
        this.yPos = yPos;
        this.xPos = xPos;
        this.iconManger = iconManger;
    }

    void change(Signs sign) {
        boolean isButtonOn = false;
        ImageIcon icon;
        switch (sign) {
            case ZERO:
                icon = null;
                break;
            case ONE:
                icon = this.iconManger.getCell1Icon();
                break;
            case TWO:
                icon = this.iconManger.getCell2Icon();
                break;
            case THREE:
                icon = this.iconManger.getCell3Icon();
                break;
            case FOUR:
                icon = this.iconManger.getCell4Icon();
                break;
            case FIVE:
                icon = this.iconManger.getCell5Icon();
                break;
            case SIX:
                icon = this.iconManger.getCell6Icon();
                break;
            case SEVEN:
                icon = this.iconManger.getCell7Icon();
                break;
            case EIGHT:
                icon = this.iconManger.getCell8Icon();
                break;
            case MINE:
                icon = this.iconManger.getCellMineIcon();
                break;
            case FLAG:
                isButtonOn = true;
                icon = this.iconManger.getCellFlagIcon();
                this.isQuestionHere = false;
                break;
            case WRONG_FLAG:
                isButtonOn = true;
                icon = this.iconManger.getCellWrongFlagIcon();
                break;
            case QUESTION:
                isButtonOn = true;
                icon = this.iconManger.getCellQuestionIcon();
                this.isQuestionHere = true;
                break;
            case GAME_OVER:
                icon = this.iconManger.getCellGameOverIcon();
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
