package ru.academits.novoselovda.matrix;

import ru.academits.novoselovda.vector.Vector;

/*
Реализовать класс матрицы Matrix с использованием класса Vector – хранить строки как массив векторов
Реализовать:
1.	Конструкторы:
a.	Matrix(n, m) – матрица нулей размера nxm
b.	Matrix(Matrix) – конструктор копирования
c.	Matrix(double[][]) – из двумерного массива
d.	Matrix(Vector[]) – из массива векторов-строк
2.	Методы:
a.	Получение размеров матрицы
b.	Получение и задание вектора-строки по индексу
c.	Получение вектора-столбца по индексу
d.	Транспонирование матрицы
e.	Умножение на скаляр
f.	Вычисление определителя матрицы
g.	toString определить так, чтобы выводить в виде { { вектор1}, {вектор 2} }
h.	умножение матрицы на вектор
i.	Сложение матриц
j.	Вычитание матриц
3.	Статические методы:
a.	Сложение матриц
b.	Вычитание матриц
c.	Умножение матриц

 */
public class Matrix {
    private int amountOfRaws; //TODO проверить что там в названии
    private int amountOfStrings;
    private Vector[] matrixStrings;

    public Matrix(int amountOfRaws, int amountOfStrings) {
        this.amountOfRaws = amountOfRaws;
        this.amountOfStrings = amountOfStrings;
        for (int i = 0; i < amountOfStrings; i++) {
            matrixStrings[i] = new Vector(amountOfRaws);
        }
    }

    public Matrix(Matrix matrix) {
        this.amountOfRaws = matrix.amountOfRaws;
        this.amountOfStrings = matrix.amountOfStrings;
        System.arraycopy(matrix.matrixStrings, 0, this.matrixStrings, 0, matrix.amountOfStrings);
    }

    public Matrix(double[][] matrixElements) {
        this.amountOfStrings = matrixElements.length;
        int maximum = matrixElements[0].length;
        for (int i = 1; i < this.amountOfStrings; i++) {
            maximum = Math.max(maximum, matrixElements[i].length);
        }
        this.amountOfRaws = maximum;
        for (int i = 0; i < this.amountOfStrings; i++) {
            this.matrixStrings[i] = new Vector(this.amountOfRaws, matrixElements[i]);
        }
    }

    public Matrix(Vector[] vectors) {
        this.amountOfStrings = vectors.length;
        int maximum = vectors[0].getSize();
        for (int i = 1; i < this.amountOfStrings; i++) {
            maximum = Math.max(maximum, vectors[i].getSize());
        }
        this.amountOfRaws = maximum;
        Vector nullVector = new Vector(this.amountOfRaws);
        Vector tempVector;
        for (int i = 0; i < this.amountOfStrings; i++) {
            if (vectors[i].getSize() < this.amountOfRaws) {
                tempVector = new Vector(vectors[i]);
                tempVector.add(nullVector);
                this.matrixStrings[i] = new Vector(tempVector);
            } else {
                this.matrixStrings[i] = new Vector(vectors[i]);
            }
        }
    }

    public int getAmountOfRaws() {
        return amountOfRaws;
    }

    public int getAmountOfStrings() {
        return amountOfStrings;
    }

    public String getSize() {
        return amountOfRaws + " x " + amountOfStrings;
    }

    public Vector getVectorOfRaw() {
        double[] arrayOfRawElements = new double[amountOfStrings];
        for (int i = 0; i < amountOfStrings; i++) {
            arrayOfRawElements[i] = matrixStrings[i].getComponent(i);
        }
        return new Vector(arrayOfRawElements);
    }

    public void transpose(){
        double[][]transposedMatrix = new double[amountOfRaws][amountOfStrings];
        for (int y = 0; y < amountOfStrings; y++) {
            for (int x = 0; x < amountOfRaws; x++) {

            }
        }
    }

}
