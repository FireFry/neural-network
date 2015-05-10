package org.vladyslav.math;

public interface Matrix {
    int width();
    int height();
    double get(int row, int col);
}
