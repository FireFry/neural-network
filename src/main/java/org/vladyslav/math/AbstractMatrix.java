package org.vladyslav.math;

public abstract class AbstractMatrix implements Matrix {
    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append('{');
        for (int col = 0; col < height(); col++) {
            b.append('{');
            for (int row = 0; row < width(); row++) {
                b.append(get(row, col));
                if (row < width() - 1) {
                    b.append(", ");
                }
            }
            b.append('}');
        }
        b.append('}');
        return b.toString();
    }
}
