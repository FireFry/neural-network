package org.vladyslav.math;

public class MathUtils {

    private static double sigmoid(double x) {
        return 1.0 / (1.0 + Math.exp(-x));
    }

    private static double sigmoidDerivative(double x) {
        return sigmoid(x) * (1 - sigmoid(x));
    }

    public static Matrix multiply(Matrix a, Matrix b) {
        if (a.cols() != b.rows()) {
            throw new IllegalArgumentException();
        }
        return new MatrixSnapshot(new Matrix() {
            @Override
            public int cols() {
                return b.cols();
            }

            @Override
            public int rows() {
                return a.rows();
            }

            @Override
            public double get(int row, int col) {
                double sum = 0.0;
                for (int i = 0; i < a.cols(); i++) {
                    sum += a.get(row, i) * b.get(i, col);
                }
                return sum;
            }
        });
    }

    public static Matrix sigmoid(Matrix m) {
        return new MatrixSnapshot(new Matrix() {
            @Override
            public int cols() {
                return m.cols();
            }

            @Override
            public int rows() {
                return m.rows();
            }

            @Override
            public double get(int row, int col) {
                return sigmoid(m.get(row, col));
            }
        });
    }

    public static Matrix minus(Matrix a, Matrix b) {
        if (a.cols() != b.cols() || a.rows() != b.rows()) {
            throw new IllegalArgumentException();
        }
        return new MatrixSnapshot(new Matrix() {
            @Override
            public int cols() {
                return a.cols();
            }

            @Override
            public int rows() {
                return a.rows();
            }

            @Override
            public double get(int row, int col) {
                return a.get(row, col) - b.get(row, col);
            }
        });
    }

    public static Matrix transpose(Matrix m) {
        return new AbstractMatrix() {
            @Override
            public int cols() {
                return m.rows();
            }

            @Override
            public int rows() {
                return m.cols();
            }

            @Override
            public double get(int row, int col) {
                return m.get(col, row);
            }
        };
    }

    public static MatrixSnapshot sigmoidDerivative(Matrix m) {
        return new MatrixSnapshot(new Matrix() {
            @Override
            public int cols() {
                return m.cols();
            }

            @Override
            public int rows() {
                return m.rows();
            }

            @Override
            public double get(int row, int col) {
                return sigmoidDerivative(m.get(row, col));
            }
        });
    }

    public static Matrix product(Matrix a, Matrix b) {
        if (a.cols() != b.cols() || a.rows() != b.rows()) {
            throw new IllegalArgumentException();
        }
        return new MatrixSnapshot(new Matrix() {
            @Override
            public int cols() {
                return a.cols();
            }

            @Override
            public int rows() {
                return a.rows();
            }

            @Override
            public double get(int row, int col) {
                return a.get(row, col) * b.get(row, col);
            }
        });
    }

    public static Matrix product(Matrix a, double d) {
        return new MatrixSnapshot(new Matrix() {
            @Override
            public int cols() {
                return a.cols();
            }

            @Override
            public int rows() {
                return a.rows();
            }

            @Override
            public double get(int row, int col) {
                return a.get(row, col) * d;
            }
        });
    }
}
