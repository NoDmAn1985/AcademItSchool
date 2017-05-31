package ru.academits.novoselovda.temperature.interfaces;

public interface ViewListener {
    void needConvertTemperature(double inputData, String inputScale, String resultScale);

    Object[] needGetScalesList();
}
