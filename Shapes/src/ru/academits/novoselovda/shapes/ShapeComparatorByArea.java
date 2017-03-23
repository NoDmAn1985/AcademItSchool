package ru.academits.novoselovda.shapes;

import java.util.Comparator;

public class ShapeComparatorByArea implements Comparator<Shape> {

    public int compare(Shape o1, Shape o2) {
        final double EPSILON = 1.0e-10;
        if (o1.getArea() - o2.getArea() > EPSILON) {
            return 1;
        } else if (Math.abs(o1.getArea() - o2.getArea()) <= EPSILON) {
            return 0;
        } else {
            return -1;
        }
    }
}
