package ru.academits.novoselovda.shapes;

public interface Shape {
    static final int prime = 37;

    double getWidth();

    double getHeight();

    double getArea();

    double getPerimeter();
}
