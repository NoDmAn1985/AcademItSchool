package ru.academits.novoselovda.shapes;

public class Triangle implements Shape {
    private double width;
    private double height;
    private double sideX1Y1X2Y2;
    private double sideX2Y2X3Y3;
    private double sideX3Y3X1Y1;

    public Triangle(double x1, double y1, double x2, double y2, double x3, double y3) {
        if (Math.abs((x3 - x1) * (y2 - y1) - (y3 - y1) * (x2 - x1)) > 1.0e-10) {
            sideX1Y1X2Y2 = getSide(x1, y1, x2, y2);
            sideX2Y2X3Y3 = getSide(x2, y2, x3, y3);
            sideX3Y3X1Y1 = getSide(x3, y3, x1, y1);
            this.width = getSideByAxis(x1, x2, x3);
            this.height = getSideByAxis(y1, y2, y3);
        } else {
            String message = String.format("ОШИБКА: вершины треугольника [(%f, %f)," +
                    "(%f, %f) и (%f, %f)] лежат на одной прямой", x1, y1, x2, y2, x3, y3);
            throw new RuntimeException(message);
        }
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getArea() {
        double semiPerimeter = getPerimeter() / 2;
        return Math.sqrt(semiPerimeter * (semiPerimeter - sideX1Y1X2Y2)
                * (semiPerimeter - sideX3Y3X1Y1) * (semiPerimeter - sideX2Y2X3Y3));
    }

    public double getPerimeter() {
        return sideX1Y1X2Y2 + sideX2Y2X3Y3 + sideX3Y3X1Y1;
    }

    @Override
    public String toString() {
        return String.format("треугольник со стронами %.2f, %.2f и %.2f"
                , sideX1Y1X2Y2, sideX2Y2X3Y3, sideX3Y3X1Y1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Triangle triangle = (Triangle) o;
        return (triangle.width == width && triangle.height == height);
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        return prime + (int) (width + height + sideX1Y1X2Y2
                + sideX2Y2X3Y3 + sideX3Y3X1Y1);
    }

    private double getSide(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    private double getSideByAxis(double a, double b, double c) {
        return Math.max(a, Math.max(b, c)) - Math.min(a, Math.min(b, c));
    }
}
