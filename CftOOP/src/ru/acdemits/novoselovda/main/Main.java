package ru.acdemits.novoselovda.main;

import ru.academits.novoselovda.cft.Cft;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            Cft cft = new Cft(args);
            cft.sort();
        } catch (IllegalArgumentException illegalException) {
            illegalException.getMessage();
        } catch (FileNotFoundException exception) {
            exception.getMessage();
        } catch (IOException exception2) {
            exception2.getMessage();
        }
    }
}
