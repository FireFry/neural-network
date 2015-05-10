package org.vladyslav.nn;

import org.vladyslav.math.Vector;

public class TrainingData {
    public final Vector input;
    public final Vector output;

    public TrainingData(Vector input, Vector output) {
        this.input = input;
        this.output = output;
    }
}
