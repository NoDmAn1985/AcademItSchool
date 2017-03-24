package ru.academits.novoselovda.main;

import ru.academits.novoselovda.csv.Csv;

public class Main {
    public static void main(String[] args) {
        Csv csv = new Csv();
        csv.fromCsvToHtml("in.csv", "out.html");
    }
}
