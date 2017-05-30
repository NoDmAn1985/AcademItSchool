package ru.academits.novoselovda.temperature.model;

import ru.academits.novoselovda.temperature.interfaces.TempConverter;

public class CelsiusToKelvin implements TempConverter {
    @Override
    public double convert(double inputTemperature) {
        final double kelvin = 273.15;
        return inputTemperature + kelvin;
    }
}

