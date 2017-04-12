package ru.academits.novoselovda.matrix;

import ru.academits.novoselovda.vector.Vector;

import java.util.Arrays;

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
    private Vector[] rows;

    public Matrix(int rowsCount, int columnsCount) {
        testOnZeroNumberOfElements(rowsCount, columnsCount);
        rows = new Vector[rowsCount];
        for (int i = 0; i < rowsCount; i++) {
            rows[i] = new Vector(columnsCount);
        }
    }

    public Matrix(Matrix matrix) {
        this.rows = new Vector[matrix.rows.length];
        for (int i = 0; i < matrix.rows.length; i++) {
            this.rows[i] = new Vector(matrix.rows[i]);
        }
    }

    public Matrix(double[][] matrixElements) {
        testOnZeroNumberOfElements(matrixElements);
        int maximum = matrixElements[0].length;
        for (int i = 1; i < matrixElements.length; i++) {
            maximum = Math.max(maximum, matrixElements[i].length);
        }
        this.rows = new Vector[matrixElements.length];
        for (int i = 0; i < matrixElements.length; i++) {
            this.rows[i] = new Vector(maximum, matrixElements[i]);
        }
    }

    public Matrix(Vector[] vectors) {
        int maximum = vectors[0].getSize();
        for (int i = 1; i < vectors.length; i++) {
            maximum = Math.max(maximum, vectors[i].getSize());
        }
        rows = new Vector[vectors.length];
        for (int i = 0; i < vectors.length; i++) {
            if (vectors[i].getSize() < maximum) {
                this.rows[i] = new Vector(maximum);
                this.rows[i].add(vectors[i]);
            } else {
                this.rows[i] = new Vector(vectors[i]);
            }
        }
    }

    public int getColumnsCount() {
        return this.rows[0].getSize();
    }

    public int getRowsCount() {
        return this.rows.length;
    }

    public String getSizeToString() {
        return getRowsCount() + " x " + getColumnsCount();
    }

    public Vector getRow(int rowIndex) {
        testIndexEntranceInNumberOfRows(rowIndex);
        return new Vector(rows[rowIndex]);
    }

    public void setRow(int rowIndex, Vector userVector) {
        testOnVectorSize(userVector);
        if (rowIndex == rows.length) {
            Vector[] tempArray = this.rows;
            this.rows = new Vector[this.rows.length + 1];
            this.rows = Arrays.copyOf(tempArray, this.rows.length);
            this.rows[rowIndex] = new Vector(this.getColumnsCount());
            this.rows[rowIndex].add(userVector);
            return;
        }
        testIndexEntranceInNumberOfRows(rowIndex);
        rows[rowIndex] = new Vector(userVector);
    }

    public Vector getColumn(int indexOfRow) {
        testIndexEntranceInNumberOfColumns(indexOfRow);
        double[] arrayOfColumnElements = new double[rows.length];
        for (int i = 0; i < rows.length; i++) {
            arrayOfColumnElements[i] = rows[i].getComponent(indexOfRow);
        }
        return new Vector(arrayOfColumnElements);
    }

    public void transpose() {
        Vector[] tempArray = new Vector[this.getColumnsCount()];
        for (int i = 0; i < this.getColumnsCount(); i++) {
            tempArray[i] = this.getColumn(i);
        }
        this.rows = tempArray;
    }

    public void scalarProduct(double scalar) {
        for (Vector row : this.rows) {
            row.scalarProduct(scalar);
        }
    }

    public double getDeterminant() {
        testSquareOfMatrix();
        double determinant;
        if (this.rows.length == 1) {
            determinant = this.rows[0].getComponent(0);
        } else if (this.rows.length == 2) {
            determinant = this.rows[0].getComponent(0) * this.rows[1].getComponent(1)
                    - this.rows[0].getComponent(1) * this.rows[1].getComponent(0);
        } else {
            Vector[] tempMatrix = new Vector[rows.length];
            for (int i = 0; i < rows.length; i++) {
                tempMatrix[i] = new Vector(rows[i]);
            }
            determinant = 1;
            for (int row = 0; row < tempMatrix.length - 1; row++) {
                for (int i = row + 1; i < tempMatrix.length; i++) {
                    if (tempMatrix[i].getComponent(row) != 0) {
                        double rate = tempMatrix[i].getComponent(row)
                                / tempMatrix[row].getComponent(row);
                        Vector tempVector = new Vector(tempMatrix[row]);
                        tempVector.scalarProduct(rate);
                        tempMatrix[i].subtract(tempVector);
                    }
                }
                determinant *= tempMatrix[row].getComponent(row);
            }
            determinant *= tempMatrix[tempMatrix.length - 1]
                    .getComponent(tempMatrix.length - 1);
        }
        return determinant;
    }

    @Override
    public String toString() {
        StringBuilder matrixToString = new StringBuilder();
        matrixToString.append("{ ");
        for (int i = 0; i < rows.length - 1; i++) {
            matrixToString.append(rows[i].toString()).append(", ");
        }
        matrixToString.append(rows[rows.length - 1].toString()).append(" }");
        return matrixToString.toString();
    }

    public Vector product(Vector userVector) {
        testOpportunityToProduct(userVector);
        Vector newVector = new Vector(rows.length);
        for (int i = 0; i < rows.length; i++) {
            newVector.setComponent(i, Vector.scalarProduct(userVector, rows[i]));
        }
        return newVector;
    }

    public void add(Matrix userMatrix) {
        testEqualSize(userMatrix);
        for (int i = 0; i < rows.length; i++) {
            this.rows[i].add(userMatrix.rows[i]);
        }
    }

    public void subtract(Matrix userMatrix) {
        testEqualSize(userMatrix);
        for (int i = 0; i < rows.length; i++) {
            this.rows[i].subtract(userMatrix.rows[i]);
        }
    }

    public static Matrix sum(Matrix matrix1, Matrix matrix2) {
        Matrix sumMatrix = new Matrix(matrix1);
        sumMatrix.add(matrix2);
        return sumMatrix;
    }

    public static Matrix subtract(Matrix matrix1, Matrix matrix2) {
        Matrix subtractMatrix = new Matrix(matrix1);
        subtractMatrix.subtract(matrix2);
        return subtractMatrix;
    }


    public static Matrix product(Matrix matrix1, Matrix matrix2) {
        testOpportunityToProduct(matrix1, matrix2);
        Matrix productMatrix = new Matrix(matrix1.rows.length, matrix2.getColumnsCount());
        for (int i = 0; i < matrix1.rows.length; i++) {
            for (int k = 0; k < matrix2.getColumnsCount(); k++) {
                double product = 0;
                for (int j = 0; j < matrix1.getColumnsCount(); j++) {
                    product += matrix1.rows[i].getComponent(j) * matrix2.rows[j].getComponent(k);

                }
                productMatrix.rows[i].setComponent(k, product);
            }
        }
        return productMatrix;
    }

    private void testIndexEntranceInNumberOfColumns(int index) {
        if (index < 0 || index >= this.getColumnsCount()) {
            throw new IllegalArgumentException("ОШИБКА: запрашиваемый индекс ("
                    + index + ") не найден, значение должно находиться в интервале [0, "
                    + this.getColumnsCount() + ")");
        }
    }

    private void testIndexEntranceInNumberOfRows(int index) {
        if (index < 0 || index >= this.rows.length) {
            throw new IllegalArgumentException("ОШИБКА: запрашиваемый индекс ("
                    + index + ") не найден, значение должно находиться в интервале [0, "
                    + this.rows.length + ")");
        }
    }

    private void testSquareOfMatrix() {
        if (this.rows.length != this.getColumnsCount()) {
            throw new IllegalArgumentException("ОШИБКА: матрица размером " + this.getSizeToString()
                    + " не квадратная");
        }
    }

    private void testEqualSize(Matrix userMatrix) {
        if (this.rows.length != userMatrix.rows.length || this.getColumnsCount() != userMatrix.getColumnsCount()) {
            throw new IllegalArgumentException("ОШИБКА: матрицы должны быть одинакового размера");
        }
    }

    private static void testOpportunityToProduct(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getColumnsCount() != matrix2.rows.length) {
            throw new IllegalArgumentException("ОШИБКА: количество столбцов матрицы 1"
                    + " должно совпадать с количеством строк матрицы 2");
        }
    }

    private void testOpportunityToProduct(Vector vector) {
        if (this.getColumnsCount() != vector.getSize()) {
            throw new IllegalArgumentException("ОШИБКА: количество столбцов матрицы"
                    + " должно совпадать с количеством строк элементов в векторе");
        }
    }

    private void testOnZeroNumberOfElements(int rowsCount, int columnCount) {
        if (rowsCount <= 0 || columnCount <= 0) {
            throw new IllegalArgumentException("ОШИБКА: количество элементов в строке/столбце, должно быть больше 0");
        }
    }

    private void testOnZeroNumberOfElements(double[][] userArray) {
        if (userArray.length == 0 || userArray[0].length == 0) {
            throw new IllegalArgumentException("ОШИБКА: количество элементов в строке/столбце, должно быть больше 0");
        }
    }

    private void testOnVectorSize(Vector vector) {
        if (vector.getSize() > this.getColumnsCount()) {
            throw new IllegalArgumentException("ОШИБКА: длина передаваемого вектора превышает допустимое значение");
        }
    }
}
