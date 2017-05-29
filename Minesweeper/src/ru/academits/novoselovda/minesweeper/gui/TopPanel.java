package ru.academits.novoselovda.minesweeper.gui;

import ru.academits.novoselovda.minesweeper.common.FieldPanelListener;
import ru.academits.novoselovda.minesweeper.common.FrameListener;
import ru.academits.novoselovda.minesweeper.common.TopPanelListener;
import ru.academits.novoselovda.minesweeper.control.Control;
import ru.academits.novoselovda.minesweeper.model.MyTimerTask;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.Timer;

public class TopPanel extends JPanel implements TopPanelListener {
    private String flagPath = ".\\Minesweeper\\src\\ru\\academits\\novoselovda\\minesweeper\\resources\\cell_flag.png";
    private String timePath = ".\\Minesweeper\\src\\ru\\academits\\novoselovda\\minesweeper\\resources\\clocks.png";
    private String defaultFacePath = ".\\Minesweeper\\src\\ru\\academits\\novoselovda\\minesweeper\\resources\\face_default.png";
    private String pressedFacePath = ".\\Minesweeper\\src\\ru\\academits\\novoselovda\\minesweeper\\resources\\face_pressed.png";
    private String winsFacePath = ".\\Minesweeper\\src\\ru\\academits\\novoselovda\\minesweeper\\resources\\face_win.png";
    private String lostFacePath = ".\\Minesweeper\\src\\ru\\academits\\novoselovda\\minesweeper\\resources\\cell_gameover.png";
    private String clickedFacePath = ".\\Minesweeper\\src\\ru\\academits\\novoselovda\\minesweeper\\resources\\face_clicked.png";
    private String flagToolTip = "Осталось флагов";
    private String faceToolTip = "Перезапустить игру";
    private String timeToolTip = "Времени прошло";
    private final Font myFont = new Font("Verdana", Font.BOLD, 18);

    private JTextField flagsCounter;
    private JTextField timer;
    private JButton face;

    private int panelWidth;
    private int panelHeight;

    private FrameListener frameListener;
    private FieldPanelListener fieldListener;

    private MyTimerTask myTask;


    TopPanel(int panelWidth, int panelHeight) {
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
    }

    void init(int minesCount) {
        removeAll();
        setSize(this.panelWidth, this.panelHeight);
        setBorder(BorderFactory.createRaisedBevelBorder());
        int smallSize = (int) (getHeight() * 0.7);

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridheight = 1;
        constraints.gridy = 1;
        constraints.weighty = 1;

        constraints.gridx = 1;
        constraints.weightx = 0.5;
        constraints.anchor = GridBagConstraints.CENTER;
        JLabel topPanelCell1 = new JLabel();
        add(topPanelCell1, constraints);

        constraints.gridx = 2;
        constraints.weightx = 1;
        constraints.anchor = GridBagConstraints.EAST;
        this.flagsCounter = new JTextField();
        this.flagsCounter.setColumns(3);
        this.flagsCounter.setFont(this.myFont);
        this.flagsCounter.setBackground(Color.black);
        this.flagsCounter.setForeground(Color.white);
        this.flagsCounter.setText(new DecimalFormat("000").format(minesCount));
        this.flagsCounter.setEditable(false);
        this.flagsCounter.setHorizontalAlignment(SwingConstants.CENTER);
        this.flagsCounter.setToolTipText(this.flagToolTip);
        add(this.flagsCounter, constraints);

        constraints.gridx = 3;
        constraints.weightx = 0.5;
        constraints.anchor = GridBagConstraints.WEST;
        JLabel topPanelCell3 = new JLabel();
        Image image = new ImageIcon(flagPath).getImage();
        Image newImage = image.getScaledInstance(smallSize, smallSize, java.awt.Image.SCALE_SMOOTH);
        topPanelCell3.setIcon(new ImageIcon(newImage));
        add(topPanelCell3, constraints);

        constraints.gridx = 4;
        constraints.weightx = 0.5;
        constraints.anchor = GridBagConstraints.CENTER;
        JLabel topPanelCell4 = new JLabel();
        add(topPanelCell4, constraints);

        constraints.gridx = 5;
        constraints.weightx = 0.5;
        constraints.anchor = GridBagConstraints.CENTER;
        this.face = new JButton();
        setNewFace(this.defaultFacePath);
        this.face.setMargin(new Insets(2, 2, 2, 2));
        this.face.setToolTipText(this.faceToolTip);
        this.face.addMouseListener(new MouseAdapter() {
            private Icon face;
            @Override
            public void mouseClicked(MouseEvent e) {
                TopPanel.this.frameListener.needRestart();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                this.face = TopPanel.this.face.getIcon();
                setNewFace(TopPanel.this.clickedFacePath);
            }

            @Override
                public void mouseReleased(MouseEvent e) {
                TopPanel.this.face.setIcon(this.face);
            }
        });
        add(this.face, constraints);

        constraints.gridx = 6;
        constraints.weightx = 0.5;
        constraints.anchor = GridBagConstraints.CENTER;
        JLabel topPanelCell6 = new JLabel();
        add(topPanelCell6, constraints);

        constraints.gridx = 7;
        constraints.weightx = 0.5;
        constraints.anchor = GridBagConstraints.EAST;
        JLabel topPanelCell7 = new JLabel();
        image = new ImageIcon(timePath).getImage();
        newImage = image.getScaledInstance(smallSize, smallSize, java.awt.Image.SCALE_SMOOTH);
        topPanelCell7.setIcon(new ImageIcon(newImage));
        add(topPanelCell7, constraints);

        constraints.gridx = 8;
        constraints.weightx = 1;
        constraints.anchor = GridBagConstraints.WEST;
        this.timer = new JTextField();
        this.timer.setColumns(3);
        this.timer.setFont(myFont);
        this.timer.setBackground(Color.black);
        this.timer.setForeground(Color.white);
        this.timer.setText("000");
        this.timer.setEditable(false);
        this.timer.setHorizontalAlignment(SwingConstants.CENTER);
        this.timer.setToolTipText(this.timeToolTip);
        add(this.timer, constraints);

        constraints.gridx = 9;
        constraints.weightx = 0.5;
        constraints.anchor = GridBagConstraints.CENTER;
        JLabel topPanelCell9 = new JLabel();
        add(topPanelCell9, constraints);
    }

    @Override
    public void needStartTimer() {
        this.myTask = new MyTimerTask(this.timer);
        this.myTask.addListener(this.fieldListener);
        new java.util.Timer().schedule(this.myTask,10,100);
    }

    @Override
    public void needStopTimer() {
        this.myTask.pause();
    }

    @Override
    public void needShowLost() {
        setNewFace(this.lostFacePath);
    }

    @Override
    public void needShowWins() {
        setNewFace(this.winsFacePath);
    }

    @Override
    public void needShowPressed() {
        setNewFace(this.pressedFacePath);
    }

    @Override
    public void needShowDefaultFace() {
        setNewFace(this.defaultFacePath);
    }

    @Override
    public void needUpdateFlagsCounter(int count) {
        this.flagsCounter.setText(new DecimalFormat("000").format(count));
    }

    private void setNewFace(String facePath) {
        Image image = new ImageIcon(facePath).getImage();
        Image newImage = image.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
        this.face.setIcon(new ImageIcon(newImage));
    }

    void addListener(FrameListener frameListener) {
        this.frameListener = frameListener;
    }

    void addListener(FieldPanelListener fieldListener) {
        this.fieldListener = fieldListener;
    }


}