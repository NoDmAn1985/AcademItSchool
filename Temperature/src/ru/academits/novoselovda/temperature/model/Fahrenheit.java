package ru.academits.novoselovda.temperature.model;

import ru.academits.novoselovda.temperature.interfaces.Scales;

public class Fahrenheit extends AbstractScale implements Scales {

    public Fahrenheit() {
        this.converters.put("Гр. кельвина", new FahrenheitToKelvin());
        this.converters.put("Гр. цельсия", new FahrenheitToCelsius());
    }
}
