package ru.academits.novoselovda.main;

import ru.academits.novoselovda.vector.Vector;

public class Main {
    public static void main(String[] args) {
        try {
            Vector vector1 = new Vector(3);
            System.out.println("Вектор1: " + vector1 + ", длиной " + vector1.getSize());
            Vector vector2 = new Vector(vector1);
            System.out.println("Вектор2: " + vector2 + ", длиной " + vector2.getSize());
            double[] array1 = {1, 2, 3, 4};
            Vector vector3 = new Vector(10, array1);
            System.out.println("Вектор3: " + vector3 + ", длиной " + vector3.getSize());
            System.out.println("-----------------------------------------------------------------");
            Vector vector4 = new Vector(array1);
            System.out.println("Вектор4: " + vector4 + ", длиной " + vector4.getSize());
            double[] array2 = {-1, 2, -3};
            Vector vector5 = new Vector(array2);
            System.out.println("Вектор5: " + vector5 + ", длиной " + vector5.getSize());
            vector4.add(vector5);
            System.out.println("Вектор4 = Вектор4 + Вектор5: " + vector4 + ", длиной " + vector4.getSize());
            vector4.subtract(vector5);
            System.out.println("Вектор4 = Вектор4 - Вектор5: " + vector4 + ", длиной " + vector4.getSize());
            int scalar = 10;
            vector4.scalarProduct(10);
            System.out.println("Вектора4 x на скаляр " + scalar + ": " + vector4 + ", длиной " + vector4.getSize());
            vector4.reverse();
            System.out.println("Вектор4 после разворота: " + vector4 + ", длиной " + vector4.getSize());
            int userIndex = 2;
            System.out.println("Компонент Вектора4 № " + userIndex + " = " + vector4.getComponent(userIndex));
            System.out.println("Вектор4: " + vector4 + ", длиной " + vector4.getSize());
            double userComponent = 99;
            System.out.println("Установим " + userComponent + " в позицию " + userIndex);
            vector4.setComponent(userIndex, userComponent);
            System.out.println("Вектор4: " + vector4 + ", длиной " + vector4.getSize());
            System.out.println("Вектор4 == Вектору5: " + vector4.equals(vector5));
            System.out.println("-----------------------------------------------------------------");
            System.out.println("Статические методы:");
            System.out.println("Вектор4: " + vector4 + ", длиной " + vector4.getSize());
            double[] array3 = {-1, 2, -3, 4, 15};
            Vector vector6 = new Vector(array3);
            System.out.println("Вектор6: " + vector6 + ", длиной " + vector6.getSize());
            Vector vector7 = Vector.sum(vector4, vector6);
            System.out.println("Сумма Вектора4 и Вектора6: " + vector7 + ", длиной " + vector7.getSize());
            vector7 = Vector.subtract(vector4, vector6);
            System.out.println("Разность Вектора4 и Вектора6: " + vector7 + ", длиной " + vector7.getSize());
            System.out.println("Скалярное произведение Вектора4 и Вектора6: " + Vector.scalarProduct(vector4, vector6));
        } catch (RuntimeException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
