package ru.academits.novoselovda.main;

import ru.academits.novoselovda.vector.Vector;

public class Main {
    public static void main(String[] args) {
        try {
            Vector vector5 = new Vector(3);
            System.out.println("Вектор5: " + vector5.toString() + ", длиной " + vector5.getSize());
            Vector vector6 = new Vector(vector5);
            System.out.println("Вектор6: " + vector6.toString() + ", длиной " + vector6.getSize());
            double[] array1 = {1, 2, 3, 4};
            Vector vector7 = new Vector(10, array1);
            System.out.println("Вектор7: " + vector7.toString() + ", длиной " + vector7.getSize());
            System.out.println("-----------------------------------------------------------------");
            Vector vector1 = new Vector(array1);
            System.out.println("Вектор1: " + vector1.toString() + ", длиной " + vector1.getSize());
            double[] array2 = {-1, 2, -3, 4, 6};
            Vector vector2 = new Vector(array2);
            System.out.println("Вектор2: " + vector2.toString() + ", длиной " + vector2.getSize());
            vector1.add(vector2);
            System.out.println("Вектор1 = Вектор1 + Вектор2: " + vector1.toString() + ", длиной " + vector1.getSize());
            vector1.diff(vector2);
            System.out.println("Вектор1 = Вектор1 - Вектор2: " + vector1.toString() + ", длиной " + vector1.getSize());
            System.out.println("Скалярное произведение Вектора1 и Вектора2: " + vector1.scalarProduct(vector2));
            vector1.reverse();
            System.out.println("Вектор1 после разворота: " + vector1.toString() + ", длиной " + vector1.getSize());
            int userIndex = 2;
            System.out.println("Компонент Вектора1 № " + userIndex + " = " + vector1.getComponent(userIndex));
            System.out.println("Вектор1: " + vector1.toString() + ", длиной " + vector1.getSize());
            double userComponent = 99;
            System.out.println("Установим " + userComponent + " в позицию " + userIndex);
            vector1.setComponent(userIndex, userComponent);
            System.out.println("Вектор1: " + vector1.toString() + ", длиной " + vector1.getSize());
            System.out.println("Вектор1 == Вектору2: " + vector1.equals(vector2));
            System.out.println("-----------------------------------------------------------------");
            System.out.println("Статические методы:");
            System.out.println("Вектор1: " + vector1.toString() + ", длиной " + vector1.getSize());
            double[] array3 = {-1, 2, -3, 4, 15};
            Vector vector3 = new Vector(array3);
            System.out.println("Вектор3: " + vector3.toString() + ", длиной " + vector3.getSize());
            Vector vector4 = Vector.sum(vector1, vector3);
            System.out.println("Сумма Вектора1 и Вектора3: " + vector4.toString() + ", длиной " + vector4.getSize());
            vector4 = Vector.residual(vector1, vector3);
            System.out.println("Разность Вектора1 и Вектора3: " + vector4.toString() + ", длиной " + vector4.getSize());
            System.out.println("Скалярное произведение Вектора1 и Вектора3: " + Vector.scalarProduct(vector1, vector3));
        } catch (RuntimeException exception) {
            exception.getMessage();
        }
    }
}
