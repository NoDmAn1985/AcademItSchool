package ru.academits.novoselovda.temperature.control;

import ru.academits.novoselovda.temperature.interfaces.Convertible;
import ru.academits.novoselovda.temperature.interfaces.View;
import ru.academits.novoselovda.temperature.interfaces.ViewListener;
import ru.academits.novoselovda.temperature.model.*;

import java.util.Map;
import java.util.TreeMap;

public class Control implements ViewListener {
    private View view;
    private Map<String, Convertible> scales;

    public Control(View view) {
        this.view = view;
        this.scales = new TreeMap<>();
        Convertible scale = new Celsius();
        this.scales.put(scale.getName(), scale);
        scale = new Kelvin();
        this.scales.put(scale.getName(), scale);
        scale = new Fahrenheit();
        this.scales.put(scale.getName(), scale);
    }

    @Override
    public void needConvertTemperature(double inputTemperature, String inputScale, String resultScale) {
        view.onTemperatureConverted(this.scales.get(resultScale).convertTo(
                this.scales.get(inputScale).getDegreesCelsius(inputTemperature)));
    }

    @Override
    public Object[] needGetScalesList() {
        return this.scales.keySet().toArray();
    }
}
