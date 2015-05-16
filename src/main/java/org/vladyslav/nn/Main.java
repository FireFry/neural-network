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
    }
}