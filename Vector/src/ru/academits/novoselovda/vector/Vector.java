package ru.academits.novoselovda.vector;

import java.util.Arrays;

public class Vector {
    private int n;
    private double[] components;

    public Vector(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("ОШИБКА: размерность вектора должна быть больше нуля");
        }
        this.n = n;
        components = new double[n];
    }

    public Vector(Vector vector) {
        this.n = vector.n;
        components = new double[n];
        this.components = vector.components;
    }

    public Vector(double[] components) {
        this.components = components;
        this.n = components.length;
    }

    public Vector(int n, double[] array) {
        if (n <= 0) {
            throw new IllegalArgumentException("ОШИБКА: размерность вектора должна быть больше нуля");
        }
        this.n = n;
        if (array.length < n) {
            this.components = new double[n];
            for (int i = 0; i < n; i++) {
                this.components[i] = (i < array.length) ? array[i] : 0;
            }
        } else {
            this.components = array;
        }
    }

    public int getSize() {
        return n;
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
        for (int i = 0; i < n; i++) {
            components[i] = components[i] + ((i < vector.n) ? vector.components[i] : 0);
        }
    }

    public void diff(Vector vector) {
        for (int i = 0; i < n; i++) {
            components[i] = components[i] - ((i < vector.n) ? vector.components[i] : 0);
        }
    }

    public double scalarProduct(Vector vector) {
        double production = 0;
        int length = (n > vector.n) ? vector.n : n;
        for (int i = 0; i < length; i++) {
            production += components[i] * vector.components[i];
        }
        return production;
    }

    public void reverse() {
        for (int i = 0; i < n; i++) {
            components[i] = -components[i];
        }
    }

    public double getComponent(int index) {
        if (index >= n) {
            throw new RuntimeException("ОШИБКА: запрашиваемый индекс превышает предел массива");
        }
        return components[index];
    }

    public void setComponent(int index, double component) {
        if (index >= n) {
            throw new RuntimeException("ОШИБКА: запрашиваемый индекс превышает предел массива");
        }
        this.components[index] = component;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return (n == vector.n && Arrays.equals(components, vector.components));
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;
        hash = prime * hash + n;
        return prime * hash + Arrays.hashCode(components);
    }

    public static Vector sum(Vector vector1, Vector vector2) {
        int length = (vector1.n > vector2.n) ? vector1.n : vector2.n;
        double[] newComponents = new double[length];
        for (int i = 0; i < length; i++) {
            newComponents[i] = ((i < vector1.n) ? vector1.components[i] : 0) + ((i < vector2.n) ? vector2.components[i] : 0);
        }
        return new Vector(newComponents);
    }

    public static Vector residual(Vector vector1, Vector vector2) {
        int length = (vector1.n > vector2.n) ? vector1.n : vector2.n;
        double[] newComponents = new double[length];
        for (int i = 0; i < length; i++) {
            newComponents[i] = ((i < vector1.n) ? vector1.components[i] : 0) - ((i < vector2.n) ? vector2.components[i] : 0);
        }
        return new Vector(newComponents);
    }

    public static double scalarProduct(Vector vector1, Vector vector2) {
        int length = (vector1.n > vector2.n) ? vector2.n : vector1.n;
        double production = 0;
        for (int i = 0; i < length; i++) {
            production += vector1.components[i] * vector2.components[i];
        }
        return production;
    }
}
