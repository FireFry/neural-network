package org.vladyslav.math;

public class MatrixSnapshot extends AbstractMatrix {
    private final double[][] data;
    private final int height;
    private final int width;

    public MatrixSnapshot(Matrix matrix) {
        width = matrix.width();
        height = matrix.height();
        data = new double[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                data[row][col] = matrix.get(row, col);
            }
        }
    }

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
}