package ru.academits.novoselovda.temperature.model;

import ru.academits.novoselovda.temperature.interfaces.TempConverter;

import java.util.Map;
import java.util.TreeMap;

abstract class AbstractScale {
    Map<Object, TempConverter> converters = new TreeMap<>();

    public Object[] getListOfTargetScales() {
        return this.converters.keySet().toArray();
    }

    public double convertTo(Object targetScale, double inputTemperature) {
        String targetScaleString = (String) targetScale;
        return this.converters.get(targetScaleString).convert(inputTemperature);
    }

}
