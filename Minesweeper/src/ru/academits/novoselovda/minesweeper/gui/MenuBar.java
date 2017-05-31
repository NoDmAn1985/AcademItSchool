package ru.academits.novoselovda.minesweeper.gui;

import ru.academits.novoselovda.minesweeper.common.FrameListener;
import ru.academits.novoselovda.minesweeper.model.About;
import ru.academits.novoselovda.minesweeper.model.HighScore;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.*;

class MenuBar extends JMenuBar {
    private static final String MENU_NEW_GAME_NAME = "Меню";
    private static final String RESTART_GAME_MENU_NAME = "Перезапустить игру";
    private static final String NEW_BEGINNER_GAME_MENU_NAME = "Новая игра (новичок)";
    private static final String NEW_AMATEUR_GAME_MENU_NAME = "Новая игра (любитель)";
    private static final String NEW_PROFESSIONAL_GAME_MENU_NAME = "Новая игра (профессионал)";
    private static final String SET_FIELD_MENU_NAME = "Новая игра...";
    private static final String MENU_HIGH_SCORES_NAME = "Рекорды";
    private static final String MENU_CHANGE_NAME = "Сменить имя";
    private static final String MENU_ABOUT_NAME = "О программе";
    private static final String MENU_EXIT_NAME = "Выход";

    private static final String RESTART_ICON_PATH = ".\\Minesweeper\\src\\ru\\academits\\novoselovda\\minesweeper\\resources\\restart.png";
    private static final String SET_FIELD_ICON_PATH = ".\\Minesweeper\\src\\ru\\academits\\novoselovda\\minesweeper\\resources\\options.png";
    private static final String BEGINNER_ICON_PATH = ".\\Minesweeper\\src\\ru\\academits\\novoselovda\\minesweeper\\resources\\face_clicked.png";
    private static final String AMATEUR_ICON_PATH = ".\\Minesweeper\\src\\ru\\academits\\novoselovda\\minesweeper\\resources\\face_default.png";
    private static final String PROFESSIONAL_ICON_PATH = ".\\Minesweeper\\src\\ru\\academits\\novoselovda\\minesweeper\\resources\\face_pressed.png";
    private static final String RECORDS_ICON_PATH = ".\\Minesweeper\\src\\ru\\academits\\novoselovda\\minesweeper\\resources\\face_win.png";
    private static final String CHANGE_ICON_PATH = ".\\Minesweeper\\src\\ru\\academits\\novoselovda\\minesweeper\\resources\\change.png";

    private static final int SIZE = 30;

    private static final Insets MY_INSETS_MENU = new Insets(0, 5, 0, 5);
    private static final Insets MY_INSETS_ITEM = new Insets(0, 0, 0, 0);
    private static final Font MY_FONT_MENU = new Font("Verdana", Font.BOLD, 14);
    private static final Font MY_FONT_ITEM = new Font("Verdana", Font.PLAIN, 14);

    private static final int BEGINNER_Y_CELLS_COUNT = 9;
    private static final int BEGINNER_X_CELLS_COUNT = 9;
    private static final int BEGINNER_MINES_COUNT = 10;

    private static final int AMATEUR_Y_CELLS_COUNT = 16;
    private static final int AMATEUR_X_CELLS_COUNT = 16;
    private static final int AMATEUR_MINES_COUNT = 40;

    private static final int PROFESSIONAL_Y_CELLS_COUNT = 16;
    private static final int PROFESSIONAL_X_CELLS_COUNT = 30;
    private static final int PROFESSIONAL_MINES_COUNT = 99;

    private HighScore highScore;
    private FrameListener frameListener;

    MenuBar(HighScore highScore) {
        this.highScore = highScore;
    }

