package org.vladyslav.nn;

import org.vladyslav.math.MathUtils;
import org.vladyslav.math.Matrices;
import org.vladyslav.math.Matrix;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

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
        Stack<Matrix> a = new Stack<>();
        Stack<Matrix> z = new Stack<>();
        List<Matrix> d = new ArrayList<>();
        a.add(x);
        for (Matrix w : weights) {
            z.add(MathUtils.multiply(Matrices.bias(a.get(a.size() - 1)), w));
            a.add(MathUtils.sigmoid(z.get(z.size() - 1)));
        }
        d.add(MathUtils.minus(a.get(a.size() - 1), y));
        for (int i = d.size() - 2; i > 0; i--) {
            Matrix wT = MathUtils.transpose(weights.get(i));
            Matrix zDerivative = Matrices.bias(MathUtils.sigmoidDerivative(z.get(i - 1)));
            Matrix delta = MathUtils.product(MathUtils.multiply(d.get(i - 1), wT), zDerivative);
            d.add(delta);
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
