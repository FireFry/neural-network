package org.vladyslav.math;

public class Polynomial implements Function {
    private double[] coefficients;

    public Polynomial(double... coefficients) {
        this.coefficients = coefficients;
    }

    @Override
    public double apply(double x) {double exp = 1.0;
        double sum = 0;
        for (int i = coefficients.length - 1; i >= 0; i--) {
            double coefficient = coefficients[i];
            sum += coefficient * exp;
            exp *= x;
        }
        return sum;
    }
}
