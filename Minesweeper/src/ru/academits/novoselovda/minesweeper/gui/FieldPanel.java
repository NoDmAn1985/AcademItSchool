package ru.academits.novoselovda.minesweeper.gui;

import ru.academits.novoselovda.minesweeper.common.AbstractFieldView;
import ru.academits.novoselovda.minesweeper.common.FieldPanelListener;
import ru.academits.novoselovda.minesweeper.common.Signs;
import ru.academits.novoselovda.minesweeper.common.TopPanelListener;
import ru.academits.novoselovda.minesweeper.control.Control;
import ru.academits.novoselovda.minesweeper.model.HighScore;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FieldPanel extends AbstractFieldView {
    private MyButton[][] map;

    @Override
    protected void change(Signs signs, int y, int x) {
        this.map[y][x].change(signs);
    }

    public class InsideClass extends JPanel implements FieldPanelListener {
        private TopPanelListener topPanelListener;
        private IconManger iconManger;
        private boolean isGameOver;
        private Border border;

        InsideClass(Control control, int yCellsCount, int xCellsCount, int minesCount, IconManger iconManger) {
            FieldPanel.this.control = control;
            FieldPanel.this.yCellsCount = yCellsCount;
            FieldPanel.this.xCellsCount = xCellsCount;
            FieldPanel.this.minesCount = minesCount;
            this.border = new JButton().getBorder();
            this.iconManger = iconManger;
        }

        void init() {
            removeAll();
            this.isGameOver = false;
            setLayout(new GridLayout(0, FieldPanel.this.xCellsCount));
            FieldPanel.this.map = new MyButton[FieldPanel.this.yCellsCount][FieldPanel.this.xCellsCount];
            for (int y = 0; y < FieldPanel.this.yCellsCount; y++) {
                for (int x = 0; x < FieldPanel.this.xCellsCount; x++) {
                    FieldPanel.this.map[y][x] = new MyButton(new FieldActionListener(), y, x, this.iconManger);
                    add(FieldPanel.this.map[y][x]);
                }
            }
        }

        @Override
        public void needGameOver() {
            FieldPanel.this.gameLost(-1, -1);
            gameOver();
        }

        private class FieldActionListener extends MouseAdapter {

            @Override
            public void mousePressed(MouseEvent e) {
                if (isGameOver) {
                    return;
                }
                InsideClass.this.topPanelListener.needShowPressed();
                MyButton button = (MyButton) e.getSource();
                int yPos = button.getYPos();
                int xPos = button.getXPos();
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (FieldPanel.this.control.isItFirstMove()) {
                        InsideClass.this.topPanelListener.needStartTimer();
                    }
                    if (FieldPanel.this.control.isNewGameStarted() && FieldPanel.this.control.isFlagHere(yPos, xPos)) {
                        return;
                    }
                    FieldPanel.this.openThisCell(yPos, xPos);
                    FieldPanel.InsideClass.this.topPanelListener.needUpdateFlagsCounter(
                            FieldPanel.this.control.getFlagsRemainCount(FieldPanel.this.minesCount));
                } else if (e.getButton() == MouseEvent.BUTTON2 && FieldPanel.this.control.isNewGameStarted() &&
                        FieldPanel.this.control.isCellShown(yPos, xPos)) {
                    openOrShowVariants(yPos, xPos);
                } else if (e.getButton() == MouseEvent.BUTTON3 && FieldPanel.this.control.isNewGameStarted() &&
                        !FieldPanel.this.control.isCellShown(yPos, xPos)) {
                    rightButtonClicked(yPos, xPos);
                }
                if (!FieldPanel.this.control.isItFirstMove()) {
                    gameOver();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (isGameOver) {
                    return;
                }
                InsideClass.this.topPanelListener.needShowDefaultFace();
                if (e.getButton() == MouseEvent.BUTTON2 && FieldPanel.this.control.isNewGameStarted()) {
                    MyButton button = (MyButton) e.getSource();
                    int yPos = button.getYPos();
                    int xPos = button.getXPos();
                    changeBorders(InsideClass.this.border, yPos, xPos);
                }
            }
        }

        private void rightButtonClicked(int yPos, int xPos) {
            if (FieldPanel.this.control.isFlagHere(yPos, xPos)) {
                takeFlagOff(yPos, xPos);
                this.topPanelListener.needUpdateFlagsCounter(
                        FieldPanel.this.control.getFlagsRemainCount(FieldPanel.this.minesCount));
                FieldPanel.this.putQuestion(yPos, xPos);
            } else if (FieldPanel.this.map[yPos][xPos].isQuestionHere()) {
                FieldPanel.this.takeQuestionOff(yPos, xPos);
            } else {
                putFlag(yPos, xPos);
                this.topPanelListener.needUpdateFlagsCounter(
                        FieldPanel.this.control.getFlagsRemainCount(FieldPanel.this.minesCount));
            }
        }

        void openOrShowVariants(int yPos, int xPos) {
            if (FieldPanel.this.control.isAllNeighboringFlagsPutted(yPos, xPos)) {
                openNeighborCells(yPos, xPos);
            } else {
                changeBorders(BorderFactory.createRaisedBevelBorder(), yPos, xPos);
            }
        }

        void changeBorders(Border border, int yPos, int xPos) {
            int yMin = (yPos == 0 ? yPos : yPos - 1);
            int yMax = (yPos == FieldPanel.this.yCellsCount - 1 ? yPos : yPos + 1);
            int xMin = (xPos == 0 ? xPos : xPos - 1);
            int xMax = (xPos == FieldPanel.this.xCellsCount - 1 ? xPos : xPos + 1);
            for (int y = yMin; y <= yMax; y++) {
                for (int x = xMin; x <= xMax; x++) {
                    if (!FieldPanel.this.control.isFlagHere(y, x) && !FieldPanel.this.control.isCellShown(y, x) &&
                            FieldPanel.this.map[y][x].getBorder() != border) {
                        FieldPanel.this.map[y][x].setBorder(border);
                    }
                }
            }
        }

        private void gameOver() {
            boolean isLost = FieldPanel.this.control.isLost();
            if (isLost || FieldPanel.this.control.isWin()) {
                InsideClass.this.topPanelListener.needStopTimer();
                if (isLost) {
                    new GameOverMessage().showIt();
                    InsideClass.this.topPanelListener.needShowLost();
                } else {
                    FieldPanel.this.openAllCells(true);
                    InsideClass.this.topPanelListener.needUpdateFlagsCounter(0);
                    InsideClass.this.topPanelListener.needShowWins();
                    HighScore highScore = FieldPanel.this.control.getHighScore();
                    int time = FieldPanel.this.control.getTime();
                    FieldPanel.this.control.saveScore((String) JOptionPane.showInputDialog(InsideClass.this.getParent(),
                            "Введите своё имя:", "ПОБЕДА!!! Вы обезвредили все мины",
                            JOptionPane.QUESTION_MESSAGE, null, null,
                            highScore.getUserName()), time);
                    JOptionPane.showMessageDialog(InsideClass.this.getParent(), highScore.show(),
                            highScore.getTitle(), JOptionPane.PLAIN_MESSAGE);
                }
                InsideClass.this.isGameOver = true;
            }
        }

        void addListener(TopPanelListener topPanelListener) {
            this.topPanelListener = topPanelListener;
        }
    }
}
