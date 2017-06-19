package ru.academits.novoselovda.temperature.control;

import ru.academits.novoselovda.temperature.interfaces.Convertible;
import ru.academits.novoselovda.temperature.interfaces.View;
import ru.academits.novoselovda.temperature.interfaces.ViewListener;
import ru.academits.novoselovda.temperature.model.*;

import java.util.*;

public class Control implements ViewListener {
    private final View view;
    private final Map<String, Convertible> scales;

    public Control(View view) {
        this.view = view;
        List<Convertible> scalesList = new LinkedList<>();
        scalesList.add(new Celsius());
        scalesList.add(new Kelvin());
        scalesList.add(new Fahrenheit());

        this.scales = new TreeMap<>();
        for (Convertible scale : scalesList) {
            this.scales.put(scale.getName(), scale);
        }
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
