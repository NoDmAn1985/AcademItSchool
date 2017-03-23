package ru.academits.novoselovda.shapes;

public class Circle implements Shape {
    private double width;
    private double radius;

    public Circle(double radius) {
        if (radius > 0) {
            this.radius = radius;
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
        return Math.PI * radius * radius;
    }

    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public String toString() {
        return "окружность с радиусом " + radius;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Circle circle = (Circle) o;
        return (circle.radius == radius && circle.width == width);
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        return prime + (int) (width + radius);
    }
}
