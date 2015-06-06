package org.vladyslav.nn;

import org.vladyslav.math.Matrix;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NeuralNetwork {
    private final int layersCount;
    private final List<Matrix> weights;
    private final List<Matrix> layerOutputs;
    private final List<Matrix> layerDeltas;

    public NeuralNetwork(int firstLayer, int secondLayer, int... otherLayers) {
        this(new Random(), firstLayer, secondLayer, otherLayers);
    }

    public NeuralNetwork(Random random, int firstLayer, int secondLayer, int... otherLayers) {
        layersCount = 2 + otherLayers.length;
        weights = createWeights(random, firstLayer, secondLayer, otherLayers);
        layerOutputs = createEmptyMatrixList(layersCount);
        layerDeltas = createEmptyMatrixList(layersCount - 1);
    }

    private static List<Matrix> createWeights(Random random, int firstLayer, int secondLayer, int[] otherLayers) {
        List<Matrix> weights = new ArrayList<>(2 + otherLayers.length);
        weights.add(Matrix.random(random, firstLayer + 1, secondLayer));
        int prev = secondLayer;
        for (int layerSize : otherLayers) {
            weights.add(Matrix.random(random, prev + 1, layerSize));
            prev = layerSize;
        }
        return weights;
    }

    private static List<Matrix> createEmptyMatrixList(int size) {
        List<Matrix> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(null);
        }
        return list;
    }

    public void train(Matrix input, Matrix output, double learningRate) {
        forward(input);
        backPropagate(output);
        adjustWeights(learningRate);
    }

    public Matrix forward(Matrix input) {
        layerOutputs.set(0, input);
        for (int j = 1; j < layersCount; j++) {
            Matrix layerInput = layerOutputs.get(j - 1).addBias().multiply(weights.get(j - 1));
            layerOutputs.set(j, sigmoid(layerInput));
        }
        return layerOutputs.get(layersCount - 1);
    }

    private void backPropagate(Matrix output) {
        layerDeltas.set(layersCount - 2, output.minus(layerOutputs.get(layersCount - 1)));
        for (int j = layersCount - 2; j > 0; j--) {
            layerDeltas.set(j - 1, layerDeltas.get(j)
                            .multiply(weights.get(j).transpose())
                            .removeBias()
                            .product(sigmoidDerivative(layerOutputs.get(j)))
            );
        }
    }

    private void adjustWeights(double learningRate) {
        for (int j = 0; j < layersCount - 1; j++) {
            weights.set(j, layerOutputs.get(j)
                            .addBias()
                            .transpose()
                            .multiply(layerDeltas.get(j))
                            .polynomial(learningRate, 0)
                            .plus(weights.get(j))
            );
        }
    }

    private Matrix sigmoid(Matrix m) {
        return m.map(x -> 1.0 / (1.0 + Math.exp(-x)));
    }

    private Matrix sigmoidDerivative(Matrix m) {
        return m.polynomial(-1, 1, 0);
    }

    public static double error(Matrix prediction, Matrix output) {
        return prediction.minus(output).polynomial(1, 0, 0).average();
    }
}