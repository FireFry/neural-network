package org.vladyslav.nn;

import org.vladyslav.math.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NeuralNetwork {
    List<Matrix> matrices = new ArrayList<>();

    public NeuralNetwork(int firstLayer, int secondLayer, int... otherLayers) {
        Random random = new Random(405315);
        matrices.add(Matrices.random(random, firstLayer + 1, secondLayer));
        int prev = secondLayer;
        for (int layerSize : otherLayers) {
            matrices.add(Matrices.random(random, prev + 1, layerSize));
            prev = layerSize;
        }
    }

    public void train(TrainingData trainingData) {
        Vector output = process(trainingData.input);
        // TODO
    }

    public Vector process(Vector input) {
        Vector x = input;
        for (Matrix m : matrices) {
            x = MathUtils.sigmoid(MathUtils.multiply(Vectors.bias(x), m));
        }
        return x;
    }
}
