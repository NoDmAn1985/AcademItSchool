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
    private int amountOfRows;
    private int amountOfLines;
    private Vector[] matrixLines;

    public Matrix(int amountOfLines, int amountOfRows) {
        testOnZeroNumberOfElements(amountOfRows, amountOfLines);
        this.amountOfRows = amountOfRows;
        this.amountOfLines = amountOfLines;
        matrixLines = new Vector[this.amountOfLines];
        for (int i = 0; i < amountOfLines; i++) {
            matrixLines[i] = new Vector(amountOfRows);
        }
    }

    public Matrix(Matrix matrix) {
        this.amountOfRows = matrix.amountOfRows;
        this.amountOfLines = matrix.amountOfLines;
        this.matrixLines = new Vector[this.amountOfLines];
        for (int i = 0; i < this.amountOfLines; i++) {
            this.matrixLines[i] = new Vector(matrix.matrixLines[i]);
        }
    }

    public Matrix(double[][] matrixElements) {
        testOnZeroNumberOfElements(matrixElements);
        this.amountOfLines = matrixElements.length;
        int maximum = matrixElements[0].length;
        for (int i = 1; i < this.amountOfLines; i++) {
            maximum = Math.max(maximum, matrixElements[i].length);
        }
        this.amountOfRows = maximum;
        this.matrixLines = new Vector[amountOfLines];
        for (int i = 0; i < this.amountOfLines; i++) {
            this.matrixLines[i] = new Vector(this.amountOfRows, matrixElements[i]);
        }
    }

    public Matrix(Vector[] vectors) {
        this.amountOfLines = vectors.length;
        int maximum = vectors[0].getSize();
        for (int i = 1; i < this.amountOfLines; i++) {
            maximum = Math.max(maximum, vectors[i].getSize());
        }
        this.amountOfRows = maximum;
        matrixLines = new Vector[amountOfLines];
        for (int i = 0; i < this.amountOfLines; i++) {
            if (vectors[i].getSize() < this.amountOfRows) {
                this.matrixLines[i] = new Vector(amountOfRows);
                this.matrixLines[i].add(vectors[i]);
            } else {
                this.matrixLines[i] = new Vector(vectors[i]);
            }
        }
    }

    public int getAmountOfRows() {
        return amountOfRows;
    }

    public int getAmountOfLines() {
        return amountOfLines;
    }

    public String getSizeToString() {
        return amountOfLines + " x " + amountOfRows;
    }

    public Vector getLine(int indexOfLine) {
        testIndexEntranceInNumberOfLines(indexOfLine);
        return matrixLines[indexOfLine];
    }

    public void setLine(int indexOfLine, Vector userVector) {
        if (indexOfLine == amountOfLines) {
            Vector[] tempArray = this.matrixLines;
            this.amountOfLines = indexOfLine + 1;
            this.matrixLines = new Vector[amountOfLines];
            this.matrixLines = Arrays.copyOf(tempArray, amountOfLines);
            this.matrixLines[indexOfLine] = new Vector(amountOfRows);
            this.matrixLines[indexOfLine].add(userVector);
            return;
        }
        testIndexEntranceInNumberOfLines(indexOfLine);
        matrixLines[indexOfLine] = new Vector(userVector);
    }

    public Vector getRow(int indexOfRow) {
        testIndexEntranceInNumberOfRows(indexOfRow);
        double[] arrayOfRowElements = new double[amountOfLines];
        for (int i = 0; i < amountOfLines; i++) {
            arrayOfRowElements[i] = matrixLines[i].getComponent(indexOfRow);
        }
        return new Vector(arrayOfRowElements);
    }

    public void transpose() {
        Matrix transposedMatrix = new Matrix(this.amountOfRows, this.amountOfLines);
        for (int i = 0; i < this.amountOfRows; i++) {
            transposedMatrix.setLine(i, this.getRow(i));
        }
        this.amountOfLines = transposedMatrix.amountOfLines;
        this.amountOfRows = transposedMatrix.amountOfRows;
        this.matrixLines = Arrays.copyOf(transposedMatrix.matrixLines, this.amountOfLines);
    }

    public void scalarProduct(double scalar) {
        for (int i = 0; i < amountOfLines; i++) {
            this.matrixLines[i].scalarProduct(scalar);
        }
    }

    public double getDeterminant() {
        testSquareOfMatrix();
        double determinant = 0;
        if (this.amountOfLines <= 3) {
            double[] productOfElementsInDiagonalDown = new double[amountOfRows];
            Arrays.fill(productOfElementsInDiagonalDown, 1.0);
            double[] productOfElementsInDiagonalUp = new double[amountOfRows];
            Arrays.fill(productOfElementsInDiagonalUp, 1.0);
            for (int index = 0; index < amountOfRows; index++) {
                for (int line = 0; line < this.amountOfRows; line++) {
                    int posForDiagonalDown = (index + line < this.amountOfRows) ? index + line : index + line - amountOfRows;
                    int posForDiagonalUp = (index - line >= 0) ? index - line : index - line + this.amountOfRows;
                    productOfElementsInDiagonalDown[index] *= matrixLines[line].getComponent(posForDiagonalDown);
                    productOfElementsInDiagonalUp[index] *= matrixLines[line].getComponent(posForDiagonalUp);
                }
                determinant = determinant + productOfElementsInDiagonalDown[index] - productOfElementsInDiagonalUp[index];
            }
        } else if (this.amountOfLines > 3) {
            Matrix tempMatrix = new Matrix(this);
            determinant = 1;
            for (int row = 0; row < tempMatrix.amountOfRows - 1; row++) {
                for (int i = row + 1; i < tempMatrix.amountOfLines; i++) {
                    if (tempMatrix.matrixLines[i].getComponent(row) != 0) {
                        double rate = tempMatrix.matrixLines[i].getComponent(row)
                                / tempMatrix.matrixLines[row].getComponent(row);
                        Vector tempVector = new Vector(tempMatrix.matrixLines[row]);
                        tempVector.scalarProduct(rate);
                        tempMatrix.matrixLines[i].subtract(tempVector);
                    }
                }
                determinant *= tempMatrix.matrixLines[row].getComponent(row);
            }
            determinant *= tempMatrix.matrixLines[tempMatrix.amountOfLines - 1]
                    .getComponent(tempMatrix.amountOfLines - 1);
        }
        return Math.round(determinant);
    }

    @Override
    public String toString() {
        StringBuilder matrixToString = new StringBuilder();
        matrixToString.append("{ ");
        for (int i = 0; i < amountOfLines - 1; i++) {
            matrixToString.append(matrixLines[i].toString()).append(", ");
        }
        matrixToString.append(matrixLines[amountOfLines - 1].toString()).append(" }");
        return matrixToString.toString();
    }

    public Vector productWithVector(Vector userVector) {
        testOpportunityToProduct(userVector);
        Vector newVector = new Vector(this.amountOfLines);
        for (int i = 0; i < this.amountOfLines; i++) {
            newVector.setComponent(i, Vector.scalarProduct(userVector, matrixLines[i]));
        }
        return newVector;
    }

    public void add(Matrix userMatrix) {
        testEqualSize(userMatrix);
        for (int i = 0; i < amountOfLines; i++) {
            this.matrixLines[i].add(userMatrix.matrixLines[i]);
        }
    }

    public void subtract(Matrix userMatrix) {
        testEqualSize(userMatrix);
        for (int i = 0; i < amountOfLines; i++) {
            this.matrixLines[i].subtract(userMatrix.matrixLines[i]);
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
        Matrix productMatrix = new Matrix(matrix1.amountOfLines, matrix2.amountOfRows);
        for (int i = 0; i < matrix1.amountOfLines; i++) {
            for (int j = 0; j < matrix2.amountOfRows; j++) {
                productMatrix.getLine(i).setComponent(j, Vector.scalarProduct(matrix1.getLine(i), matrix2.getRow(j)));
            }
        }
        return productMatrix;
    }

    private void testIndexEntranceInNumberOfRows(int index) {
        if (index < 0 || index >= amountOfRows) {
            throw new IllegalArgumentException("ОШИБКА: запрашиваемый индекс ("
                    + index + ") не найден, значение должно находиться в интервале [0, "
                    + amountOfRows + ")");
        }
    }

    private void testIndexEntranceInNumberOfLines(int index) {
        if (index < 0 || index >= amountOfLines) {
            throw new IllegalArgumentException("ОШИБКА: запрашиваемый индекс ("
                    + index + ") не найден, значение должно находиться в интервале [0, "
                    + amountOfLines + ")");
        }
    }

    private void testSquareOfMatrix() {
        if (this.amountOfLines != this.amountOfRows) {
            throw new IllegalArgumentException("ОШИБКА: матрица размером " + this.getSizeToString()
                    + " не квадратная");
        }
    }

    private void testEqualSize(Matrix userMatrix) {
        if (this.amountOfLines != userMatrix.amountOfLines || this.amountOfRows != userMatrix.amountOfRows) {
            throw new IllegalArgumentException("ОШИБКА: матрицы должны быть одинакового размера");
        }
    }

    private static void testOpportunityToProduct(Matrix matrix1, Matrix matrix2) {
        if (matrix1.amountOfRows != matrix2.amountOfLines) {
            throw new IllegalArgumentException("ОШИБКА: количество столбцов матрицы 1"
                    + " должно совпадать с количеством строк матрицы 2");
        }
    }

    private void testOpportunityToProduct(Vector vector) {
        if (this.amountOfRows != vector.getSize()) {
            throw new IllegalArgumentException("ОШИБКА: количество столбцов матрицы"
                    + " должно совпадать с количеством строк элементов в векторе");
        }
    }

    private void testOnZeroNumberOfElements(int amountOfRows, int amountOfLines) {
        if (amountOfRows <= 0 || amountOfLines <= 0) {
            throw new IllegalArgumentException("ОШИБКА: количество элементов в строке/столбце, должно быть больше 0");
        }
    }

    private void testOnZeroNumberOfElements(double[][] userArray) {
        if (userArray.length == 0 || userArray[0].length == 0) {
            throw new IllegalArgumentException("ОШИБКА: количество элементов в строке/столбце, должно быть больше 0");
        }
    }
}
