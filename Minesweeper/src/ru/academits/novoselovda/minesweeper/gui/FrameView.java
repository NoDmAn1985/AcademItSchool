package ru.academits.novoselovda.minesweeper.gui;

import ru.academits.novoselovda.minesweeper.common.*;
import ru.academits.novoselovda.minesweeper.control.Control;

import javax.swing.*;
import java.awt.*;

public class FrameView implements View, FrameListener, ErrorShowMessageListener {
    private Control control;

    private static final int CELL_SIZE = 30;
    private static final int MENU_ICON_SIZE = 30;
    private static final int MENU_HEIGHT = 50;
    private static final int TOP_PANEL_HEIGHT = 50;
    private static final String TITLE = "Сапёр (Minesweeper)";

    private int frameWidth;

    private JFrame frame = new JFrame();
    private IconManager iconManager;

    private int yCellsCount;
    private int xCellsCount;
    private int minesCount;

    private boolean isItFirstGame = true;
    private int yFramePos;
    private int xFramePos;

    public FrameView(Control control) {
        this.control = control;
        this.control.setErrorMessageListener(this);
        this.yCellsCount = this.control.getMinYCounts();
        this.xCellsCount = this.control.getMinXCounts();
        this.minesCount = this.control.getMinMinesCounts();
    }

    @Override
    public void startApplication() {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            FrameView.this.control.restart();
            initFrame();
            initComponents();
        });
    }

    private void initFrame() {
        this.frameWidth = CELL_SIZE * xCellsCount;
        int frameHeight = CELL_SIZE * yCellsCount + MENU_HEIGHT + TOP_PANEL_HEIGHT;
        this.frame.setTitle(TITLE);
        this.frame.setSize(this.frameWidth, frameHeight);
        if (this.isItFirstGame) {
            iconManager = new IconManager(CELL_SIZE, MENU_ICON_SIZE, TOP_PANEL_HEIGHT);
            this.frame.setLocationRelativeTo(null);
            this.isItFirstGame = false;
        } else {
            this.frame.setLocation(this.xFramePos, this.yFramePos);
        }
        this.frame.setResizable(false);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.setVisible(true);
        this.frame.setIconImage(this.iconManager.getIcon());
    }

    private void initComponents() {
        MenuBar mainMenuBar = new MenuBar(this.control.getHighScore(), this.iconManager);
        mainMenuBar.init();
        mainMenuBar.addListener(this);
        this.frame.setJMenuBar(mainMenuBar);

        TopPanel topPanel = new TopPanel(this.frameWidth, TOP_PANEL_HEIGHT, this.iconManager);
        topPanel.init(this.minesCount);
        topPanel.addListener(this);
        this.frame.add(topPanel, BorderLayout.PAGE_START);

        FieldPanel.InsideClass field = new FieldPanel().new InsideClass(this.control, yCellsCount,xCellsCount, minesCount, this.iconManager);
        field.init();
        field.addListener(topPanel);
        this.frame.add(field, BorderLayout.CENTER);
    }

    @Override
    public void needStartNewGame(int yCellsCount, int xCellsCount, int minesCount) {
        this.yFramePos = this.frame.getLocationOnScreen().y;
        this.xFramePos = this.frame.getLocationOnScreen().x;
        this.frame.dispose();
        this.yCellsCount = yCellsCount;
        this.xCellsCount = xCellsCount;
        this.minesCount = minesCount;
        this.frame = new JFrame();
        startApplication();
    }

    @Override
    public void needRestart() {
        this.yFramePos = this.frame.getLocationOnScreen().y;
        this.xFramePos = this.frame.getLocationOnScreen().x;
        this.frame.dispose();
        this.frame = new JFrame();
        startApplication();
    }

    @Override
    public int getCellSize() {
        return CELL_SIZE;
    }

    @Override
    public void needShowErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(this.frame, errorMessage, null, JOptionPane.ERROR_MESSAGE);
    }
}