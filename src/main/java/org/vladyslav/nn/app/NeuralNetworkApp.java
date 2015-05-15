package org.vladyslav.nn.app;

import org.vladyslav.math.Matrix;

import java.util.Random;

import static org.vladyslav.math.MathUtils.*;
import static org.vladyslav.math.Matrices.create;
import static org.vladyslav.math.Matrices.random;

public class NeuralNetworkApp {
    private static final Matrix X = create(new double[][]{{0, 0}, {0, 1}, {1, 0}, {1, 1}}, 4, 2);
    private static final Matrix Y = create(new double[][]{{0}, {1}, {1}, {0}}, 4, 1);

    public static void main(String[] args) {
        double learningRate = 10;

        Random random = new Random(31415926);
        Matrix a1 = X;
        Matrix w1 = random(random, X.cols(), 2);
        Matrix w2 = random(random, 2, 3);
        Matrix w3 = random(random, 3, 1);

        for (int i = 0; i < 10000; i++) {
            Matrix z2 = multiply(a1, w1);
            Matrix a2 = sigmoid(z2);

            Matrix z3 = multiply(a2, w2);
            Matrix a3 = sigmoid(z3);

            Matrix z4 = multiply(a3, w3);
            Matrix a4 = sigmoid(z4);

            Matrix d4 = minus(a4, Y);
            Matrix d3 = product(multiply(d4, transpose(w3)), sigmoidDerivative(z3));
            Matrix d2 = product(multiply(d3, transpose(w2)), sigmoidDerivative(z2));

            w1 = minus(w1, product(multiply(transpose(a1), d2), learningRate));
            w2 = minus(w2, product(multiply(transpose(a2), d3), learningRate));
            w3 = minus(w3, product(multiply(transpose(a3), d4), learningRate));

            System.out.println(product(minus(d4, Y), minus(d4, Y)));
        }
    }
}
