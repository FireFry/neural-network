package org.vladyslav.nn;

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
            layerOutputs.set(j, layerOutputs.get(j - 1)
                    .addBias()
                    .multiply(weights.get(j - 1))
                    .sigmoid()
            );
        }
        return layerOutputs.get(layersCount - 1);
    }

    private void backPropagate(Matrix output) {
        layerDeltas.set(layersCount - 2, output.minus(layerOutputs.get(layersCount - 1)));
        for (int j = layersCount - 2; j > 0; j--) {
            layerDeltas.set(j - 1, layerDeltas.get(j)
                    .multiply(weights.get(j).transpose())
                    .removeBias()
                    .product(layerOutputs.get(j).sigmoidDerivative())
            );
        }
    }

    private void adjustWeights(double learningRate) {
        for (int j = 0; j < layersCount - 1; j++) {
            weights.set(j, layerOutputs.get(j)
                    .addBias()
                    .transpose()
                    .multiply(layerDeltas.get(j))
                    .applyPolynomial(0, learningRate)
                    .plus(weights.get(j))
            );
        }
    }

    public static double error(Matrix prediction, Matrix output) {
        return prediction.minus(output).applyPolynomial(0, 0, 1).average();
    }
}