package ru.academits.novoselovda.shapes;

public class ShapesMethods {
    public void printMax(Shape[] shapes, int rate, String parameter) {
        if (rate > shapes.length) {
            System.out.println("Заданное место превышает длину массива");
            return;
        }
        System.out.printf("На %d месте по %s находится - %s%n"
                , rate, (parameter.equals("площадь") ? "площади" : "периметру")
                , getMaxOfThisParameter(shapes, rate, parameter, Double.MAX_VALUE));
    }

    public void printArray(Shape[] shapes) {
        for (int i = 0; i < shapes.length; i++) {
            System.out.printf("%d) %s, площадью %.2f и периметром %.2f%n", i,
                    shapes[i].toString(), shapes[i].getArea(), shapes[i].getPerimeter());
        }
    }

    private String getMaxOfThisParameter(Shape[] shapes, int rate, String parameter, double maxToSkeep) {
        int indexOfShape = 0;
        double maximum = 0;
        double valueOfThisParameter;
        final double EPSILON = 1.0e-10;
        for (int i = 0; i < shapes.length; i++) {
            switch (parameter) {
                case "площадь":
                    valueOfThisParameter = shapes[i].getArea();
                    break;
                case "периметр":
                    valueOfThisParameter = shapes[i].getPerimeter();
                    break;
                default:
                    throw new RuntimeException("ОШИБКА: передан неверный параметр для поиска" +
                            "(введите \"периметр\" или \"площадь\")");
            }
            if (maxToSkeep - valueOfThisParameter > EPSILON
                    && valueOfThisParameter - maximum > EPSILON) {
                maximum = valueOfThisParameter;
                indexOfShape = i;
            }
        }
        if (maximum == 0) {
            return "пусто";
        } else if (rate == 1) {
            return String.format("%s (%s: %.2f)", shapes[indexOfShape].toString(), parameter, maximum);
        } else {
            return getMaxOfThisParameter(shapes, rate - 1, parameter, maximum);
        }
    }

}
