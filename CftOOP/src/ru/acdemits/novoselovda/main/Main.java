package ru.acdemits.novoselovda.main;

import ru.academits.novoselovda.cft.Cft;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            Cft cft = new Cft(args);
            cft.sort();
        } catch (IOException | IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }

    }
}
