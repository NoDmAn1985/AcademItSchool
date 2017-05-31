package ru.academits.novoselovda.temperature.model;

import ru.academits.novoselovda.temperature.interfaces.Convertible;

public class Fahrenheit implements Convertible {
    private static final String NAME = "Гр. фаренгейта";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public double convertTo(double degreesCelsius) {
        final double fahrenheitRate = 9.0 / 5;
        final double fahrenheitZero = 32;
        return degreesCelsius * fahrenheitRate + fahrenheitZero;
    }

    @Override
    public double getDegreesCelsius(double degrees) {
        final double fahrenheitRate = 9.0 / 5;
        final double fahrenheitZero = 32;
        return (degrees - fahrenheitZero) / fahrenheitRate;
    }
}
