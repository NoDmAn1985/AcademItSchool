package ru.academits.novoselovda.temperature.model;

import ru.academits.novoselovda.temperature.interfaces.TempConverter;

public class KelvinToFahrenheit implements TempConverter {
    @Override
    public double convert(double inputTemperature) {
        return new CelsiusToFahrenheit().convert(new KelvinToCelsius().convert(inputTemperature));
    }
}

