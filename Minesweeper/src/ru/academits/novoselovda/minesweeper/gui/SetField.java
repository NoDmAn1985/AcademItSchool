package ru.academits.novoselovda.minesweeper.gui;

import ru.academits.novoselovda.minesweeper.common.FrameListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class SetField extends JFrame {
    private static final String TITLE = "Параметры поля";
    private static final String Y_SLIDER_NAME = "Количество клеток по высоте: ";
    private static final String X_SLIDER_NAME = "Количество клеток по ширине: ";
    private static final String MINES_SLIDER_NAME = "Количество мин на поле:\t";
    private static final String BUTTON_NAME = "Создать поле";
    private static final int MINES_COUNT_LIMIT = 999;
    private static final Font MY_FONT = new Font("Verdana", Font.BOLD, 14);
    private static final int FRAME_WIDTH = 500;
    private static final int FRAME_HEIGHT = 200;

    private JTextField yCount = new JTextField(3);
    private JTextField xCount = new JTextField(3);
    private JTextField minesCount = new JTextField(3);

    private GridBagConstraints constraints;

    private JSlider mineSlider;
    private JSlider ySlider;
    private JSlider xSlider;
    private JPanel bigPanel;

    private FrameListener frameListener;

    private int minYCellsCount;
    private int minXCellsCount;
    private int minMinesCount;

    private Container container;

    SetField(Container container, int minYCellsCount, int minXCellsCount, int minMinesCount) {
        this.container = container;
        this.minYCellsCount = minYCellsCount;
        this.minXCellsCount = minXCellsCount;
        this.minMinesCount = minMinesCount;
    }

    void showIt(FrameListener frameListener){
        this.frameListener = frameListener;
        setTitle(TITLE);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                SetField.this.container.setEnabled(true);
                SetField.this.dispose();
            }
        });

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
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
        setAndAddSlider(this.xSlider, X_SLIDER_NAME, this.xCount);
        this.xSlider.addChangeListener(e -> {
            SetField.this.xCount.setText("" + SetField.this.xSlider.getValue());
            SetField.this.mineSlider.setMaximum(getMaxMinesCount());
        });

        int maxYCellsCount = (int) (screenSize.height * 0.85) / cellSize;

        this.constraints.gridy = 2;
        this.ySlider = new JSlider(SwingConstants.HORIZONTAL, this.minYCellsCount, maxYCellsCount, this.minYCellsCount);
        setAndAddSlider(this.ySlider, Y_SLIDER_NAME, this.yCount);
        this.ySlider.addChangeListener(e -> {
            SetField.this.yCount.setText("" + SetField.this.ySlider.getValue());
            SetField.this.mineSlider.setMaximum(getMaxMinesCount());
        });

        this.constraints.gridy = 3;
        this.mineSlider = new JSlider(SwingConstants.HORIZONTAL, this.minMinesCount, getMaxMinesCount(), this.minMinesCount);
        setAndAddSlider(this.mineSlider, MINES_SLIDER_NAME, this.minesCount);
        this.mineSlider.addChangeListener(e -> SetField.this.minesCount.setText("" + SetField.this.mineSlider.getValue()));

        this.constraints.gridy = 4;
        this.constraints.gridx = 1;
        this.constraints.gridwidth = 3;
        this.constraints.fill = GridBagConstraints.BOTH;
        JButton createButton = new JButton(BUTTON_NAME);
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
        label.setFont(MY_FONT);
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
        counter.setFont(MY_FONT);
        counter.setEditable(false);
        this.bigPanel.add(counter, this.constraints);
    }

    private int getMaxMinesCount() {
        int count = (int) (this.xSlider.getValue() * this.ySlider.getValue() * 0.5);
        return (count > MINES_COUNT_LIMIT ? MINES_COUNT_LIMIT : count);
    }
}
