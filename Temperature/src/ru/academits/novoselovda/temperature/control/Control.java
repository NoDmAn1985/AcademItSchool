package ru.academits.novoselovda.temperature.control;

import ru.academits.novoselovda.temperature.interfaces.Scales;
import ru.academits.novoselovda.temperature.interfaces.View;
import ru.academits.novoselovda.temperature.interfaces.ViewListener;
import ru.academits.novoselovda.temperature.model.*;

import java.util.Map;
import java.util.TreeMap;

public class Control implements ViewListener {
    private View view;
    private Map<String, Scales> scales;

    public Control(View view) {
        this.view = view;
        this.scales = new TreeMap<>();
        this.scales.put("Гр. цельсия", new Celsius());
        this.scales.put("Гр. кельвина", new Kelvin());
        this.scales.put("Гр. френгейта", new Fahrenheit());
    }

    @Override
    public void needConvertTemperature(double inputTemperature, String inputScale, String resultScale) {
        view.onTemperatureConverted(this.scales.get(inputScale).convertTo(resultScale, inputTemperature));
    }

    @Override
    public Object[] needGetScalesList() {
        return this.scales.keySet().toArray();
    }

    @Override
    public Object[] needGetResultScalesList(Object inputScale) {
        String inputScaleString = (String) inputScale;
        return this.scales.get(inputScaleString).getListOfTargetScales();
    }
}
