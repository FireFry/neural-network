package org.vladyslav.nn;

import java.util.Random;

import static org.vladyslav.nn.Matrix.*;

public class Main {
    private static final Random RANDOM = new Random(31415926);

    private static final Matrix BINARY_INPUT = combineRows(row(0, 0),row(0, 1),row(1, 0),row(1, 1));
    private static final Matrix AND = row(-30, 20, 20).transpose();
    private static final Matrix OR = row(-10, 20, 20).transpose();
    private static final Matrix NOT_AND_NOT = row(10, -20, -20).transpose();

    public static void main(String[] args) {
        System.out.println(Matrix.combineRows(Matrix.range(-10, 10, 1), Matrix.range(-10, 10, 1).sigmoid()).transpose());
        System.out.println();
        System.out.println(BINARY_INPUT.addBias().multiply(AND).sigmoid());
        System.out.println(BINARY_INPUT.addBias().multiply(OR).sigmoid());
        System.out.println(BINARY_INPUT.addBias().multiply(NOT_AND_NOT).sigmoid());
        System.out.println();

        Matrix XOR_FIRST = Matrix.combineRows(AND.transpose(), NOT_AND_NOT.transpose()).transpose();
        Matrix XOR_SECOND = OR;
        System.out.println(BINARY_INPUT.addBias().multiply(XOR_FIRST).sigmoid().addBias().multiply(XOR_SECOND).sigmoid());

        System.out.println("AND: " + trainTwoLayers(BINARY_INPUT, Matrix.row(0, 0, 0, 1).transpose(), Matrix.random(RANDOM, 3, 1), 1.0));
        System.out.println("OR: " + trainTwoLayers(BINARY_INPUT, Matrix.row(0, 1, 1, 1).transpose(), Matrix.random(RANDOM, 3, 1), 1.0));
        System.out.println("NOT_AND_NOT: " + trainTwoLayers(BINARY_INPUT, Matrix.row(1, 0, 0, 0).transpose(), Matrix.random(RANDOM, 3, 1), 1.0));
    }

    private static Matrix trainTwoLayers(Matrix x, Matrix y, Matrix w, double lambda) {
        for (int i = 0; i < 100000; i++) {
            Matrix a = x.addBias().multiply(w).sigmoid();
            Matrix d = y.minus(a);
            w = w.plus(x.addBias().transpose().multiply(d).product(lambda));
        }
        return w;
    }
}