package org.vladyslav.nn;

import org.vladyslav.math.Matrix;

public class Perceptron {
    private Matrix weight;

    public Perceptron(int numberOfFutures) {
        weight = Matrix.create(numberOfFutures + 1, 1, 0.0);
    }

    public Matrix forward(Matrix input) {
        return normalize(input.addBias().multiply(weight));
    }

    public boolean train(Matrix input, Matrix output) {
        Matrix prediction = forward(input);
        if (normalize(output).equals(prediction)) {
            return true;
        }
        Matrix correction = Matrix.matrix(output.rows(), output.cols(), (r, c) -> {
            boolean expected = output.get(r, c) > 0;
            boolean actual = prediction.get(c, c) > 0;
            return expected == actual ? 0.0 : expected ? 1.0 : -1.0;
        });
        weight = input.addBias().transpose().multiply(correction).plus(weight);
        return false;
    }

    private static Matrix normalize(Matrix output) {
        return Matrix.view(output.rows(), output.cols(), (r, c) -> output.get(r, c) > 0 ? 1 : 0);
    }
}