package ru.academits.novoselovda.temperature.model;

import ru.academits.novoselovda.temperature.interfaces.TempConverter;

public class FahrenheitToKelvin implements TempConverter {
    @Override
    public double convert(double inputTemperature) {
        return new CelsiusToKelvin().convert(new FahrenheitToCelsius().convert(inputTemperature));
    }
}

