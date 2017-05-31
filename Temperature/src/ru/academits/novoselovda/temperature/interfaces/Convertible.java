package ru.academits.novoselovda.temperature.interfaces;

public interface Convertible {
    String getName();
    double convertTo(double degreesCelsius);
    double getDegreesCelsius(double degrees);
}
