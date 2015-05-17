package org.vladyslav.nn.app;

import org.vladyslav.nn.Matrix;
import org.vladyslav.nn.NeuralNetwork;

import java.util.Random;

import static org.vladyslav.nn.Matrix.combineRows;
import static org.vladyslav.nn.Matrix.row;

public class NeuralNetworkApp {
    private static final Random RANDOM = new Random(31415926);
    private static final double LEARNING_RATE = 1.0;
    private static final double TARGET_ERROR = 1e-6;
    private static final int MAX_ITERATIONS = 100000;

    private static final Matrix BINARY_INPUT = combineRows(
            row(0, 0),
            row(0, 1),
            row(1, 0),
            row(1, 1)
    );

    private static final Matrix XOR_OUTPUT = combineRows(
            row(0 ^ 0),
            row(0 ^ 1),
            row(1 ^ 0),
            row(1 ^ 1)
    );

    public static void main(String[] args) {
        NeuralNetwork neuralNetwork = new NeuralNetwork(RANDOM, 2, 2, 1);
        int iterationCount = 0;
        double error;
        do {
            iterationCount++;
            neuralNetwork.train(BINARY_INPUT, XOR_OUTPUT, LEARNING_RATE);
            error = NeuralNetwork.error(neuralNetwork.forward(BINARY_INPUT), XOR_OUTPUT);
        } while (iterationCount < MAX_ITERATIONS && error > TARGET_ERROR);
        System.out.println("Iterations: " + iterationCount);
    }

}
