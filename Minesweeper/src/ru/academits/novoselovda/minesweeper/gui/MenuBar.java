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

public class MenuBar extends JMenuBar {
    private final String menuNewGameName = "Меню";
    private final String restartGameMenuName = "Перезапустить игру";
    private final String newBeginnerGameMenuName = "Новая игра (новичок)";
    private final String newAmateurGameMenuName = "Новая игра (любитель)";
    private final String newProfessionalGameMenuName = "Новая игра (профессионал)";
    private final String setFieldMenuName = "Новая игра...";
    private final String menuHighScoresName = "Рекорды";
    private final String menuChangeName = "Сменить имя";
    private final String menuAboutName = "О программе";
    private final String menuExitName = "Выход";
    private final Insets myInsetsMenu = new Insets(0, 5, 0, 5);
    private final Insets myInsetsItem = new Insets(5, -25, 5, 5);
    private final Font myFontMenu = new Font("Verdana", Font.BOLD, 14);
    private final Font myFontItem = new Font("Verdana", Font.PLAIN, 14);

    private final int beginnerYCellsCount = 9;
    private final int beginnerXCellsCount = 9;
    private final int beginnerMinesCount = 10;

    private final int amateurYCellsCount = 16;
    private final int amateurXCellsCount = 16;
    private final int amateurMinesCount = 40;

    private final int professionalYCellsCount = 16;
    private final int professionalXCellsCount = 30;
    private final int professionalMinesCount = 99;

    private HighScore highScore;
    private FrameListener frameListener;

    MenuBar(HighScore highScore) {
        this.highScore = highScore;
    }

    void init() {
        JMenu menuNewGame = new JMenu(this.menuNewGameName);
        setPropertiesAndAddToMenuBar(menuNewGame);

        JMenuItem restartGame = new JMenuItem(this.restartGameMenuName);
        restartGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuBar.this.frameListener.needRestart();
            }
        });
        setPropertiesAndAddToMenu(menuNewGame, restartGame);

        menuNewGame.addSeparator();

        JMenuItem setField = new JMenuItem(this.setFieldMenuName);
        setField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JMenuItem menuItem = (JMenuItem) e.getSource();
                JPopupMenu popupMenu = (JPopupMenu) menuItem.getParent();
                Component invoker = popupMenu.getInvoker();
                JComponent invokerAsJComponent = (JComponent) invoker;
                Container topLevel = invokerAsJComponent.getTopLevelAncestor();
                topLevel.setEnabled(false);
                new SetField(MenuBar.this.beginnerYCellsCount, MenuBar.this.beginnerXCellsCount,
                        MenuBar.this.beginnerMinesCount).showIt(MenuBar.this.frameListener);
            }
        });
        setPropertiesAndAddToMenu(menuNewGame, setField);

        menuNewGame.addSeparator();

        JMenuItem newBeginnerGame = new JMenuItem(this.newBeginnerGameMenuName);
        setPropertiesAndAddToMenu(menuNewGame, newBeginnerGame);
        newBeginnerGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuBar.this.frameListener.needStartNewGame(beginnerYCellsCount, beginnerXCellsCount, beginnerMinesCount);
            }
        });

        JMenuItem newAmateurGame = new JMenuItem(this.newAmateurGameMenuName);
        setPropertiesAndAddToMenu(menuNewGame, newAmateurGame);
        newAmateurGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuBar.this.frameListener.needStartNewGame(amateurYCellsCount, amateurXCellsCount, amateurMinesCount);
            }
        });

        JMenuItem newProfessionalGame = new JMenuItem(this.newProfessionalGameMenuName);
        setPropertiesAndAddToMenu(menuNewGame, newProfessionalGame);
        newProfessionalGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuBar.this.frameListener.
                        needStartNewGame(professionalYCellsCount, professionalXCellsCount, professionalMinesCount);
            }
        });

        menuNewGame.addSeparator();

        JMenuItem menuHighScores = new JMenuItem(this.menuHighScoresName);
        setPropertiesAndAddToMenu(menuNewGame, menuHighScores);
        menuHighScores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JMenuItem menuItem = (JMenuItem) e.getSource();
                JPopupMenu popupMenu = (JPopupMenu) menuItem.getParent();
                Component invoker = popupMenu.getInvoker();
                JComponent invokerAsJComponent = (JComponent) invoker;
                Container topLevel = invokerAsJComponent.getTopLevelAncestor();
                JOptionPane.showMessageDialog(topLevel, MenuBar.this.highScore.show(),
                        MenuBar.this.menuHighScoresName, JOptionPane.PLAIN_MESSAGE);
            }
        });

        JMenuItem menuChange = new JMenuItem();
        menuChange.setText(this.menuChangeName + " (" + MenuBar.this.highScore.getUserName() +  ")");
        setPropertiesAndAddToMenu(menuNewGame, menuChange);
        menuChange.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JMenuItem menuItem = (JMenuItem) e.getSource();
                JPopupMenu popupMenu = (JPopupMenu) menuItem.getParent();
                Component invoker = popupMenu.getInvoker();
                JComponent invokerAsJComponent = (JComponent) invoker;
                Container topLevel = invokerAsJComponent.getTopLevelAncestor();
                MenuBar.this.highScore.setUserName(JOptionPane.showInputDialog(MenuBar.this.getParent(),
                        "Введите своё имя:","Смена имени", JOptionPane.QUESTION_MESSAGE, null,
                        null, "Anonymous").toString());
                menuChange.setText(MenuBar.this.menuChangeName + " (" + MenuBar.this.highScore.getUserName() +  ")");
            }
        });

        JMenu menuAbout = new JMenu(menuAboutName);
        setPropertiesAndAddToMenuBar(menuAbout);
        menuAbout.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showAbout();
            }
        });

        add(Box.createHorizontalGlue());

        JMenu menuExit = new JMenu(menuExitName);
        setPropertiesAndAddToMenuBar(menuExit);
        menuExit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
    }

    private void setPropertiesAndAddToMenuBar(JMenu menu) {
        menu.setFont(myFontMenu);
        menu.setMargin(myInsetsMenu);
        this.add(menu);
    }

    private void setPropertiesAndAddToMenu(JMenu menu, JMenuItem item) {
        item.setFont(myFontItem);
        item.setMargin(myInsetsItem);
        menu.add(item);
    }

    void addListener(FrameListener frameListener) {
        this.frameListener = frameListener;
    }

    private void showAbout() {
        JTextPane text = new JTextPane();
        text.setText(new About().toString());
        text.setFont(this.myFontMenu);

        StyledDocument doc = text.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        text.setBackground(Color.BLUE);
        text.setForeground(Color.WHITE);

        text.setEditable(false);
        JOptionPane.showMessageDialog(MenuBar.this.getParent(), text, this.menuAboutName,
                JOptionPane.PLAIN_MESSAGE);
    }
}
