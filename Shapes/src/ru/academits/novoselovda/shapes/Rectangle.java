package ru.academits.novoselovda.shapes;

public class Rectangle implements Shape {
    private double width;
    private double height;


    public Rectangle(double width, double height) {
        if (width > 0 && height > 0) {
            this.width = width;
            this.height = height;
        } else {
            throw new RuntimeException("ОШИБКА: прямоугольник со сторонами " + width
                    + " и " + height + " не может быть создан");
        }
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getArea() {
        return width * height;
    }

    public double getPerimeter() {
        return 2 * (width + height);
    }

    @Override
    public String toString() {
        return "прямоугольник со стронами " + width + " и " + height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Rectangle rectangle = (Rectangle) o;
        return (rectangle.width == width && rectangle.height == height);
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        return prime + (int) (width + height);
    }
}
