package ru.academits.novoselovda.minesweeper.gui;

import javax.swing.*;
import java.awt.*;

class IconManger {
    private static final String RESOURCES_PATH = ".\\Minesweeper\\src\\ru\\academits\\novoselovda\\minesweeper\\resources\\";
    private static final String ICON_PATH = "cell_bomb.png";
    private Image icon;
    private static final String RESTART_ICON_PATH = "restart.png";
    private ImageIcon restartIcon;
    private static final String SET_FIELD_ICON_PATH = "options.png";
    private ImageIcon setFieldIcon;
    private static final String BEGINNER_ICON_PATH = "face_clicked.png";
    private ImageIcon beginnerIcon;
    private static final String AMATEUR_ICON_PATH = "face_default.png";
    private ImageIcon amateurIcon;
    private static final String PROFESSIONAL_ICON_PATH = "face_pressed.png";
    private ImageIcon professionalIcon;
    private static final String RECORDS_ICON_PATH = "face_win.png";
    private ImageIcon recordsIcon;
    private static final String CELL_ONE_PATH = "cell1.png";
    private ImageIcon cell1Icon;
    private static final String CELL_TWO_PATH = "cell2.png";
    private ImageIcon cell2Icon;
    private static final String CELL_THREE_PATH = "cell3.png";
    private ImageIcon cell3Icon;
    private static final String CELL_FOUR_PATH = "cell4.png";
    private ImageIcon cell4Icon;
    private static final String CELL_FIVE_PATH = "cell5.png";
    private ImageIcon cell5Icon;
    private static final String CELL_SIX_PATH = "cell6.png";
    private ImageIcon cell6Icon;
    private static final String CELL_SEVEN_PATH = "cell7.png";
    private ImageIcon cell7Icon;
    private static final String CELL_EIGHT_PATH = "cell8.png";
    private ImageIcon cell8Icon;
    private static final String CELL_MINE_PATH = "cell_bomb.png";
    private ImageIcon cellMineIcon;
    private static final String CELL_FLAG_PATH = "cell_flag.png";
    private ImageIcon cellFlagIcon;
    private static final String CELL_WRONG_FLAG_PATH = "cell_wrongflag.png";
    private ImageIcon cellWrongFlagIcon;
    private static final String CELL_QUESTION_PATH = "question.png";
    private ImageIcon cellQuestionIcon;
    private static final String CELL_GAME_OVER_PATH = "cell_gameover.png";
    private ImageIcon cellGameOverIcon;
    private ImageIcon topPanelFlagIcon;
    private static final String TIME_PATH = "clocks.png";
    private ImageIcon topPanelTimerIcon;
    private static final String DEFAULT_FACE_PATH = "face_default.png";
    private ImageIcon topPanelDefaultFaceIcon;
    private static final String PRESSED_FACE_PATH = "face_pressed.png";
    private ImageIcon topPanelPressedFaceIcon;
    private static final String WINS_FACE_PATH = "face_win.png";
    private ImageIcon topPanelWinsFaceIcon;
    private static final String LOST_FACE_PATH = "cell_gameover.png";
    private ImageIcon topPanelLostFaceIcon;
    private static final String CLICKED_FACE_PATH = "face_clicked.png";
    private ImageIcon topPanelClickedFaceIcon;

    private static final int CELL_ICON_DIFFERENCE = 5;

