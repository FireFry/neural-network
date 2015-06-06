package org.vladyslav.math;

import java.util.Random;

/**
 * Represents a two-dimensional matrix. In general this class is designed to represent immutable matrix. Some of
 * default methods create new version of matrix data, other creates new view of given matrix. According to this
 * behaviour may be unpredictable for mutable Matrix implementations.
 */
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
     * @return element on specified position
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

    /**
     * Creates a copy of given matrix. Changes of original matrix won't affect its copy.
     */
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

    /**
     * Creates a matrix with given behaviour. GetMethod is invoked only once for each element and is not used after
     * method returns. If it's desired for getMethod to be called each time get() method of the result matrix is
     * invoked consider using Matrix.view() method instead.
     */
    public static Matrix matrix(int rows, int cols, GetMethod getMethod) {
        return copy(view(rows, cols, getMethod));
    }

    /**
     * Creates a matrix with given behaviour. GetMethod is invoked each time get() method of the result matrix is
     * called. Since such behaviour might cause performance problems, consider using matrix() method, which caches
     * results in memory, instead.
     */
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

    /**
     * Generates a matrix with given size and random values in range between 0.0 and 1.0.
     */
    public static Matrix random(Random random, int rows, int cols) {
        return matrix(rows, cols, (r, c) -> random.nextDouble());
    }

    /**
     * Creates a matrix with given size filled with given value.
     */
    public static Matrix fill(int rows, int cols, double value) {
        return view(rows, cols, (r, c) -> value);
    }

    /**
     * Creates a single-row matrix filled with given values. Is often used in pair with combineRows() method, which is
     * a convenient way to declare two-dimensional matrix. To create a vector (column) transpose the result matrix.
     */
    public static Matrix row(double... values) {
        return matrix(1, values.length, (r, c) -> values[c]);
    }

    /**
     * Used in combination with row() method to create a two-dimensional matrix. Behaviour of this method is undefined
     * if input matrices are not single-rows matrices with the same size.
     */
    public static Matrix combineRows(Matrix firstRow, Matrix... otherRows) {
        return matrix(
                otherRows.length + 1,
                firstRow.cols(),
                (r, c) -> r < 1 ? firstRow.get(0, c) : otherRows[r - 1].get(0, c));
    }

    /**
     * Creates a single-row matrix with values in range [from..to] with given step.
     */
    public static Matrix rangeRow(double from, double to, double step) {
        final int size = (int) Math.ceil((to - from) / step);
        return matrix(1, size, (r, c) -> from + c * step);
    }

    /**
     * Adds a new column filled with value 1.0 in front of the matrix. Resulting matrix has the same amount of rows and
     * an extra column with index 0. Other columns indexes have value increased by 1.
     */
    public Matrix addBias() {
        return view(rows(), cols() + 1, (r, c) -> (c == 0) ? 1.0 : get(r, c - 1));
    }

    /**
     * Removes a column with index 0 from given matrix. This method makes sense only in pair with addBias() method.
     */
    public Matrix removeBias() {
        return view(rows(), cols() - 1, (r, c) -> get(r, c + 1));
    }

    /**
     * Performs matrix multiplication.
     */
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

    /**
     * Element-wise minus operation. Both matrices should have the same size.
     */
    public Matrix minus(Matrix other) {
        requires(this, other, cols() == other.cols() && rows() == other.rows());
        return matrix(rows(), cols(), (r, c) -> get(r, c) - other.get(r, c));
    }

    /**
     * Element-wise plus operation. Both matrices should have the same size.
     */
    public Matrix plus(Matrix other) {
        requires(this, other, cols() == other.cols() && rows() == other.rows());
        return matrix(rows(), cols(), (r, c) -> get(r, c) + other.get(r, c));
    }

    /**
     * Transposes matrix.
     */
    public Matrix transpose() {
        return view(cols(), rows(), (r, c) -> get(c, r));
    }

    /**
     * Element-wise multiplication. Both matrices should have the same size.
     */
    public Matrix product(Matrix other) {
        requires(this, other, cols() == other.cols() && rows() == other.rows());
        return matrix(rows(), cols(), (r, c) -> get(r, c) * other.get(r, c));
    }

    /**
     * Applies polynomial function to each element of given matrix. Index of each coefficient corresponds to
     * the exponent of it's multiplier.
     */
    public Matrix polynomial(double... coefficients) {
        return map(new Polynomial(coefficients));
    }

    /**
     * Applies given function to each element of the matrix.
     */
    public Matrix map(Function function) {
        return matrix(rows(), cols(), (r, c) -> function.apply(get(r, c)));
    }

    /**
     * Finds average of set of all matrix elements.
     */
    public double average() {
        return sum() / cols() / rows();
    }

    /**
     * Sums all matrix elements.
     */
    public double sum() {
        double sum = 0;
        for (int row = 0; row < rows(); row++) {
            for (int col = 0; col < cols(); col++) {
                sum += get(row, col);
            }
        }
        return sum;
    }

    private static void requires(Matrix a, Matrix b, boolean valid) {
        if (!valid) {
            throw new IllegalArgumentException("Incompatible matrices " +
                    a.rows() + ":" + a.cols() + " and " +
                    b.rows() + ":" + b.cols());
        }
    }
}