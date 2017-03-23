package ru.academits.novoselovda.shapes;

import java.util.Comparator;

public class ShapeComparatorByPerimeter implements Comparator<Shape> {

    public int compare(Shape o1, Shape o2) {
        final double EPSILON = 1.0e-10;
        if (o1.getPerimeter() - o2.getPerimeter() > EPSILON) {
            return 1;
        } else if (Math.abs(o1.getPerimeter() - o2.getPerimeter()) <= EPSILON) {
            return 0;
        } else {
            return -1;
        }
    }
}
