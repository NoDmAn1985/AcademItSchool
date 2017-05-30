package ru.academits.novoselovda.temperature.interfaces;

public interface Scales {

    Object[] getListOfTargetScales();

    double convertTo(Object targetScale, double inputTemperature);
}
