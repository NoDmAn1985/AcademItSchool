package ru.academits.novoselovda.temperature.interfaces;

public interface View {
    void startApplication();

    void onTemperatureConverted(double resultData);

    void addViewListener(ViewListener listener);
}
