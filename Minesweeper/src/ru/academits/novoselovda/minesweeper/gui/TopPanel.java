package ru.academits.novoselovda.minesweeper.gui;

import ru.academits.novoselovda.minesweeper.common.FrameListener;
import ru.academits.novoselovda.minesweeper.common.TopPanelListener;
import ru.academits.novoselovda.minesweeper.model.MyTimerTask;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;

public class TopPanel extends JPanel implements TopPanelListener {
    private static final String FLAG_TOOL_TIP = "Осталось флагов";
    private static final String FACE_TOOL_TIP = "Перезапустить игру";
    private static final String TIME_TOOL_TIP = "Времени прошло";
    private static final Font MY_FONT = new Font("Verdana", Font.BOLD, 18);

    private JTextField flagsCounter;
    private JTextField timer;
    private JButton face;

    private int panelWidth;
    private int panelHeight;

    private FrameListener frameListener;

    private MyTimerTask myTask;
    private IconManager iconManager;

    TopPanel(int panelWidth, int panelHeight, IconManager iconManager) {
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
        this.iconManager = iconManager;
    }

    void init(int minesCount) {
        removeAll();
        setSize(this.panelWidth, this.panelHeight);
        setBorder(BorderFactory.createRaisedBevelBorder());

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
        this.flagsCounter.setFont(MY_FONT);
        this.flagsCounter.setBackground(Color.black);
        this.flagsCounter.setForeground(Color.white);
        this.flagsCounter.setText(new DecimalFormat("000").format(minesCount));
        this.flagsCounter.setEditable(false);
        this.flagsCounter.setHorizontalAlignment(SwingConstants.CENTER);
        this.flagsCounter.setToolTipText(FLAG_TOOL_TIP);
        add(this.flagsCounter, constraints);

        constraints.gridx = 3;
        constraints.weightx = 0.5;
        constraints.anchor = GridBagConstraints.WEST;
        JLabel topPanelCell3 = new JLabel();
        topPanelCell3.setIcon(this.iconManager.getTopPanelFlagIcon());
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
        this.face.setIcon(this.iconManager.getTopPanelDefaultFaceIcon());
        this.face.setMargin(new Insets(2, 2, 2, 2));
        this.face.setToolTipText(FACE_TOOL_TIP);
        this.face.addMouseListener(new MouseAdapter() {
            private Icon face;
            @Override
            public void mouseClicked(MouseEvent e) {
                TopPanel.this.frameListener.needRestart();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                this.face = TopPanel.this.face.getIcon();
                TopPanel.this.face.setIcon(TopPanel.this.iconManager.getTopPanelClickedFaceIcon());
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
        topPanelCell7.setIcon(this.iconManager.getTopPanelTimerIcon());
        add(topPanelCell7, constraints);

        constraints.gridx = 8;
        constraints.weightx = 1;
        constraints.anchor = GridBagConstraints.WEST;
        this.timer = new JTextField();
        this.timer.setColumns(3);
        this.timer.setFont(MY_FONT);
        this.timer.setBackground(Color.black);
        this.timer.setForeground(Color.white);
        this.timer.setText("000");
        this.timer.setEditable(false);
        this.timer.setHorizontalAlignment(SwingConstants.CENTER);
        this.timer.setToolTipText(TIME_TOOL_TIP);
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
        new java.util.Timer().schedule(this.myTask,10,100);
    }

    @Override
    public void needStopTimer() {
        this.myTask.pause();
    }

    @Override
    public void needShowLost() {
        this.face.setIcon(this.iconManager.getTopPanelLostFaceIcon());
    }

    @Override
    public void needShowWins() {
        this.face.setIcon(this.iconManager.getTopPanelWinsFaceIcon());
    }

    @Override
    public void needShowPressed() {
        this.face.setIcon(this.iconManager.getTopPanelPressedFaceIcon());
    }

    @Override
    public void needShowDefaultFace() {
        this.face.setIcon(this.iconManager.getTopPanelDefaultFaceIcon());
    }

    @Override
    public void needUpdateFlagsCounter(int count) {
        this.flagsCounter.setText(new DecimalFormat("000").format(count));
    }

    void addListener(FrameListener frameListener) {
        this.frameListener = frameListener;
    }

}