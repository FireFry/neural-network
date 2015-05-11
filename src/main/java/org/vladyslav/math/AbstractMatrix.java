package org.vladyslav.math;

public abstract class AbstractMatrix implements Matrix {
    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append("Matrix[");
        b.append(height());
        b.append(":");
        b.append(width());
        b.append("]");
        b.append('{');
        for (int row = 0; row < height(); row++) {
            b.append('{');
            for (int col = 0; col < width(); col++) {
                b.append(get(row, col));
                if (col < width() - 1) {
                    b.append(", ");
                }
            }
            b.append('}');
        }
        b.append('}');
        return b.toString();
    }
}
