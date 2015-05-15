package org.vladyslav.math;

public interface Matrix {
    int cols();
    int rows();
    double get(int row, int col);
}
