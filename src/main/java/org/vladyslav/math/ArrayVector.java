package org.vladyslav.math;

import java.util.Arrays;

class ArrayVector extends AbstractVector {
    private final double[] a;

    public ArrayVector(double[] a) {
        this.a = Arrays.copyOf(a, a.length);
    }

    @Override
    public int size() {
        return a.length;
    }

    @Override
    public double get(int index) {
        return a[index];
    }
}
