package ru.academits.novoselovda.factory.model;

import ru.academits.novoselovda.factory.common.Configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Config {
    private static final String CONFIG_PATH = ".\\Factory\\src\\ru\\academits\\novoselovda\\factory\\resourсes\\config.ini";
    private static final String SEPARATOR = " = ";

    private static final int MAX_COUNT = 100;

    private HashMap<Configuration, Integer> config;

    public Config() {
        config = new HashMap<>();
        for (Configuration configuration : Configuration.values()) {
            config.put(configuration, configuration.getDefaultValue());
        }
    }

    public void loadConfigFromFile() {
        try (Scanner scanner = new Scanner(new FileInputStream(CONFIG_PATH))) {
            while (scanner.hasNextLine()) {
                setConfig(scanner.nextLine());
            }
        } catch (FileNotFoundException exception) {
            System.out.println("нет файла: " + exception.getMessage());
            //значения будут поумолчанию
        }

    }

    private void setConfig(String text) {
        int index = text.indexOf(SEPARATOR);
        if (index < 0) {
            return;
        }
        String parameter = text.substring(0, index);
        String valueString = text.substring(index + 3, text.length());
        int value = 0;
        try {
            value = Integer.parseInt(valueString);
        } catch (NumberFormatException exception) {
            System.out.println("в конфиге ошибочные данные");
        }
        for (Configuration configuration : config.keySet()) {
            if (configuration == Configuration.CONTROLLERS_COUNT) {
                continue;
            }
            if (parameter.equals(configuration.getName()) && value > 0 && value <= MAX_COUNT) {
                config.put(configuration, value);
                return;
            }
        }
    }

    public HashMap<Configuration, Integer> getOptions() {
        return config;
    }
}
