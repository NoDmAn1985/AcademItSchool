package ru.academits.novoselovda.temperature.model;

import ru.academits.novoselovda.temperature.interfaces.TempConverter;

public class NoConvert implements TempConverter {
    @Override
    public double convert(double userData) {
        return userData;
    }
}
