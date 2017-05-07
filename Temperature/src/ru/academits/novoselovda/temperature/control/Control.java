package ru.academits.novoselovda.temperature.control;

import ru.academits.novoselovda.temperature.interfaces.TempConverter;
import ru.academits.novoselovda.temperature.interfaces.View;
import ru.academits.novoselovda.temperature.interfaces.ViewListener;
import ru.academits.novoselovda.temperature.model.*;

public class Control implements ViewListener {
    private static final String[] LIST = {"Гр. цельсия", "Гр. фаренгейта", "Гр. кельвина"};
    private TempConverter celsiusToKelvin = new CelsiusToKelvin();
    private TempConverter celsiusToFahrenheit = new CelsiusToFahrenheit();
    private TempConverter kelvinToCelsius = new KelvinToCelsius();
    private TempConverter kelvinToFahrenheit = new KelvinToFahrenheit();
    private TempConverter fahrenheitToCelsius = new FahrenheitToCelsius();
    private TempConverter fahrenheitToKelvin = new FahrenheitToKelvin();
    private View view;

    public Control(View view) {
        this.view = view;
    }

    public static String[] getListOfScales() {
        return LIST;
    }

    @Override
    public void needConvertTemperature(double userData, int userScale, int resultScale) {
        double resultData = 0;
        if (userScale == resultScale) {
            resultData = userData;
        } else if (userScale == 0 ) {
            switch (resultScale) {
                case 1:
                    resultData = celsiusToFahrenheit.convert(userData);
                    break;
                case 2:
                    resultData = celsiusToKelvin.convert(userData);
                    break;
            }
        } else if (userScale == 1) {
            switch (resultScale) {
                case 0:
                    resultData = fahrenheitToCelsius.convert(userData);
                    break;
                case 2:
                    resultData = fahrenheitToKelvin.convert(userData);
                    break;
            }
        } else {
            switch (resultScale) {
                case 0:
                    resultData = kelvinToCelsius.convert(userData);
                    break;
                case 1:
                    resultData = kelvinToFahrenheit.convert(userData);
                    break;
            }
        }
        view.onTemperatureConverted(resultData);
    }
}
