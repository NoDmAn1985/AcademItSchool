package ru.academits.novoselovda.temperature.model;

import ru.academits.novoselovda.temperature.interfaces.Scales;

public class Kelvin extends AbstractScale implements Scales {

    public Kelvin() {
        this.converters.put("Гр. фаренгейт", new KelvinToFahrenheit());
        this.converters.put("Гр. цельсия", new KelvinToCelsius());
    }
}
