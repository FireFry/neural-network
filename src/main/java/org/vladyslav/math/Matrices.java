package org.vladyslav.math;

import java.util.Random;

public class Matrices {
    public static Matrix random(Random random, int rows, int cols) {
        return new MatrixSnapshot(new Matrix() {
            @Override
            public int cols() {
                return cols;
            }

            @Override
            public int rows() {
                return rows;
            }

            @Override
            public double get(int row, int col) {
                return random.nextDouble();
            }
        });
    }

    public static Matrix create(double[][] data, int rows, int cols) {
        return new MatrixSnapshot(new Matrix() {
            @Override
            public int cols() {
                return cols;
            }

            @Override
            public int rows() {
                return rows;
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
            public int cols() {
                return x.cols() + 1;
            }

            @Override
            public int rows() {
                return x.rows();
            }

            @Override
            public double get(int row, int col) {
                return col == 0 ? 1.0 : x.get(row, col - 1);
            }
        };
    }
}
