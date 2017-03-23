package ru.academits.novoselovda.shapes;

import java.util.Comparator;

public class ShapesMethods {

    public void printArray(Shape[] shapes) {
        for (int i = 0; i < shapes.length; i++) {
            System.out.printf("%d) %s, площадью %.2f и периметром %.2f%n", i,
                    shapes[i].toString(), shapes[i].getArea(), shapes[i].getPerimeter());
        }
    }

    public void insertionSortOfArray(Shape[] shapes, Comparator<Shape> comparator) {
        for (int i = 1; i < shapes.length; i++) {
            if (comparator.compare(shapes[i], shapes[i - 1]) > 0) {
                Shape temp = shapes[i];
                int j = i - 1;
                while (j >= 0) {
                    if (comparator.compare(temp, shapes[j]) > 0) {
                        shapes[j + 1] = shapes[j];
                    } else {
                        break;
                    }
                    --j;
                }
                shapes[j + 1] = temp;
            }
        }
    }
}
