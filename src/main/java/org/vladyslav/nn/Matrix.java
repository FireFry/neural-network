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

    public static Matrix create(double[][] data) {
        return copy(new Matrix() {
            public int rows() { return data.length; }
            public int cols() { return data.length == 0 ? 0 : data[0].length; }
            public double get(int row, int col) { return data[row][col]; }
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

    public Matrix multiply(Matrix b) { Matrix a = this;
        if (a.cols() != b.rows()) throw new IllegalArgumentException();
        return copy(new Matrix() {
            public int rows() { return a.rows(); }
            public int cols() { return b.cols(); }
            public double get(int row, int col) {
                double sum = 0.0;
                for (int i = 0; i < cols(); i++) {
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
        if (a.cols() != b.cols() || a.rows() != b.rows()) throw new IllegalArgumentException();
        return copy(new Matrix() {
            public int rows() { return a.rows(); }
            public int cols() { return a.cols(); }
            public double get(int row, int col) { return a.get(row, col) - b.get(row, col); }
        });
    }

    public Matrix plus(Matrix b) { Matrix a = this;
        if (a.cols() != b.cols() || a.rows() != b.rows()) throw new IllegalArgumentException();
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
        if (a.cols() != b.cols() || a.rows() != b.rows()) throw new IllegalArgumentException();
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
}
