package org.vladyslav.nn.app;

import org.vladyslav.math.Matrices;
import org.vladyslav.math.Matrix;
import org.vladyslav.math.Vectors;
import org.vladyslav.nn.NeuralNetwork;

public class Main {
    private static final Matrix X = Matrices.create(new double[][]{{0, 0}, {0, 1}, {1, 0}, {1, 1}}, 4, 2);
    private static final Matrix Y = Matrices.create(new double[][]{{0}, {1}, {1}, {0}}, 4, 1);

    public static void main(String[] args) {
        NeuralNetwork nn = new NeuralNetwork(2, 2, 1);
        nn.train(X, Y);

        Matrix output;

        output = nn.forward(Matrices.view(Vectors.create(0, 0)));
        assert Vectors.create(1, 0).equals(output);

        output = nn.forward(Matrices.view(Vectors.create(0, 1)));
        assert Vectors.create(1, 0).equals(output);

        output = nn.forward(Matrices.view(Vectors.create(1, 0)));
        assert Vectors.create(1, 0).equals(output);

        output = nn.forward(Matrices.view(Vectors.create(1, 1)));
        assert Vectors.create(0, 1).equals(output);
    }
}
