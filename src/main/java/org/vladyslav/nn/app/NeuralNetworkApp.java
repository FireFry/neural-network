package org.vladyslav.nn.app;

import org.vladyslav.nn.Matrix;
import org.vladyslav.nn.NeuralNetwork;

import java.util.Random;

import static org.vladyslav.nn.Matrix.combineRows;
import static org.vladyslav.nn.Matrix.row;

public class NeuralNetworkApp {
    private static final Random RANDOM = new Random(31415926);
    private static final int ITERATIONS = 10000;
    private static final double LEARNING_RATE = 1.0;

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
        for (int i = 0; i < ITERATIONS; i++) {
            neuralNetwork.train(BINARY_INPUT, XOR_OUTPUT, LEARNING_RATE);
        }
        Matrix prediction = neuralNetwork.forward(BINARY_INPUT);
        System.out.println(prediction);
        System.out.println("Error: " + NeuralNetwork.error(prediction, XOR_OUTPUT));
    }

}
