package ru.academits.novoselovda.main;

import ru.academits.novoselovda.shapes.*;

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
            shapesMethods.printMax(shapes, 1, "площадь");
            shapesMethods.printMax(shapes, 2, "периметр");

        } catch (RuntimeException exception) {
            System.out.println(exception.getMessage());
        }

    }

}
