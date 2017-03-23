package ru.academits.novoselovda.shapes;

public class Triangle implements Shape {
    private double width;
    private double height;
    private double sideX1Y1X2Y2;
    private double sideX2Y2X3Y3;
    private double sideX3Y3X1Y1;


    public Triangle(double x1, double y1, double x2, double y2, double x3, double y3) {
        if (Math.abs((x3 - x1) * (y2 - y1) - (y3 - y1) * (x2 - x1)) > 1.0e-10) {
            sideX1Y1X2Y2 = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
            sideX2Y2X3Y3 = Math.sqrt(Math.pow(x2 - x3, 2) + Math.pow(y2 - y3, 2));
            sideX3Y3X1Y1 = Math.sqrt(Math.pow(x3 - x1, 2) + Math.pow(y3 - y1, 2));
            this.width = Math.max(x1, Math.max(x2, x3)) - Math.min(x1, Math.min(x2, x3));
            this.height = Math.max(y1, Math.max(y2, y3)) - Math.min(y1, Math.min(y2, y3));
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Triangle triangle = (Triangle) o;
        return (Double.compare(triangle.width, width) == 0
                && Double.compare(triangle.height, height) == 0);
    }

    @Override
    public int hashCode() {
        return prime + (int) (width + height + sideX1Y1X2Y2
                + sideX2Y2X3Y3 + sideX3Y3X1Y1);
    }
}
