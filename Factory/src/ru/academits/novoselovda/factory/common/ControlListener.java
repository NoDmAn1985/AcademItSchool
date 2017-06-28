package ru.academits.novoselovda.factory.common;

public interface ControlListener {
    void getOptions();

    void start();

    void clear();

    void changeProvidersOfPartsASpeed(int value);

    void changeProvidersOfPartsBSpeed(int value);

    void changeProvidersOfPartsCSpeed(int value);

    void changeProducersSpeed(int value);

    void changeDealersSpeed(int value);

    void changeControllerSpeed(int value);
}
