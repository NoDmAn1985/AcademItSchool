package ru.academits.novoselovda.main;

import ru.academits.novoselovda.matrix.Matrix;
import ru.academits.novoselovda.vector.Vector; //вектор берётся из модуля вектор

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("1. Конструкторы");
            System.out.println("a.	Matrix(n, m) – матрица нулей размера nxm");
            Matrix matrix1 = new Matrix(3, 3);
            System.out.println(matrix1);

            System.out.println("b.	Matrix(Matrix) – конструктор копирования");
            Matrix matrix2 = new Matrix(matrix1);
            System.out.println(matrix2);

            System.out.println("c.	Matrix(double[][]) – из двумерного массива");
            double[][] array = {
                    {1.0, 2.0, 3.0},
                    {4.0},
                    {5.0, 6.0, 7.0}};
            Matrix matrix3 = new Matrix(array);
            System.out.println(matrix3);

            System.out.println("d.	Matrix(Vector[]) – из массива векторов-строк");
            Vector[] vectors = new Vector[3];
            vectors[0] = new Vector(array[0]);
            vectors[1] = new Vector(array[1]);
            vectors[2] = new Vector(array[2]);
            Matrix matrix4 = new Matrix(vectors);
            System.out.println(matrix4);

            System.out.println();
            System.out.println("2.	Методы:");
            System.out.println("a.	Получение размеров матрицы");
            System.out.println(matrix4);
            System.out.println(matrix4.getRowsCount());
            System.out.println(matrix4.getColumnsCount());
            System.out.println(matrix4.getSizeToString());

            System.out.println("b.	Получение и задание вектора-строки по индексу");
            System.out.println(matrix4.getRow(1));
            double[] array1 = {9.9, 8.8, 7.7};
            Vector vector = new Vector(array1);
            matrix4.setRow(matrix4.getRowsCount(), vector);
            System.out.println(matrix4);
            System.out.println(matrix4.getSizeToString());

            System.out.println("c.	Получение вектора-столбца по индексу");
            System.out.println(matrix4.getColumn(2));

            System.out.println("d.	Транспонирование матрицы");
            System.out.println(matrix4);
            matrix4.transpose();
            System.out.println(matrix4);

            System.out.println("e.	Умножение на скаляр");
            matrix4.scalarProduct(-1);
            System.out.println(matrix4);

            System.out.println("f.	Вычисление определителя матрицы");
            double[][] array2 = {
                    {2, 1, 3},
                    {1, -4, 1},
                    {5, 1, -2}};
            Matrix matrix5 = new Matrix(array2);
            System.out.println(matrix5.getDeterminant());

            System.out.println("g.	toString определить так, чтобы выводить в виде { { вектор1}, {вектор 2} }");
            System.out.println(matrix5.toString());

            System.out.println("h.	умножение матрицы на вектор");
            double[][] array3 = {
                    {1, 1, 1},
                    {1, 1, 1},
                    {1, 1, 1},
                    {1, 1, 1}};
            Matrix matrix6 = new Matrix(array3);
            System.out.println(matrix6);
            double[] array4 = {2, 3, 5};
            System.out.println(matrix6.product(new Vector(array4)));

            System.out.println("i.	Сложение матриц");
            double[][] array5 = {
                    {1, 1, 1},
                    {1, 1, 1},
                    {1, 1, 1}};
            Matrix matrix7 = new Matrix(array5);
            double[][] array6 = {
                    {0, 0, -1},
                    {0, -1, 0},
                    {-1, 0, 0}};
            Matrix matrix8 = new Matrix(array6);
            matrix7.add(matrix8);
            System.out.println(matrix7);

            System.out.println("j.	Вычитание матриц");
            matrix7.subtract(matrix8);
            System.out.println(matrix7);

            System.out.println();
            System.out.println("3.	Статические методы:");
            double[][] array7 = {
                    {1, 1, 1},
                    {1, 1, 1},
                    {1, 1, 1}};
            Matrix matrix9 = new Matrix(array7);
            double[][] array8 = {
                    {0, 0, -1},
                    {0, -1, 0},
                    {-1, 0, 0}};
            Matrix matrix10 = new Matrix(array8);
            System.out.println("a.	Сложение матриц");
            System.out.println(Matrix.sum(matrix9, matrix10));

            System.out.println("b.	Вычитание матриц");
            System.out.println(Matrix.subtract(matrix9, matrix10));
            double[][] array9 = {
                    {0, -1, 0, -1, 0, -1},
                    {-1, 0, -1, 0, -1, 0},
                    {0, -1, 0, -1, 0, -1}};
            Matrix matrix11 = new Matrix(array9);

            System.out.println("c.	Умножение матриц");
            System.out.println(Matrix.product(matrix9, matrix11));
            System.out.println();
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }
}
