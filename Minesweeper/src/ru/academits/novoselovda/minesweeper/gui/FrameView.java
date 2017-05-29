package ru.academits.novoselovda.minesweeper.gui;

import ru.academits.novoselovda.minesweeper.common.*;
import ru.academits.novoselovda.minesweeper.control.Control;

import javax.swing.*;
import java.awt.*;

public class FrameView implements View, FrameListener {
    private Control control;

    private JFrame frame = new JFrame();
    private final int cellSize = 30;
    private final int menuHeight = 50;
    private final int topPanelHeight = 50;
    private final String title = "Сапёр (Minesweeper)";
    private final String iconPath = ".\\Minesweeper\\src\\ru\\academits\\novoselovda\\minesweeper\\resources\\cell_bomb.png";
    private int frameWidth;
    private int frameHeight;

    private MenuBar mainMenuBar;
    private FieldPanel.InsideClass field;
    private TopPanel topPanel;

    private int yCellsCount;
    private int xCellsCount;
    private int minesCount;

    public FrameView(Control control) {
        this.control = control;
        this.yCellsCount = this.control.getMinYCounts();
        this.xCellsCount = this.control.getMinXCounts();
        this.minesCount = this.control.getMinMinesCounts();
    }

    @Override
    public void startApplication() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                FrameView.this.control.restart();
                initFrame();
                initComponents();
            }
        });
    }

    private void initFrame() {
        this.frameWidth = cellSize * xCellsCount;
        this.frameHeight = cellSize * yCellsCount + this.menuHeight + this.topPanelHeight;
        this.frame.setTitle(this.title);
        this.frame.setSize(this.frameWidth, this.frameHeight);
        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(false);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.setVisible(true);
        this.frame.setIconImage(new ImageIcon(iconPath).getImage());
    }

    private void initComponents() {
        this.mainMenuBar = new MenuBar(this.control.getHighScore());
        this.mainMenuBar.init();
        this.mainMenuBar.addListener(this);
        this.frame.setJMenuBar(this.mainMenuBar);

        this.topPanel = new TopPanel(this.frameWidth, this.topPanelHeight);
        this.topPanel.init(this.minesCount);
        this.topPanel.addListener(this);
        this.topPanel.addListener(this.field);
        this.frame.add(this.topPanel, BorderLayout.PAGE_START);

        this.field = new FieldPanel().new InsideClass(this.control, yCellsCount,xCellsCount, minesCount);
        this.field.init();
        this.field.addListener(this.topPanel);
        this.frame.add(this.field, BorderLayout.CENTER);
    }

    @Override
    public void needStartNewGame(int yCellsCount, int xCellsCount, int minesCount) {
        this.frame.dispose();
        this.yCellsCount = yCellsCount;
        this.xCellsCount = xCellsCount;
        this.minesCount = minesCount;
        this.frame = new JFrame();
        startApplication();
    }

    @Override
    public void needRestart() {
        this.frame.dispose();
        this.frame = new JFrame();
        startApplication();
    }

    @Override
    public int getCellSize() {
        return cellSize;
    }
}