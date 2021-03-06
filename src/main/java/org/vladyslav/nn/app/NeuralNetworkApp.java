package org.vladyslav.nn.app;

import org.vladyslav.math.Matrix;
import org.vladyslav.nn.NeuralNetwork;

import java.util.Random;

public class NeuralNetworkApp {
    private static final Random RANDOM = new Random(31415926);
    private static final double LEARNING_RATE = 1;
    private static final double TARGET_ERROR = 1e-6;

    private static final Matrix BINARY_INPUT = Matrix.combineRows(
            Matrix.row(0, 0),
            Matrix.row(0, 1),
            Matrix.row(1, 0),
            Matrix.row(1, 1)
    );

    private static final Matrix XOR_OUTPUT = Matrix.combineRows(
            Matrix.row(0 ^ 0),
            Matrix.row(0 ^ 1),
            Matrix.row(1 ^ 0),
            Matrix.row(1 ^ 1)
    );

    public static void main(String[] args) {
        NeuralNetwork neuralNetwork = new NeuralNetwork(RANDOM, 2, 2, 1);
        long iterationCount = 0;
        double prevError;
        double error = Double.MAX_VALUE;
        do {
            iterationCount++;
            neuralNetwork.train(BINARY_INPUT, XOR_OUTPUT, LEARNING_RATE);
            prevError = error;
            error = NeuralNetwork.error(neuralNetwork.forward(BINARY_INPUT), XOR_OUTPUT);
        } while (error > TARGET_ERROR && prevError > error);
        if (error > TARGET_ERROR) {
            System.err.println("Iterations: " + iterationCount);
            System.err.println("Error: " + error);
        } else {
            System.out.println("Iterations: " + iterationCount);
        }
    }
}
