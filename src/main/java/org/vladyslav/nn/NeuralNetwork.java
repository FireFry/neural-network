package org.vladyslav.nn;

import org.vladyslav.math.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NeuralNetwork {
    private List<Matrix> weights = new ArrayList<>();

    public NeuralNetwork(int firstLayer, int secondLayer, int... otherLayers) {
        this(new Random(), firstLayer, secondLayer, otherLayers);
    }

    public NeuralNetwork(Random random, int firstLayer, int secondLayer, int... otherLayers) {
        weights.add(Matrices.random(random, firstLayer + 1, secondLayer));
        int prev = secondLayer;
        for (int layerSize : otherLayers) {
            weights.add(Matrices.random(random, prev + 1, layerSize));
            prev = layerSize;
        }
    }

    public void train(Vector x, Vector y) {
        List<Vector> a = new ArrayList<>();
        List<Vector> z = new ArrayList<>();
        a.add(x);
        for (Matrix w : weights) {
            Vector nextZ = MathUtils.multiply(Vectors.bias(a.get(a.size() - 1)), w);
            z.add(nextZ);
            a.add(MathUtils.sigmoid(nextZ));
        }
        for (int i = a.size() - 1; i > 0; i--) {
            Vector delta = MathUtils.multiply(MathUtils.minus(y, a.get(i)), Matrices.view(MathUtils.sigmoidPrime(z.get(i))));
            Vector dJdW = MathUtils.multiply(a.get(i - 1), Matrices.view(delta));
        }
    }

    public Vector forward(Vector input) {
        Vector x = input;
        for (Matrix m : weights) {
            x = MathUtils.sigmoid(MathUtils.multiply(Vectors.bias(x), m));
        }
        return x;
    }
}
