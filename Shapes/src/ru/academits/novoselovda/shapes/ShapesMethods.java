package ru.academits.novoselovda.shapes;

public class ShapesMethods {

    public void printArray(Shape[] shapes) {
        for (int i = 0; i < shapes.length; i++) {
            System.out.printf("%d) %s, площадью %.2f и периметром %.2f%n", i,
                    shapes[i].toString(), shapes[i].getArea(), shapes[i].getPerimeter());
        }
    }
}
