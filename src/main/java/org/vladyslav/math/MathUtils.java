package org.vladyslav.math;

public class MathUtils {

    public static Vector multiply(Vector vector, Matrix m) {
        return new VectorSnapshot(new Vector() {
            @Override
            public int size() {
                return m.height();
            }

            @Override
            public double get(int index) {
                double sum = 0.0;
                for (int i = 0; i < vector.size(); i++) {
                    sum += vector.get(i) * m.get(i, index);
                }
                return sum;
            }
        });
    }

    public static Matrix multiply(Matrix a, Matrix b) {
        return new MatrixSnapshot(new Matrix() {
            @Override
            public int width() {
                return a.width();
            }

            @Override
            public int height() {
                return b.height();
            }

            @Override
            public double get(int row, int col) {
                double sum = 0.0;
                for (int i = 0; i < a.height(); i++) {
                    sum += a.get(row, i) * b.get(i, col);
                }
                return sum;
            }
        });
    }

    public static Vector sigmoid(Vector v) {
        return new VectorSnapshot(new Vector() {
            @Override
            public int size() {
                return v.size();
            }

            @Override
            public double get(int index) {
                return 1.0 / (1.0 + Math.exp(-v.get(index)));
            }
        });
    }

    public static Matrix sigmoid(Matrix m) {
        return new MatrixSnapshot(new Matrix() {
            @Override
            public int width() {
                return m.width();
            }

            @Override
            public int height() {
                return m.height();
            }

            @Override
            public double get(int row, int col) {
                return 1.0 / (1.0 + Math.exp(-m.get(row, col)));
            }
        });
    }

    public static Vector sigmoidPrime(Vector v) {
        return new VectorSnapshot(new Vector() {
            @Override
            public int size() {
                return v.size();
            }

            @Override
            public double get(int index) {
                double exp = Math.exp(-v.get(index));
                return exp / ((exp + 1) * (exp + 1));
            }
        });
    }

    public static Vector minus(Vector a, Vector b) {
        return new VectorSnapshot(new Vector() {
            @Override
            public int size() {
                return a.size();
            }

            @Override
            public double get(int index) {
                return a.get(index) - b.get(index);
            }
        });
    }
}
