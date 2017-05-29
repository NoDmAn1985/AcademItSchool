package ru.academits.novoselovda.minesweeper.model;

public class About {
    private String text = "Игра Сапёр (Minesweeper)\n" +
            "разработана\n" +
            "Новоселовым Дмитрием\n" +
            "(nodman@mail.ru)\n" +
            "в мае 2017 года";

    public String toString() {
        return this.text;
    }
}
