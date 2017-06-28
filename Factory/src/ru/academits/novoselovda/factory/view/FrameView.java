package ru.academits.novoselovda.factory.view;

import ru.academits.novoselovda.factory.common.Configuration;
import ru.academits.novoselovda.factory.common.ControlListener;
import ru.academits.novoselovda.factory.common.FrameViewListener;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class FrameView implements FrameViewListener {
    private static final String TITLE = "Factory";
    private static final String BUTTON_START_TEXT = "старт";
    private static final String TITLE_OF_COUNT_PANEL = "количество рабочих";
    private static final String TITLE_OF_SPEED_PANEL = "скорость";
    private static final String TITLE_OF_ITEMS_COUNT_PANEL = "произведено";
    private static final String TITLE_OF_ITEMS_COUNT = "всего";
    private static final String TITLE_OF_STORE_COUNT = "на складе";
    private static final String TITLE_OF_TARGET_PANEL = "достижение цели";

    private static final Font MY_TITLE_FONT = new Font("Verdana", Font.BOLD, 14);
    private static final Font MY_TEXT_FONT = new Font("Verdana", Font.PLAIN, 12);

    private static final Color STORE_COLOR = new Color(22, 180, 209);
    private static final Color TARGET_COLOR = new Color(17, 156, 24);

    private static final Border BORDER = BorderFactory.createLineBorder(Color.GRAY);

    private static final int FRAME_WIDTH = 400;
    private static final int FRAME_HEIGHT = 620;
    private static final int SIZE = FRAME_WIDTH / 6 - 25;

    private static final int MIN_SPEED = 1;
    private static final int MAX_SPEED = 10;

    private int y;
    private int x;
    private int tableWidth;

    private JFrame frame;

    private GridBagConstraints constants = new GridBagConstraints();

    private JLabel providersOfPartsACount = new JLabel();
    private JLabel providersOfPartsBCount = new JLabel();
    private JLabel providersOfPartsCCount = new JLabel();
    private JLabel producersCount = new JLabel();
    private JLabel dealersCount = new JLabel();
    private JLabel controllerCount = new JLabel();

    private JSlider providersOfPartsASpeed = new JSlider();
    private JSlider providersOfPartsBSpeed = new JSlider();
    private JSlider providersOfPartsCSpeed = new JSlider();
    private JSlider producersSpeed = new JSlider();
    private JSlider dealersSpeed = new JSlider();
    private JSlider controllerSpeed = new JSlider();

    private JLabel providersOfPartsASpeedValue = new JLabel();
    private JLabel providersOfPartsBSpeedValue = new JLabel();
    private JLabel providersOfPartsCSpeedValue = new JLabel();
    private JLabel producersSpeedValue = new JLabel();
    private JLabel dealersSpeedValue = new JLabel();
    private JLabel controllerSpeedValue = new JLabel();

    private JLabel partsACount = new JLabel();
    private JLabel partsBCount = new JLabel();
    private JLabel partsCCount = new JLabel();
    private JLabel carsCount = new JLabel();

    private JProgressBar partsAProgressBar = new JProgressBar();
    private JProgressBar partsBProgressBar = new JProgressBar();
    private JProgressBar partsCProgressBar = new JProgressBar();
    private JProgressBar carsProgressBar = new JProgressBar();
    private JProgressBar targetProgressBar = new JProgressBar();

    private ControlListener controlListener;
    private IconManager iconManager;


    public FrameView() {
        iconManager = new IconManager(SIZE);
    }

    public void startApplication() {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            initFrame();
            initContent();
            initData();
        });
    }

    private void initFrame() {
        frame = new JFrame();
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setTitle(TITLE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setIconImage(this.iconManager.getIcon());
        frame.setVisible(true);
    }

    private void initContent() {
        frame.setLayout(new GridBagLayout());
        constants.weightx = 1;
        constants.gridheight = 1;
        constants.fill = GridBagConstraints.BOTH;

        constants.gridy = y;
        constants.gridx = 0;
        constants.weighty = 0.1;
        constants.gridwidth = tableWidth;
        JPanel panel = new JPanel(new GridLayout());
        addButton(BUTTON_START_TEXT, e -> controlListener.start(), panel);
        frame.add(panel, constants);

        ImageIcon partAIcon = iconManager.getAIcon();
        ImageIcon partBIcon = iconManager.getBIcon();
        ImageIcon partCIcon = iconManager.getCIcon();

        setLabel(partAIcon, true);
        setLabel(partBIcon, false);
        setLabel(partCIcon, false);
        setLabel(iconManager.getProducersIcon(), false);
        setLabel(iconManager.getDealersIcon(), false);
        setLabel(iconManager.getControllerIcon(), false);

        setTitle(TITLE_OF_COUNT_PANEL);

        setLabel(providersOfPartsACount, true);
        setLabel(providersOfPartsBCount, false);
        setLabel(providersOfPartsCCount, false);
        setLabel(producersCount, false);
        setLabel(dealersCount, false);
        setLabel(controllerCount, false);

        setTitle(TITLE_OF_SPEED_PANEL);

        setSlider(providersOfPartsASpeed, true);
        providersOfPartsASpeed.addChangeListener(e -> {
            int speed = providersOfPartsASpeed.getValue();
            providersOfPartsASpeedValue.setText("" + speed);
            controlListener.changeProvidersOfPartsASpeed(speed);
        });
        setSlider(providersOfPartsBSpeed, false);
        providersOfPartsBSpeed.addChangeListener(e -> {
            int speed = providersOfPartsBSpeed.getValue();
            providersOfPartsBSpeedValue.setText("" + speed);
            controlListener.changeProvidersOfPartsBSpeed(speed);
        });
        setSlider(providersOfPartsCSpeed, false);
        providersOfPartsCSpeed.addChangeListener(e -> {
            int speed = providersOfPartsCSpeed.getValue();
            providersOfPartsCSpeedValue.setText("" + speed);
            controlListener.changeProvidersOfPartsCSpeed(speed);
        });
        setSlider(producersSpeed, false);
        producersSpeed.addChangeListener(e -> {
            int speed = producersSpeed.getValue();
            producersSpeedValue.setText("" + speed);
            controlListener.changeProducersSpeed(speed);
        });
        setSlider(dealersSpeed, false);
        dealersSpeed.addChangeListener(e -> {
            int speed = dealersSpeed.getValue();
            dealersSpeedValue.setText("" + speed);
            controlListener.changeDealersSpeed(speed);
        });
        setSlider(controllerSpeed, false);
        controllerSpeed.addChangeListener(e -> {
            int speed = controllerSpeed.getValue();
            controllerSpeedValue.setText("" + speed);
            controlListener.changeControllerSpeed(speed);
        });

        setLabel(providersOfPartsASpeedValue, true);
        setLabel(providersOfPartsBSpeedValue, false);
        setLabel(providersOfPartsCSpeedValue, false);
        setLabel(producersSpeedValue, false);
        setLabel(dealersSpeedValue, false);
        setLabel(controllerSpeedValue, false);

        setTitle(TITLE_OF_ITEMS_COUNT_PANEL);

        setLabel(new JLabel(), true);
        setTitle(TITLE_OF_ITEMS_COUNT, false, 1);
        setTitle(TITLE_OF_STORE_COUNT, false, tableWidth - 2);

        setLabel(partAIcon, true);
        setLabel(partsACount, false);
        setProgressBar(partsAProgressBar, STORE_COLOR);

        setLabel(partBIcon, true);
        setLabel(partsBCount, false);
        setProgressBar(partsBProgressBar, STORE_COLOR);

        setLabel(partCIcon, true);
        setLabel(partsCCount, false);
        setProgressBar(partsCProgressBar, STORE_COLOR);

        setLabel(iconManager.getCarIcon(), true);
        setLabel(carsCount, false);
        setProgressBar(carsProgressBar, STORE_COLOR);

        setTitle(TITLE_OF_TARGET_PANEL);
        setLabel(iconManager.getTargetIcon(), true);
        setProgressBar(targetProgressBar, TARGET_COLOR);
    }

    private void initData() {
        controlListener.getOptions();
    }

    private void setSlider(JSlider slider, boolean isFirstColumn) {
        if (isFirstColumn) {
            x = 0;
            ++y;
        } else {
            ++x;
        }
        if (tableWidth < x + 1) {
            tableWidth = x + 1;
        }
        slider.setOrientation(SwingConstants.VERTICAL);
        slider.setMinimum(MIN_SPEED);
        slider.setMaximum(MAX_SPEED);
        slider.setValue(MIN_SPEED);
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setBorder(BORDER);
        constants.gridy = y;
        constants.gridx = x;
        constants.weighty = 1;
        constants.gridwidth = 1;
        frame.add(slider, constants);
    }

    private void setLabel(ImageIcon imageIcon, boolean isFirstColumn) {
        setLabel(null, 1, isFirstColumn, imageIcon, null);
    }

    private void setLabel(JLabel label, boolean isFirstColumn) {
        setLabel(label, 1, isFirstColumn, null, MY_TEXT_FONT);
    }

    private void setTitle(String text, boolean isFirstColumn, int width) {
        JLabel label = new JLabel(text);
        setLabel(label, width, isFirstColumn, null, MY_TITLE_FONT);
    }

    private void setTitle(String text) {
        ++y;
        JLabel label = new JLabel(text);
        constants.gridwidth = tableWidth;
        setLabel(label, tableWidth, true, null, MY_TITLE_FONT);
    }

    private void setLabel(JLabel label, int gridWidth, boolean isFirstColumn, ImageIcon imageIcon, Font font) {
        if (isFirstColumn) {
            x = 0;
            ++y;
        } else {
            ++x;
        }
        if (tableWidth < x + 1) {
            tableWidth = x + 1;
        }
        if (label == null) {
            label = new JLabel();
        }
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(BORDER);
        if (imageIcon != null) {
            label.setIcon(imageIcon);
        } else {
            label.setFont(font);
        }
        constants.gridy = y;
        constants.gridx = x;
        constants.weighty = 0.1;
        constants.gridwidth = gridWidth;
        frame.add(label, constants);
    }

    private void setProgressBar(JProgressBar progressBar, Color color) {
        ++x;
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout());
        panel.setBorder(BORDER);
        panel.add(progressBar);
        progressBar.setForeground(color);
        progressBar.setFont(MY_TEXT_FONT);
        progressBar.setStringPainted(true);
        progressBar.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        constants.gridy = y;
        constants.gridx = x;
        constants.weighty = 0.1;
        constants.gridwidth = tableWidth - x;
        frame.add(panel, constants);
    }

    private void addButton(String buttonText, ActionListener actionListener, JPanel panel) {
        JButton button = new JButton(buttonText);
        button.setFont(MY_TEXT_FONT);
        button.setBorder(BorderFactory.createEmptyBorder(1, -5, 1, -5));
        button.addActionListener(actionListener);
        panel.add(button);

    }

    @Override
    public void setStartOptions(HashMap<Configuration, Integer> options) {
        providersOfPartsACount.setText("" + options.get(Configuration.BODY_PROVIDERS_COUNT));
        providersOfPartsBCount.setText("" + options.get(Configuration.ENGINE_PROVIDERS_COUNT));
        providersOfPartsCCount.setText("" + options.get(Configuration.ACCESSORIES_PROVIDERS_COUNT));
        producersCount.setText("" + options.get(Configuration.PRODUCERS_COUNT));
        dealersCount.setText("" + options.get(Configuration.DEALERS_COUNT));
        controllerCount.setText("" + options.get(Configuration.CONTROLLERS_COUNT));

        int partsAStorageCapacity = options.get(Configuration.BODY_STORAGE_SIZE);
        int partsBStorageCapacity = options.get(Configuration.ENGINE_STORAGE_SIZE);
        int partsCStorageCapacity = options.get(Configuration.ACCESSORIES_STORAGE_SIZE);
        int carsStorageCapacity = options.get(Configuration.CAR_STORAGE_SIZE);
        int targetValue = options.get(Configuration.TARGET_VALUE);

        setProgressBarMaximum(partsAProgressBar, partsAStorageCapacity);
        setProgressBarMaximum(partsBProgressBar, partsBStorageCapacity);
        setProgressBarMaximum(partsCProgressBar, partsCStorageCapacity);
        setProgressBarMaximum(carsProgressBar, carsStorageCapacity);
        setProgressBarMaximum(targetProgressBar, targetValue);

        providersOfPartsASpeed.setValue(options.get(Configuration.BODY_PROVIDERS_SPEED));
        providersOfPartsBSpeed.setValue(options.get(Configuration.ENGINE_PROVIDERS_SPEED));
        providersOfPartsCSpeed.setValue(options.get(Configuration.ACCESSORIES_PROVIDERS_SPEED));
        producersSpeed.setValue(options.get(Configuration.PRODUCERS_SPEED));
        dealersSpeed.setValue(options.get(Configuration.DEALERS_SPEED));
        controllerSpeed.setValue(options.get(Configuration.CONTROLLERS_SPEED));

        providersOfPartsASpeedValue.setText("" + options.get(Configuration.BODY_PROVIDERS_SPEED));
        providersOfPartsBSpeedValue.setText("" + options.get(Configuration.ENGINE_PROVIDERS_SPEED));
        providersOfPartsCSpeedValue.setText("" + options.get(Configuration.ACCESSORIES_PROVIDERS_SPEED));
        producersSpeedValue.setText("" + options.get(Configuration.PRODUCERS_SPEED));
        dealersSpeedValue.setText("" + options.get(Configuration.DEALERS_SPEED));
        controllerSpeedValue.setText("" + options.get(Configuration.CONTROLLERS_SPEED));
    }

    @Override
    public void updatePartsACount(int storageCount, int producedCount) {
        updateProgressBar(partsAProgressBar, storageCount);
        partsACount.setText("" + producedCount);
    }

    @Override
    public void updatePartsBCount(int storageCount, int producedCount) {
        updateProgressBar(partsBProgressBar, storageCount);
        partsBCount.setText("" + producedCount);
    }

    @Override
    public void updatePartsCCount(int storageCount, int producedCount) {
        updateProgressBar(partsCProgressBar, storageCount);
        partsCCount.setText("" + producedCount);

    }

    @Override
    public void updateCarsCount(int storageCount, int producedCount) {
        updateProgressBar(carsProgressBar, storageCount);
        carsCount.setText("" + producedCount);
    }

    @Override
    public void updateTarget(int value) {
        updateProgressBar(targetProgressBar, value);
    }

    private void setProgressBarMaximum(JProgressBar progressBar, int maxValue) {
        progressBar.setMaximum(maxValue);
        progressBar.setValue(0);
        progressBar.setString(String.format("%d из %d", 0, maxValue));
    }

    private void updateProgressBar(JProgressBar progressBar, int value) {
        progressBar.setValue(value);
        int maxValue = progressBar.getMaximum();
        progressBar.setString(String.format("%d из %d", value, maxValue));
    }

    public void addControlListener(ControlListener controlListener) {
        this.controlListener = controlListener;
    }
}
