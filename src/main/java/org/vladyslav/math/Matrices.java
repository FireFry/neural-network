package org.vladyslav.math;

import java.util.Random;

public class Matrices {
    public static Matrix random(Random random, int width, int height) {
        return new MatrixSnapshot(new Matrix() {
            @Override
            public int width() {
                return width;
            }

            @Override
            public int height() {
                return height;
            }

            @Override
            public double get(int row, int col) {
                return random.nextDouble();
            }
        });
    }

    public static Matrix view(Vector vector) {
        return new AbstractMatrix() {
            @Override
            public int width() {
                return 1;
            }

            @Override
            public int height() {
                return vector.size();
            }

            @Override
            public double get(int row, int col) {
                return vector.get(col);
            }
        };
    }

    public static Matrix create(double[][] data, int width, int height) {
        return new MatrixSnapshot(new Matrix() {
            @Override
            public int width() {
                return width;
            }

            @Override
            public int height() {
                return height;
            }

            @Override
            public double get(int row, int col) {
                return data[row][col];
            }
        });
    }

    public static Matrix bias(Matrix x) {
        return new AbstractMatrix() {
            @Override
            public int width() {
                return x.width() + 1;
            }

            @Override
            public int height() {
                return x.height();
            }

            @Override
            public double get(int row, int col) {
                return row == 0 ? 1.0 : x.get(row - 1, col);
            }
        };
    }
}
