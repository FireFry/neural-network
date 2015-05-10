package org.vladyslav.nn.app;

import org.vladyslav.math.Vector;
import org.vladyslav.math.Vectors;
import org.vladyslav.nn.NeuralNetwork;

public class Main {
    public static void main(String[] args) {
        NeuralNetwork nn = new NeuralNetwork(2, 2);

        nn.train(Vectors.create(0, 0), Vectors.create(1, 0));
        nn.train(Vectors.create(0, 1), Vectors.create(1, 0));
        nn.train(Vectors.create(1, 0), Vectors.create(1, 0));
        nn.train(Vectors.create(1, 1), Vectors.create(0, 1));

        Vector output;

        output = nn.process(Vectors.create(0, 0));
        assert Vectors.create(1, 0).equals(output);

        output = nn.process(Vectors.create(0, 1));
        assert Vectors.create(1, 0).equals(output);

        output = nn.process(Vectors.create(1, 0));
        assert Vectors.create(1, 0).equals(output);

        output = nn.process(Vectors.create(1, 1));
        assert Vectors.create(0, 1).equals(output);
    }
}
