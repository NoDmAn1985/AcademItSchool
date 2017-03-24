package ru.academits.novoselovda.main;

import ru.academits.novoselovda.shapes.*;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        try {
            Shape[] shapes = {
                    new Circle(1),
                    new Circle(2),
                    new Rectangle(1, 2),
                    new Rectangle(2, 3),
                    new Square(2),
                    new Square(3),
                    new Triangle(1, 0, 1, 1, 2, 2),
                    new Triangle(1, 2, 1, 4, 5, 6),
                    new Triangle(0, 0, 1, 1, 2, 0),
                    new Triangle(-1, -1, -2, 1, 2 - 3, 0)};

            ShapesMethods shapesMethods = new ShapesMethods();
            shapesMethods.printArray(shapes);
            System.out.println();

            Arrays.sort(shapes, new ShapeComparatorByArea());
            int position = 1;
            System.out.printf("На %d месте по площади находится %s и площадью %.2f%n"
                    , position, shapes[position - 1].toString(), shapes[position - 1].getArea());

            Arrays.sort(shapes, new ShapeComparatorByPerimeter());
            position = 2;
            System.out.printf("На %d месте по площади находится %s и площадью %.2f%n"
                    , position, shapes[position - 1].toString(), shapes[position - 1].getPerimeter());


        } catch (RuntimeException exception) {
            System.out.println(exception.getMessage());
        }

    }

}
