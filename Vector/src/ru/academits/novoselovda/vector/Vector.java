package ru.academits.novoselovda.vector;

import java.util.ArrayList;

public class Vector {
    private ArrayList<Double> components = new ArrayList<>();

    public Vector(int n) {
        testOnPositive(n);
        for (int i = 0; i < n; i++) {
            components.add(0.0);
        }
    }

    public Vector(Vector vector) {
        this.components.addAll(vector.components);
    }

    public Vector(double[] components) {
        for (double component : components) {
            this.components.add(component);
        }
    }

    public Vector(int n, double[] components) {
        testOnPositive(n);
        for (int i = 0; i < Math.max(components.length, n); i++) {
            this.components.add((i < components.length) ? components[i] : 0);
        }
    }

    public int getSize() {
        return components.size();
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
        for (int i = 0; i < Math.max(this.components.size(), vector.components.size()); i++) {
            double newDouble = ((i < this.components.size()) ? this.components.get(i) : 0)
                    + ((i < vector.components.size()) ? vector.components.get(i) : 0);
            if (i < this.components.size()) {
                this.components.set(i, newDouble);
            } else {
                this.components.add(newDouble);
            }
        }
    }

    public void diff(Vector vector) {
        for (int i = 0; i < Math.max(this.components.size(), vector.components.size()); i++) {
            double newDouble = ((i < this.components.size()) ? this.components.get(i) : 0)
                    - ((i < vector.components.size()) ? vector.components.get(i) : 0);
            if (i < this.components.size()) {
                this.components.set(i, newDouble);
            } else {
                this.components.add(newDouble);
            }
        }
    }

    public void scalarProduct(double scalar) {
        for (int i = 0; i < components.size(); i++) {
            components.set(i, scalar * components.get(i));
        }
    }

    public void reverse() {
        for (int i = 0; i < components.size(); i++) {
            components.set(i, -components.get(i));
        }
    }

    public double getComponent(int index) {
        testOfEntrance(index);
        return components.get(index);
    }

    public void setComponent(int index, double component) {
        testOfEntrance(index);
        this.components.set(index, component);
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
        return (this.components.equals(vector.components));
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        return prime + components.hashCode();
    }

    public static Vector sum(Vector vector1, Vector vector2) {
        Vector vectorNew = new Vector(vector1);
        vectorNew.add(vector2);
        return vectorNew;
    }

    public static Vector residual(Vector vector1, Vector vector2) {
        Vector vectorNew = new Vector(vector1);
        vectorNew.diff(vector2);
        return vectorNew;
    }

    public static double scalarProduct(Vector vector1, Vector vector2) {
        int length = Math.min(vector1.getSize(), vector2.getSize());
        double production = 0;
        for (int i = 0; i < length; i++) {
            production += vector1.components.get(i) * vector2.components.get(i);
        }
        return production;
    }

    private void testOfEntrance(int index) {
        if (index >= components.size()) {
            throw new IllegalArgumentException("ОШИБКА: запрашиваемый индекс превышает предел вектора");
        }
    }

    private static void testOnPositive(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("ОШИБКА: размерность вектора должна быть больше нуля");
        }
    }

}
