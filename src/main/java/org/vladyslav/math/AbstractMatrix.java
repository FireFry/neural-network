package org.vladyslav.math;

import java.util.Formatter;

public abstract class AbstractMatrix implements Matrix {
    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append("Matrix(");
        b.append(rows());
        b.append(":");
        b.append(cols());
        b.append(") [");
        for (int row = 0; row < rows(); row++) {
            b.append('[');
            for (int col = 0; col < cols(); col++) {
                b.append(new Formatter().format("%.2f", get(row, col)));
                if (col < cols() - 1) {
                    b.append(" ");
                }
            }
            b.append("]");
            if (row < rows() - 1) {
                b.append(" ");
            }
        }
        b.append(']');
        return b.toString();
    }
}
