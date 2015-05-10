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
}
