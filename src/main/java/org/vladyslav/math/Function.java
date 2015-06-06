package org.vladyslav.math;

/**
 * Function that maps double to double. May be applied to each element of matrix by using apply() method from it.
 */
public interface Function {
    double apply(double x);
}
