package org.vladyslav.nn.app;

import org.vladyslav.math.Matrices;
import org.vladyslav.math.Matrix;
import org.vladyslav.nn.NeuralNetwork;

import java.util.Random;

public class Main {
    private static final Matrix X = Matrices.create(new double[][]{{0, 0}, {0, 1}, {1, 0}, {1, 1}}, 4, 2);
    private static final Matrix Y = Matrices.create(new double[][]{{0}, {1}, {1}, {0}}, 4, 1);

    public static void main(String[] args) {
        NeuralNetwork nn = new NeuralNetwork(new Random(75371), 2, 2, 1);
        nn.forward(X);
        nn.train(X, Y);
    }
}
