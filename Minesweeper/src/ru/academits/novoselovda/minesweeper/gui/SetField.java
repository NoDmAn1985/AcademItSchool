package ru.academits.novoselovda.minesweeper.gui;

import ru.academits.novoselovda.minesweeper.common.FrameListener;
import ru.academits.novoselovda.minesweeper.model.Field;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SetField extends JFrame {
    private final String title = "Параметры поля";
    private final String ySliderName = "Количество клеток по высоте: ";
    private final String xSliderName = "Количество клеток по ширине: ";
    private final String minesSliderName = "Количество мин на поле:\t";
    private final String buttonName = "Создать поле";
    private final int minesCountLimit = 999;

    private final Font myFont = new Font("Verdana", Font.BOLD, 14);

    private JTextField yCount = new JTextField(3);
    private JTextField xCount = new JTextField(3);
    private JTextField minesCount = new JTextField(3);

    private GridBagConstraints constraints;

    private final int frameWidth = 500;
    private final int frameHeight = 200;

    private JSlider mineSlider;
    private JSlider ySlider;
    private JSlider xSlider;
    private JPanel bigPanel;

    private FrameListener frameListener;

    private int minYCellsCount;
    private int minXCellsCount;
    private int minMinesCount;

    SetField(int minYCellsCount, int minXCellsCount, int minMinesCount) {
        this.minYCellsCount = minYCellsCount;
        this.minXCellsCount = minXCellsCount;
        this.minMinesCount = minMinesCount;
    }

    void showIt(FrameListener frameListener){
        this.frameListener = frameListener;
        setTitle(this.title);
        setSize(this.frameWidth, this.frameHeight);
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(true);
        setVisible(true);
        initComponents();
        pack();
    }

    private void initComponents() {
        this.bigPanel = new JPanel();
        this.bigPanel.setBorder(BorderFactory.createLineBorder(Color.blue));
        add(this.bigPanel);
        this.bigPanel.setLayout(new GridBagLayout());
        this.constraints = new GridBagConstraints();
        this.constraints.weighty = 1;

        int cellSize = this.frameListener.getCellSize();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int maxXCellsCount = screenSize.width / cellSize - 1;

        this.constraints.gridy = 1;
        this.xSlider = new JSlider(SwingConstants.HORIZONTAL, this.minXCellsCount, maxXCellsCount, this.minXCellsCount);
        setAndAddSlider(this.xSlider, this.xSliderName, this.xCount);
        this.xSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                SetField.this.xCount.setText("" + SetField.this.xSlider.getValue());
                SetField.this.mineSlider.setMaximum(getMaxMinesCount());
            }
        });

        int maxYCellsCount = (int) (screenSize.height * 0.85) / cellSize;

        this.constraints.gridy = 2;
        this.ySlider = new JSlider(SwingConstants.HORIZONTAL, this.minYCellsCount, maxYCellsCount, this.minYCellsCount);
        setAndAddSlider(this.ySlider, this.ySliderName, this.yCount);
        this.ySlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                SetField.this.yCount.setText("" + SetField.this.ySlider.getValue());
                SetField.this.mineSlider.setMaximum(getMaxMinesCount());
            }
        });

        this.constraints.gridy = 3;
        this.mineSlider = new JSlider(SwingConstants.HORIZONTAL, this.minMinesCount, getMaxMinesCount(), this.minMinesCount);
        setAndAddSlider(this.mineSlider, this.minesSliderName, this.minesCount);
        this.mineSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                SetField.this.minesCount.setText("" + SetField.this.mineSlider.getValue());
            }
        });

        this.constraints.gridy = 4;
        this.constraints.gridx = 1;
        this.constraints.gridwidth = 3;
        this.constraints.fill = GridBagConstraints.BOTH;
        JButton createButton = new JButton(this.buttonName);
        this.bigPanel.add(createButton, this.constraints);
        createButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SetField.this.frameListener.needStartNewGame(SetField.this.ySlider.getValue(),
                        SetField.this.xSlider.getValue(), SetField.this.mineSlider.getValue());
                SetField.this.dispose();
            }
        });
    }

    private void setAndAddSlider(JSlider slider, String nameOfSlider, JTextField counter) {
        this.constraints.gridx = 1;
        this.constraints.weightx = 1;
        this.constraints.gridwidth = 1;
        JLabel label = new JLabel(nameOfSlider);
        label.setFont(this.myFont);
        this.bigPanel.add(label, this.constraints);
        this.constraints.gridx = 2;
        this.constraints.weightx = 1;
        this.constraints.gridwidth = 1;
        this.bigPanel.add(slider, this.constraints);
        this.constraints.gridx = 3;
        this.constraints.weightx = 0.2;
        this.constraints.gridwidth = 1;
        counter.setText("" + slider.getValue());
        counter.setHorizontalAlignment(SwingConstants.CENTER);
        counter.setFont(this.myFont);
        counter.setEditable(false);
        this.bigPanel.add(counter, this.constraints);
    }

    private int getMaxMinesCount() {
        int count = (int) (this.xSlider.getValue() * this.ySlider.getValue() * 0.5);
        return (count > this.minesCountLimit ? this.minesCountLimit : count);
    }
}
