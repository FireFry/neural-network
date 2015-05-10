package org.vladyslav.math;

public class Vectors {
    public static Vector create(double... values) {
        return new ArrayVector(values);
    }

    public static Vector bias(Vector vector) {
        return new VectorSnapshot(new Vector() {
            @Override
            public int size() {
                return vector.size() + 1;
            }

            @Override
            public double get(int index) {
                return index == 0 ? 1d : vector.get(index - 1);
            }
        });
    }
}
