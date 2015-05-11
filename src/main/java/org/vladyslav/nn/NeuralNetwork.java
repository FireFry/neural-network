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

    public void train(Matrix x, Matrix y) {
        List<Matrix> a = new ArrayList<>();
        List<Matrix> z = new ArrayList<>();
        a.add(x);
        for (Matrix w : weights) {
            Matrix nextZ = MathUtils.multiply(Matrices.bias(a.get(a.size() - 1)), w);
            z.add(nextZ);
            a.add(MathUtils.sigmoid(nextZ));
        }

    }

    public Matrix forward(Matrix input) {
        Matrix x = input;
        for (Matrix m : weights) {
            x = MathUtils.sigmoid(MathUtils.multiply(Matrices.bias(x), m));
        }
        return x;
    }
}