    IconManger(int cellSize, int menuItemIconSize, int topPanelHeight) {
        this.icon = new ImageIcon(ICON_PATH).getImage();
        this.restartIcon = imageToIcon(RESTART_ICON_PATH, menuItemIconSize);
        this.setFieldIcon = imageToIcon(SET_FIELD_ICON_PATH, menuItemIconSize);
        this.beginnerIcon = imageToIcon(BEGINNER_ICON_PATH, menuItemIconSize);
        this.amateurIcon = imageToIcon(AMATEUR_ICON_PATH, menuItemIconSize);
        this.professionalIcon = imageToIcon(PROFESSIONAL_ICON_PATH, menuItemIconSize);
        this.recordsIcon = imageToIcon(RECORDS_ICON_PATH, menuItemIconSize);
        this.cell1Icon = imageToIcon(CELL_ONE_PATH, cellSize - CELL_ICON_DIFFERENCE);
        this.cell2Icon = imageToIcon(CELL_TWO_PATH, cellSize - CELL_ICON_DIFFERENCE);
        this.cell3Icon = imageToIcon(CELL_THREE_PATH, cellSize - CELL_ICON_DIFFERENCE);
        this.cell4Icon = imageToIcon(CELL_FOUR_PATH, cellSize - CELL_ICON_DIFFERENCE);
        this.cell5Icon = imageToIcon(CELL_FIVE_PATH, cellSize - CELL_ICON_DIFFERENCE);
        this.cell6Icon = imageToIcon(CELL_SIX_PATH, cellSize - CELL_ICON_DIFFERENCE);
        this.cell7Icon = imageToIcon(CELL_SEVEN_PATH, cellSize - CELL_ICON_DIFFERENCE);
        this.cell8Icon = imageToIcon(CELL_EIGHT_PATH, cellSize - CELL_ICON_DIFFERENCE);
        this.cellMineIcon = imageToIcon(CELL_MINE_PATH, cellSize - CELL_ICON_DIFFERENCE);
        this.cellFlagIcon = imageToIcon(CELL_FLAG_PATH, cellSize - CELL_ICON_DIFFERENCE);
        this.cellWrongFlagIcon = imageToIcon(CELL_WRONG_FLAG_PATH, cellSize - CELL_ICON_DIFFERENCE);
        this.cellQuestionIcon = imageToIcon(CELL_QUESTION_PATH, cellSize - CELL_ICON_DIFFERENCE);
        this.cellGameOverIcon = imageToIcon(CELL_GAME_OVER_PATH, cellSize - CELL_ICON_DIFFERENCE);
        this.topPanelFlagIcon = imageToIcon(CELL_FLAG_PATH, cellSize);
        this.topPanelTimerIcon = imageToIcon(TIME_PATH, cellSize);
        this.topPanelDefaultFaceIcon = imageToIcon(DEFAULT_FACE_PATH, topPanelHeight);
        this.topPanelPressedFaceIcon = imageToIcon(PRESSED_FACE_PATH, topPanelHeight);
        this.topPanelWinsFaceIcon = imageToIcon(WINS_FACE_PATH, topPanelHeight);
        this.topPanelLostFaceIcon = imageToIcon(LOST_FACE_PATH, topPanelHeight);
        this.topPanelClickedFaceIcon = imageToIcon(CLICKED_FACE_PATH, topPanelHeight);
    }

    private ImageIcon imageToIcon(String iconPath, int size) {
        Image image = new ImageIcon(RESOURCES_PATH + iconPath).getImage();
        Image newImage = image.getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(newImage);
    }


    Image getIcon() {
        return icon;
    }

    ImageIcon getRestartIcon() {
        return restartIcon;
    }

    ImageIcon getSetFieldIcon() {
        return setFieldIcon;
    }

    ImageIcon getBeginnerIcon() {
        return beginnerIcon;
    }

    ImageIcon getAmateurIcon() {
        return amateurIcon;
    }

    ImageIcon getProfessionalIcon() {
        return professionalIcon;
    }

    ImageIcon getRecordsIcon() {
        return recordsIcon;
    }

    ImageIcon getCell1Icon() {
        return cell1Icon;
    }

    ImageIcon getCell2Icon() {
        return cell2Icon;
    }

    ImageIcon getCell3Icon() {
        return cell3Icon;
    }

    ImageIcon getCell4Icon() {
        return cell4Icon;
    }

    ImageIcon getCell5Icon() {
        return cell5Icon;
    }

    ImageIcon getCell6Icon() {
        return cell6Icon;
    }

    ImageIcon getCell7Icon() {
        return cell7Icon;
    }

    ImageIcon getCell8Icon() {
        return cell8Icon;
    }

    ImageIcon getCellMineIcon() {
        return cellMineIcon;
    }

    ImageIcon getCellFlagIcon() {
        return cellFlagIcon;
    }

    ImageIcon getCellWrongFlagIcon() {
        return cellWrongFlagIcon;
    }

    ImageIcon getCellQuestionIcon() {
        return cellQuestionIcon;
    }

    ImageIcon getCellGameOverIcon() {
        return cellGameOverIcon;
    }

    ImageIcon getTopPanelFlagIcon() {
        return topPanelFlagIcon;
    }

    ImageIcon getTopPanelTimerIcon() {
        return topPanelTimerIcon;
    }

    ImageIcon getTopPanelDefaultFaceIcon() {
        return topPanelDefaultFaceIcon;
    }

    ImageIcon getTopPanelPressedFaceIcon() {
        return topPanelPressedFaceIcon;
    }

    ImageIcon getTopPanelWinsFaceIcon() {
        return topPanelWinsFaceIcon;
    }

    ImageIcon getTopPanelLostFaceIcon() {
        return topPanelLostFaceIcon;
    }

    ImageIcon getTopPanelClickedFaceIcon() {
        return topPanelClickedFaceIcon;
    }
}
