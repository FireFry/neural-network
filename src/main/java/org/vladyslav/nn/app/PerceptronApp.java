package org.vladyslav.nn.app;

import org.vladyslav.math.Matrix;
import org.vladyslav.nn.Perceptron;

public class PerceptronApp {
    private static final Matrix BINARY_INPUT = Matrix.combineRows(
            Matrix.row(0, 0),
            Matrix.row(0, 1),
            Matrix.row(1, 0),
            Matrix.row(1, 1)
    );

    private static final Matrix AND_OUTPUT = Matrix.combineRows(
            Matrix.row(0 & 0),
            Matrix.row(0 & 1),
            Matrix.row(1 & 0),
            Matrix.row(1 & 1)
    );

    public static void main(String[] args) {
        Perceptron perceptron = new Perceptron(2);
        int iterations = 0;
        boolean allCorrect;
        do {
            iterations++;
            allCorrect = perceptron.train(BINARY_INPUT, AND_OUTPUT);
        } while (!allCorrect);
        System.out.println("Iterations: " + iterations);
    }
}
