package org.vladyslav.nn;

import java.util.Random;

public abstract class Matrix {
    public abstract int rows();
    public abstract int cols();
    public abstract double get(int row, int col);

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

    public static Matrix copy(Matrix matrix) {
        final int rows = matrix.rows();
        final int cols = matrix.cols();
        final double[][] data = new double[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                data[row][col] = matrix.get(row, col);
            }
        }
        return new Matrix() {
            public int rows() { return rows; }
            public int cols() { return cols; }
            public double get(int row, int col) { return data[row][col]; }
        };
    }

    public static Matrix random(Random random, int rows, int cols) {
        return copy(new Matrix() {
            public int rows() { return rows; }
            public int cols() { return cols; }
            public double get(int row, int col) { return random.nextDouble(); }
        });
    }

    public static Matrix of(int rows, int cols, double value) {
        return copy(new Matrix() {
            public int rows() { return rows; }
            public int cols() { return cols; }
            public double get(int row, int col) { return value; }
        });
    }

    public static Matrix row(double... values) {
        return new Matrix() {
            public int rows() { return 1; }
            public int cols() { return values.length; }
            public double get(int row, int col) { return values[col]; }
        };
    }

    public static Matrix combineRows(Matrix firstRow, Matrix... otherRows) {
        return copy(new Matrix() {
            public int rows() { return 1 + otherRows.length; }
            public int cols() { return firstRow.cols(); }
            public double get(int row, int col) {
                return row < 1 ? firstRow.get(0, col) : otherRows[row - 1].get(0, col);
            }
        });
    }

    public static Matrix range(double from, double to, double step) {
        final int size = (int) Math.ceil((to - from) / step);
        return copy(new Matrix() {
            public int rows() { return 1; }
            public int cols() { return size; }
            public double get(int row, int col) { return from + col * step; }
        });
    }

    public static double sigmoid(double x) {
        return 1.0 / (1.0 + Math.exp(-x));
    }

    public static double sigmoidDerivative(double x) {
        double sigmoid = sigmoid(x);
        return sigmoid * (1 - sigmoid);
    }

    public Matrix addBias() { Matrix a = this;
        return new Matrix() {
            public int rows() { return a.rows(); }
            public int cols() { return a.cols() + 1; }
            public double get(int row, int col) { return col == 0 ? 1.0 : a.get(row, col - 1); }
        };
    }

    public Matrix removeBias() { Matrix a = this;
        return new Matrix() {
            public int rows() { return a.rows(); }
            public int cols() { return a.cols() - 1; }
            public double get(int row, int col) { return a.get(row, col + 1); }
        };
    }

    public Matrix multiply(Matrix b) { Matrix a = this;
        if (a.cols() != b.rows()) return incompatible(a, b);
        return copy(new Matrix() {
            public int rows() { return a.rows(); }
            public int cols() { return b.cols(); }
            public double get(int row, int col) {
                double sum = 0.0;
                for (int i = 0; i < a.cols(); i++) {
                    sum += a.get(row, i) * b.get(i, col);
                }
                return sum;
            }
        });
    }

    public Matrix sigmoid() { Matrix a = this;
        return copy(new Matrix() {
            public int rows() { return a.rows(); }
            public int cols() { return a.cols(); }
            public double get(int row, int col) { return sigmoid(a.get(row, col)); }
        });
    }

    public Matrix minus(Matrix b) { Matrix a = this;
        if (a.cols() != b.cols() || a.rows() != b.rows()) return incompatible(a, b);
        return copy(new Matrix() {
            public int rows() { return a.rows(); }
            public int cols() { return a.cols(); }
            public double get(int row, int col) { return a.get(row, col) - b.get(row, col); }
        });
    }

    public Matrix plus(Matrix b) { Matrix a = this;
        if (a.cols() != b.cols() || a.rows() != b.rows()) return incompatible(a, b);
        return copy(new Matrix() {
            public int rows() { return a.rows(); }
            public int cols() { return a.cols(); }
            public double get(int row, int col) { return a.get(row, col) + b.get(row, col); }
        });
    }

    public Matrix transpose() { Matrix a = this;
        return new Matrix() {
            public int rows() { return a.cols(); }
            public int cols() { return a.rows(); }
            public double get(int row, int col) { return a.get(col, row); }
        };
    }

    public Matrix sigmoidDerivative() { Matrix a = this;
        return copy(new Matrix() {
            public int rows() { return a.rows(); }
            public int cols() { return a.cols(); }
            public double get(int row, int col) { return sigmoidDerivative(a.get(row, col)); }
        });
    }

    public Matrix product(Matrix b) { Matrix a = this;
        if (a.cols() != b.cols() || a.rows() != b.rows()) return incompatible(a, b);
        return copy(new Matrix() {
            public int rows() { return a.rows(); }
            public int cols() { return a.cols(); }
            public double get(int row, int col) { return a.get(row, col) * b.get(row, col); }
        });
    }

    public Matrix product(double d) { Matrix a = this;
        return copy(new Matrix() {
            public int rows() { return a.rows(); }
            public int cols() { return a.cols(); }
            public double get(int row, int col) { return a.get(row, col) * d; }
        });
    }

    private static Matrix incompatible(Matrix a, Matrix b) {
        throw new IllegalArgumentException("Incompatible matrices " +
                a.rows() + ":" + a.cols() + " and " +
                b.rows() + ":" + b.cols() + ": " +
                "a = " + a + ", " +
                "b = " + b);
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
}
