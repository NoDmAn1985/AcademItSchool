package ru.academits.novoselovda.temperature.model;

import ru.academits.novoselovda.temperature.interfaces.Convertible;

public class Celsius implements Convertible{
    private static final String NAME = "Гр. цельсия";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public double convertTo(double degreesCelsius) {
        return degreesCelsius;
    }

    @Override
    public double getDegreesCelsius(double degrees) {
        return degrees;
    }
}