    void init() {
        JMenu menuNewGame = new JMenu(MENU_NEW_GAME_NAME, false);
        setPropertiesAndAddToMenuBar(menuNewGame);

        JMenuItem restartGame = new JMenuItem(RESTART_GAME_MENU_NAME);
        restartGame.addActionListener(e -> MenuBar.this.frameListener.needRestart());
        setPropertiesAndAddToMenu(menuNewGame, restartGame, RESTART_ICON_PATH);

        menuNewGame.addSeparator();

        JMenuItem setField = new JMenuItem(SET_FIELD_MENU_NAME);
        setField.addActionListener(e -> {
            JMenuItem menuItem = (JMenuItem) e.getSource();
            JPopupMenu popupMenu = (JPopupMenu) menuItem.getParent();
            Component invoker = popupMenu.getInvoker();
            JComponent invokerAsJComponent = (JComponent) invoker;
            Container topLevel = invokerAsJComponent.getTopLevelAncestor();
            topLevel.setEnabled(false);
            new SetField(topLevel, BEGINNER_Y_CELLS_COUNT, BEGINNER_X_CELLS_COUNT,
                    BEGINNER_MINES_COUNT).showIt(MenuBar.this.frameListener);
        });
        setPropertiesAndAddToMenu(menuNewGame, setField, SET_FIELD_ICON_PATH);

        menuNewGame.addSeparator();

        JMenuItem newBeginnerGame = new JMenuItem(NEW_BEGINNER_GAME_MENU_NAME);
        setPropertiesAndAddToMenu(menuNewGame, newBeginnerGame, BEGINNER_ICON_PATH);
        newBeginnerGame.addActionListener(e -> MenuBar.this.frameListener.needStartNewGame(BEGINNER_Y_CELLS_COUNT, BEGINNER_X_CELLS_COUNT,
                BEGINNER_MINES_COUNT));

        JMenuItem newAmateurGame = new JMenuItem(NEW_AMATEUR_GAME_MENU_NAME);
        setPropertiesAndAddToMenu(menuNewGame, newAmateurGame, AMATEUR_ICON_PATH);
        newAmateurGame.addActionListener(e -> MenuBar.this.frameListener.needStartNewGame(AMATEUR_Y_CELLS_COUNT, AMATEUR_X_CELLS_COUNT,
                AMATEUR_MINES_COUNT));

        JMenuItem newProfessionalGame = new JMenuItem(NEW_PROFESSIONAL_GAME_MENU_NAME);
        setPropertiesAndAddToMenu(menuNewGame, newProfessionalGame, PROFESSIONAL_ICON_PATH);
        newProfessionalGame.addActionListener(e -> MenuBar.this.frameListener.
                needStartNewGame(PROFESSIONAL_Y_CELLS_COUNT, PROFESSIONAL_X_CELLS_COUNT,
                        PROFESSIONAL_MINES_COUNT));

        menuNewGame.addSeparator();

        JMenuItem menuHighScores = new JMenuItem(MENU_HIGH_SCORES_NAME);
        setPropertiesAndAddToMenu(menuNewGame, menuHighScores, RECORDS_ICON_PATH);
        menuHighScores.addActionListener(e -> {
            JMenuItem menuItem = (JMenuItem) e.getSource();
            JPopupMenu popupMenu = (JPopupMenu) menuItem.getParent();
            Component invoker = popupMenu.getInvoker();
            JComponent invokerAsJComponent = (JComponent) invoker;
            Container topLevel = invokerAsJComponent.getTopLevelAncestor();
            JOptionPane.showMessageDialog(topLevel, MenuBar.this.highScore.show(),
                    MENU_HIGH_SCORES_NAME, JOptionPane.PLAIN_MESSAGE);
        });

        JMenuItem menuChange = new JMenuItem();
        menuChange.setText(MENU_CHANGE_NAME + " (" + MenuBar.this.highScore.getUserName() + ")");
        setPropertiesAndAddToMenu(menuNewGame, menuChange, CHANGE_ICON_PATH);
        menuChange.addActionListener(e -> {
            Object newName = JOptionPane.showInputDialog(MenuBar.this.getParent(),
                    "Введите своё имя:", "Смена имени", JOptionPane.QUESTION_MESSAGE, null,
                    null, "Anonymous");
            if (newName != null) {
                MenuBar.this.highScore.setUserName((String) newName);
                menuChange.setText(MENU_CHANGE_NAME + " (" + MenuBar.this.highScore.getUserName() + ")");
            }
        });

        JMenu menuAbout = new JMenu(MENU_ABOUT_NAME);
        setPropertiesAndAddToMenuBar(menuAbout);
        menuAbout.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SwingUtilities.invokeLater(() -> {
                    try {
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    } catch (Exception e1) {
                        System.out.println(e1.getMessage());
                    }
                    showAbout();
                });
            }
        });

        add(Box.createHorizontalGlue());

        JMenu menuExit = new JMenu(MENU_EXIT_NAME);
        setPropertiesAndAddToMenuBar(menuExit);
        menuExit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
    }

    private void setPropertiesAndAddToMenuBar(JMenu menu) {
        menu.setFont(MY_FONT_MENU);
        menu.setMargin(MY_INSETS_MENU);
        this.add(menu);
    }

    private void setPropertiesAndAddToMenu(JMenu menu, JMenuItem item, String iconPath) {
        item.setFont(MY_FONT_ITEM);
        item.setMargin(MY_INSETS_ITEM);
        Image image = new ImageIcon(iconPath).getImage();
        Image newImage = image.getScaledInstance(SIZE, SIZE, java.awt.Image.SCALE_SMOOTH);
        item.setIcon(new ImageIcon(newImage));
        menu.add(item);
    }

    void addListener(FrameListener frameListener) {
        this.frameListener = frameListener;
    }

    private void showAbout() {
        JTextPane text = new JTextPane();
        text.setText(new About().toString());
        text.setFont(MY_FONT_MENU);

        StyledDocument doc = text.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        text.setBackground(Color.BLUE);
        text.setForeground(Color.WHITE);

        text.setEditable(false);
        JOptionPane.showMessageDialog(MenuBar.this.getParent(), text, MENU_ABOUT_NAME,
                JOptionPane.PLAIN_MESSAGE);
    }
}
