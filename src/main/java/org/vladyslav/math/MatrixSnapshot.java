package org.vladyslav.math;

public class MatrixSnapshot extends AbstractMatrix {
    private final double[][] data;
    private final int rows;
    private final int cols;

    public MatrixSnapshot(Matrix matrix) {
        cols = matrix.cols();
        rows = matrix.rows();
        data = new double[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                data[row][col] = matrix.get(row, col);
            }
        }
    }

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
}
