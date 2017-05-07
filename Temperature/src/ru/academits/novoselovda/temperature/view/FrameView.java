package ru.academits.novoselovda.temperature.view;

import ru.academits.novoselovda.temperature.control.Control;
import ru.academits.novoselovda.temperature.interfaces.View;
import ru.academits.novoselovda.temperature.interfaces.ViewListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class FrameView implements View {
    private final ArrayList<ViewListener> listeners = new ArrayList<>();

    private final Font myFont = new Font("Verdana", Font.PLAIN, 12);
    private final Font myFontBig = new Font("Verdana", Font.BOLD, 16);

    private final int frameWidth = 550;
    private final int frameHeight = 100;
    private final int editTextLength = 10;

    private final String title = "Перевод температуры из одной шкалы в другую";
    private final String description1Text = "Ваша темп.:";
    private final String description2Text = "Результат:";
    private final String calcButtonText = "Пересчитать";
    private final String defaultResultText = "нажмите \"Пересчитать\"";
    private final String errorResultText = "ОШИБКА: нет цифр в верхем поле";
    private final String errorText = "Для ввода доступны только: знаки '-' (один раз, в начале)\n" +
            "и ',' (один раз, после цифры) и цифры. Всего " + this.editTextLength + " символов";
    private final String[] list = Control.getListOfScales();

    private final JFrame frame = new JFrame(this.title);
    private final JLabel description1 = new JLabel(this.description1Text);
    private final JTextField editText = new JTextField();
    private final JComboBox chooseUserScale = new JComboBox(this.list);
    private final JButton calcButton = new JButton(this.calcButtonText);
    private final JLabel description2 = new JLabel(this.description2Text);
    private final JLabel result = new JLabel(this.defaultResultText, SwingConstants.CENTER);
    private final JComboBox chooseResultScale = new JComboBox(this.list);


    private void initFrame() {
        this.frame.setSize(this.frameWidth, this.frameHeight);
        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(false);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.setVisible(true);
    }

    private void initContent() {
        this.frame.setLayout(new GridBagLayout());
        GridBagConstraints constants = new GridBagConstraints();
        constants.weighty = 1;
        constants.weightx = 1;
        constants.fill = GridBagConstraints.BOTH;

        constants.gridx = 1;
        constants.gridy = 1;
        constants.gridheight = 1;
        this.description1.setFont(this.myFont);
        this.frame.add(this.description1, constants);

        constants.gridx = 2;
        constants.gridy = 1;
        constants.gridheight = 1;
        this.editText.setHorizontalAlignment(SwingConstants.CENTER);
        this.editText.setText("0,0");
        this.editText.setColumns(this.editTextLength);
        this.editText.setFont(myFontBig);
        this.frame.add(this.editText, constants);

        constants.gridx = 4;
        constants.gridy = 1;
        constants.gridheight = 1;
        this.chooseUserScale.setFont(this.myFont);
        this.chooseUserScale.setSelectedIndex(0);
        this.frame.add(this.chooseUserScale, constants);

        constants.gridx = 5;
        constants.gridy = 1;
        constants.gridheight = GridBagConstraints.REMAINDER;
        this.calcButton.setMargin(new Insets(1, 1, 1, 1));
        this.calcButton.setFont(this.myFont);
        this.frame.add(this.calcButton, constants);

        constants.gridx = 1;
        constants.gridy = 2;
        constants.gridheight = 1;
        this.description2.setFont(this.myFont);
        this.frame.add(this.description2, constants);

        constants.gridx = 2;
        constants.gridy = 2;
        constants.gridheight = 1;
        this.result.setFont(this.myFont);
        this.frame.add(this.result, constants);

        constants.gridx = 4;
        constants.gridy = 2;
        constants.gridheight = 1;
        this.chooseResultScale.setFont(myFont);
        this.chooseResultScale.setSelectedIndex(1);
        this.frame.add(this.chooseResultScale, constants);
    }

    private void initEvents() {
        FrameView.this.editText.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if ((!isItEditButtons(c) &&
                        !isItPossibleToPutMinusHere(c) && !isItPossibleToPutPointHere(c) &&
                        !Character.isDigit(c) ||
                        FrameView.this.editText.getText().length() > FrameView.this.editTextLength)) {
                    JOptionPane.showMessageDialog(FrameView.this.frame, FrameView.this.errorText,
                            "Inane warning", JOptionPane.WARNING_MESSAGE);
                    e.consume();
                } else {
                    FrameView.this.result.setFont(FrameView.this.myFont);
                    FrameView.this.result.setText(FrameView.this.defaultResultText);
                }
            }

            private boolean isItEditButtons(char c) {
                return (c == KeyEvent.VK_DELETE || c == KeyEvent.VK_BACK_SPACE);
            }

            private boolean isItPossibleToPutPointHere(char c) {
                return (c == '-' && FrameView.this.editText.getSelectionStart() == 0 &&
                        !FrameView.this.editText.getText().contains(Character.toString(c)));
            }

            private boolean isItPossibleToPutMinusHere(char c) {
                if (c == ',') {
                    int position = FrameView.this.editText.getSelectionStart();
                    String text = FrameView.this.editText.getText();
                    if (position != 0 && !text.contains(Character.toString(c)) &&
                            Character.isDigit(text.charAt(position - 1))) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        FrameView.this.calcButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = FrameView.this.editText.getText();
                int textLength = text.length();
                if (textLength == 0 || (textLength == 1 && text.contains("-"))) {
                    FrameView.this.result.setText(FrameView.this.errorResultText);
                } else {
                    String userDataText = FrameView.this.editText.getText().replace(',', '.');
                    Double userData = Double.parseDouble(userDataText);
                    for (ViewListener listener : listeners) {
                        listener.needConvertTemperature(userData,
                                FrameView.this.chooseUserScale.getSelectedIndex(),
                                FrameView.this.chooseResultScale.getSelectedIndex());
                    }
                }
            }
        });

        FrameView.this.chooseUserScale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrameView.this.result.setFont(FrameView.this.myFont);
                FrameView.this.result.setText(FrameView.this.defaultResultText);
            }
        });

        FrameView.this.chooseResultScale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrameView.this.result.setFont(FrameView.this.myFont);
                FrameView.this.result.setText(FrameView.this.defaultResultText);
            }
        });

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
                initContent();
                initFrame();
                initEvents();
            }
        });
    }

    @Override
    public void onTemperatureConverted(double resultData) {
        String resultText = String.format("%.2f", resultData);
        FrameView.this.result.setFont(FrameView.this.myFontBig);
        FrameView.this.result.setText(resultText);
    }

    @Override
    public void addViewListener(ViewListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }
}