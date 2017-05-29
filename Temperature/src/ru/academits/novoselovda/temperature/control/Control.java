package ru.academits.novoselovda.temperature.control;

import ru.academits.novoselovda.temperature.interfaces.TempConverter;
import ru.academits.novoselovda.temperature.interfaces.View;
import ru.academits.novoselovda.temperature.interfaces.ViewListener;
import ru.academits.novoselovda.temperature.model.*;

import java.util.Map;
import java.util.TreeMap;

public class Control implements ViewListener {
    private static final String[] LIST = {"Гр. цельсия", "Гр. фаренгейта", "Гр. кельвина"};
    private View view;
    private Map<String, TempConverter> converters;

    public Control(View view) {
        this.view = view;
        this.converters = new TreeMap<>();

        this.converters.put(0 + "/" + 0, new NoConvert());
        this.converters.put(0 + "/" + 1, new CelsiusToFahrenheit());
        this.converters.put(0 + "/" + 2, new CelsiusToKelvin());

        this.converters.put(1 + "/" + 0, new FahrenheitToCelsius());
        this.converters.put(1 + "/" + 1, new NoConvert());
        this.converters.put(1 + "/" + 2, new FahrenheitToKelvin());

        this.converters.put(2 + "/" + 0, new KelvinToCelsius());
        this.converters.put(2 + "/" + 1, new KelvinToFahrenheit());
        this.converters.put(2 + "/" + 2, new NoConvert());
    }

    public static String[] getListOfScales() {
        return LIST;
    }

    @Override
    public void needConvertTemperature(double userData, int userScale, int resultScale) {
        view.onTemperatureConverted(this.converters.get(userScale + "/" + resultScale).convert(userData));
    }
}
