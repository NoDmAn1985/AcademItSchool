package ru.academits.novoselovda.temperature.model;

import ru.academits.novoselovda.temperature.interfaces.Convertible;

public class Kelvin implements Convertible {
    private static final String NAME = "Гр. кельвина";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public double convertTo(double degreesCelsius) {
        final double kelvin = 273.15;
        return degreesCelsius + kelvin;
    }

    @Override
    public double getDegreesCelsius(double degrees) {
        final double kelvin = 273.15;
        return degrees - kelvin;
    }
}
