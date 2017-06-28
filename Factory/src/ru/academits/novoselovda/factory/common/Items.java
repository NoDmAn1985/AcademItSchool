package ru.academits.novoselovda.factory.common;

public enum Items {
    PART_A("кузов"), PART_B("двигатель"), PART_C("аксессуары"), CAR("машина");
    private String name;
    Items(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
