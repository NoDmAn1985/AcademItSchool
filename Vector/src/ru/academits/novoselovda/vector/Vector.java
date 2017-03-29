package ru.academits.novoselovda.vector;

import java.util.Arrays;

public class Vector {
    private double[] components;

    public Vector(int n) {
        testOnPositive(n);
        this.components = new double[n];
    }

    public Vector(Vector vector) {
        this.components = Arrays.copyOf(vector.components, vector.components.length);
    }

    public Vector(double[] components) {
        this.components = Arrays.copyOf(components, components.length);
    }

    public Vector(int n, double[] components) {
        testOnPositive(n);
        this.components = Arrays.copyOf(components, n);
    }

    public int getSize() {
        return components.length;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (double component : components) {
            sb.append(component).append(", ");
        }
        sb.delete(sb.length() - 2, sb.length()).append("}");
        return sb.toString();
    }

    public void add(Vector vector) {
        if (vector.components.length > this.components.length) {
            double[] old = this.components;
            this.components = Arrays.copyOf(old, vector.components.length);
        }
        for (int i = 0; i < vector.components.length; i++) {
            this.components[i] = this.components[i] + vector.components[i];
        }
    }

    public void subtract(Vector vector) {
        if (vector.components.length > this.components.length) {
            double[] old = this.components;
            this.components = Arrays.copyOf(old, vector.components.length);
        }
        for (int i = 0; i < vector.components.length; i++) {
            this.components[i] = this.components[i] - vector.components[i];
        }
    }

    public void scalarProduct(double scalar) {
        for (int i = 0; i < this.components.length; i++) {
            this.components[i] = scalar * this.components[i];
        }
    }

    public void reverse() {
        scalarProduct(-1);
    }

    public double getComponent(int index) {
        testOfEntrance(index);
        return components[index];
    }

    public void setComponent(int index, double component) {
        testOfEntrance(index);
        this.components[index] = component;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vector vector = (Vector) o;
        return Arrays.equals(this.components, vector.components);
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        return prime + Arrays.hashCode(components);
    }

    public static Vector sum(Vector vector1, Vector vector2) {
        Vector vectorNew = new Vector(vector1);
        vectorNew.add(vector2);
        return vectorNew;
    }

    public static Vector subtract(Vector vector1, Vector vector2) {
        Vector vectorNew = new Vector(vector1);
        vectorNew.subtract(vector2);
        return vectorNew;
    }

    public static double scalarProduct(Vector vector1, Vector vector2) {
        int length = Math.min(vector1.getSize(), vector2.getSize());
        double production = 0;
        for (int i = 0; i < length; i++) {
            production += vector1.components[i] * vector2.components[i];
        }
        return production;
    }

    private void testOfEntrance(int index) {
        if (index < 0 || index >= components.length) {
            throw new IllegalArgumentException("ОШИБКА: запрашиваемый индекс не найден в векторе");
        }
    }

    private static void testOnPositive(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("ОШИБКА: размерность вектора должна быть больше нуля");
        }
    }

}
