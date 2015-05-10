package org.vladyslav.math;

class VectorSnapshot extends AbstractVector {
    private final double[] a;

    public VectorSnapshot(Vector vector) {
        a = new double[vector.size()];
        for (int i = 0; i < a.length; i++) {
            a[i] = vector.get(i);
        }
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
