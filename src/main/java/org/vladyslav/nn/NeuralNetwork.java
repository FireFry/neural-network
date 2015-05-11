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
        List<Matrix> d = new ArrayList<>();
        a.add(x);
        z.add(null);
        d.add(null);
        for (Matrix w : weights) {
            z.add(MathUtils.multiply(Matrices.bias(a.get(a.size() - 1)), w));
            a.add(MathUtils.sigmoid(z.get(z.size() - 1)));
            d.add(null); // Reserve space for later usage
        }
        d.set(d.size() - 1, MathUtils.minus(a.get(a.size() - 1), y));
        for (int i = d.size() - 2; i > 0; i--) {
            Matrix nextDelta = d.get(i + 1);
            Matrix wT = MathUtils.transpose(weights.get(i));
            Matrix zDerivative = Matrices.bias(MathUtils.sigmoidDerivative(z.get(i)));
            Matrix delta = MathUtils.product(MathUtils.multiply(nextDelta, wT), zDerivative);
            d.set(i, delta);
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
