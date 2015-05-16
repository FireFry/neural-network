package org.vladyslav.nn;

import java.util.Random;

import static org.vladyslav.nn.Matrix.*;

public class Main {
    private static final Random RANDOM = new Random(31415926);

    private static final Matrix BINARY_INPUT = combineRows(row(0, 0), row(0, 1), row(1, 0), row(1, 1));

    public static void main(String[] args) {
        Matrix x = BINARY_INPUT;
        Matrix y = Matrix.row(0, 1, 1, 0).transpose();
        Matrix w0 = Matrix.random(RANDOM, 3, 2);
        Matrix w1 = Matrix.random(RANDOM, 3, 1);
        double lambda = 1;
        int iterations = 10000;

        for (int i = 0; i < iterations; i++) {
            Matrix a1 = x.addBias().multiply(w0).sigmoid();
            Matrix a2 = a1.addBias().multiply(w1).sigmoid();

            Matrix d2 = y.minus(a2);
            Matrix d1 = d2.multiply(w1.transpose()).removeBias().product(a1).product(Matrix.of(a1.rows(), a1.cols(), 1.0).minus(a1));

            w1 = w1.plus(a1.addBias().transpose().multiply(d2).product(lambda));
            w0 = w0.plus(x.addBias().transpose().multiply(d1).product(lambda));
        }

        Matrix output = x.addBias().multiply(w0).sigmoid().addBias().multiply(w1).sigmoid();
        System.out.println(w0.transpose());
        System.out.println(w1.transpose());
        System.out.println(output);
        System.out.println("Error: " + output.minus(y).product(output.minus(y)).average());
    }
}