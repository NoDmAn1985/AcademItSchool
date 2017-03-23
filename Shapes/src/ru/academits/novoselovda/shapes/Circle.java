package ru.academits.novoselovda.shapes;

public class Circle implements Shape {
    private double width;

    public Circle(double radius) {
        if (radius > 0) {
            this.width = 2 * radius;
        } else {
            throw new RuntimeException("ОШИБКА: окружность с радиусом " + radius + " не может быть создана");
        }
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return width;
    }

    public double getArea() {
        return Math.PI * width * width / 4;
    }

    public double getPerimeter() {
        return Math.PI * width;
    }

    @Override
    public String toString() {
        return "окружность с радиусом " + (width / 2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Circle circle = (Circle) o;
        return Double.compare(circle.width, width) == 0;
    }

    @Override
    public int hashCode() {
        return prime + (int) width;
    }
}
