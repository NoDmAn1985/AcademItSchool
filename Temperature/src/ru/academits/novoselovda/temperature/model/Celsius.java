package ru.academits.novoselovda.temperature.model;

import ru.academits.novoselovda.temperature.interfaces.Scales;

public class Celsius extends AbstractScale implements Scales{

    public Celsius() {
        this.converters.put("Гр. фаренгейт", new CelsiusToFahrenheit());
        this.converters.put("Гр. кельвин", new CelsiusToKelvin());
    }
}
