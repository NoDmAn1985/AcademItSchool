package ru.academits.novoselovda.shapes;

public class Square implements Shape {
    private double width;

    public Square(double width) {
        if (width > 0) {
            this.width = width;
        } else {
            throw new RuntimeException("ОШИБКА: квадрат со стороной " + width + " не может быть создан");
        }
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return width;
    }

    public double getArea() {
        return width * width;
    }

    public double getPerimeter() {
        return 4 * width;
    }

    @Override
    public String toString() {
        return "квадрат со строной " + width;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Square square = (Square) o;
        return square.width == width;
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        return prime + (int)width;
    }
}
