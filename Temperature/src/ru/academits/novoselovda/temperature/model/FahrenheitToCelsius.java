package ru.academits.novoselovda.temperature.model;

import ru.academits.novoselovda.temperature.interfaces.TempConverter;

public class FahrenheitToCelsius implements TempConverter {
    @Override
    public double convert(double userData) {
        final double fahrenheitRate = 9.0 / 5;
        final double fahrenheitZero = 32;
        return (userData - fahrenheitZero) / fahrenheitRate;
    }
}

