package org.vladyslav.math;

import java.util.Random;

public abstract class Matrix {

    /**
     * @return number of rows
     */
    public abstract int rows();

    /**
     * @return number of columns
     */
    public abstract int cols();

    /**
     * @return element specified position
     */
    public abstract double get(int r, int c);

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append("Matrix(").append(rows()).append(":").append(cols()).append(") [");
        for (int row = 0; row < rows(); row++) {
            b.append('[');
            for (int col = 0; col < cols() - 1; col++) {
                b.append(get(row, col)).append(" ");
            }
            b.append(get(row, cols() - 1));
            b.append(row < rows() - 1 ? "] " : "]");
        }
        return b.append(']').toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Matrix matrix = (Matrix) o;

        if (rows() != matrix.rows()) return false;
        if (cols() != matrix.cols()) return false;
        for (int r = 0; r < rows(); r++) {
            for (int c = 0; c < cols(); c++) {
                long mine = Double.doubleToLongBits(get(r, c));
                long others = Double.doubleToLongBits(matrix.get(r, c));
                if (mine != others) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = rows();
        result = 31 * result + cols();
        for (int r = 0; r < rows(); r++) {
            for (int c = 0; c < cols(); c++) {
                long bits = Double.doubleToLongBits(get(r, c));
                result = 31 * result + (int)(bits ^ (bits >>> 32));
            }
        }
        return result;
    }

    public static Matrix copy(Matrix matrix) {
        final int rows = matrix.rows();
        final int cols = matrix.cols();
        final double[][] data = new double[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                data[row][col] = matrix.get(row, col);
            }
        }
        return view(rows, cols, (r, c) -> data[r][c]);
    }

    public static Matrix matrix(int rows, int cols, GetMethod getMethod) {
        return copy(view(rows, cols, getMethod));
    }

    public static Matrix view(int rows, int cols, GetMethod getMethod) {
        return new Matrix() {
            @Override
            public int rows() {
                return rows;
            }

            @Override
            public int cols() {
                return cols;
            }

            @Override
            public double get(int r, int c) {
                return getMethod.get(r, c);
            }
        };
    }

    public static Matrix random(Random random, int rows, int cols) {
        return matrix(rows, cols, (r, c) -> random.nextDouble());
    }

    public static Matrix create(int rows, int cols, double value) {
        return matrix(rows, cols, (r, c) -> value);
    }

    public static Matrix row(double... values) {
        return matrix(1, values.length, (r, c) -> values[c]);
    }

    public static Matrix combineRows(Matrix firstRow, Matrix... otherRows) {
        return matrix(
                otherRows.length + 1,
                firstRow.cols(),
                (r, c) -> r < 1 ? firstRow.get(0, c) : otherRows[r - 1].get(0, c));
    }

    public static Matrix rangeRow(double from, double to, double step) {
        final int size = (int) Math.ceil((to - from) / step);
        return matrix(1, size, (r, c) -> from + c * step);
    }

    public Matrix addBias() {
        return view(rows(), cols() + 1, (r, c) -> (c == 0) ? 1.0 : get(r, c - 1));
    }

    public Matrix removeBias() {
        return view(rows(), cols() - 1, (r, c) -> get(r, c + 1));
    }

    public Matrix multiply(Matrix other) {
        requires(this, other, cols() == other.rows());
        return matrix(rows(), other.cols(), (r, c) -> {
            double sum = 0.0;
            for (int i = 0; i < cols(); i++) {
                sum += get(r, i) * other.get(i, c);
            }
            return sum;
        });
    }

    public Matrix minus(Matrix other) {
        requires(this, other, cols() == other.cols() && rows() == other.rows());
        return matrix(rows(), cols(), (r, c) -> get(r, c) - other.get(r, c));
    }

    public Matrix plus(Matrix other) {
        requires(this, other, cols() == other.cols() && rows() == other.rows());
        return matrix(rows(), cols(), (r, c) -> get(r, c) + other.get(r, c));
    }

    public Matrix transpose() {
        return view(cols(), rows(), (r, c) -> get(c, r));
    }

    public Matrix product(Matrix other) {
        requires(this, other, cols() == other.cols() && rows() == other.rows());
        return matrix(rows(), cols(), (r, c) -> get(r, c) * other.get(r, c));
    }

    public Matrix applyPolynomial(double... polynomialCoefficients) {
        return matrix(rows(), cols(), (row, col) -> {
            double exp = 1.0;
            double sum = 0;
            double x = get(row, col);
            for (double coefficient : polynomialCoefficients) {
                sum += coefficient * exp;
                exp *= x;
            }
            return sum;
        });
    }

    public Matrix apply(Function function) {
        return matrix(rows(), cols(), (r, c) -> function.apply(get(r, c)));
    }

    public double average() {
        return sum() / cols() / rows();
    }

    public double sum() {
        double sum = 0;
        for (int row = 0; row < rows(); row++) {
            for (int col = 0; col < cols(); col++) {
                sum += get(row, col);
            }
        }
        return sum;
    }

    public interface Function {
        double apply(double x);
    }

    public interface GetMethod {
        double get(int r, int c);
    }

    private static void requires(Matrix a, Matrix b, boolean valid) {
        if (!valid) {
            throw new IllegalArgumentException("Incompatible matrices " +
                    a.rows() + ":" + a.cols() + " and " +
                    b.rows() + ":" + b.cols());
        }
    }
}