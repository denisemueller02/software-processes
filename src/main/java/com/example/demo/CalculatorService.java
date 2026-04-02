package com.example.demo;

import org.springframework.stereotype.Service;

/**
 * Simple calculator service for basic arithmetic operations.
 */
@Service
public class CalculatorService {

    /**
     * Adds two numbers.
     *
     * @param a first number
     * @param b second number
     * @return sum of a and b
     */
    public double add(double a, double b) {
        return a + b;
    }

    /**
     * Subtracts b from a.
     *
     * @param a first number
     * @param b second number
     * @return difference of a and b
     */
    public double subtract(double a, double b) {
        return a - b;
    }

    /**
     * Multiplies two numbers.
     *
     * @param a first number
     * @param b second number
     * @return product of a and b
     */
    public double multiply(double a, double b) {
        return a * b;
    }

    /**
     * Divides a by b.
     *
     * @param a dividend
     * @param b divisor
     * @return quotient of a divided by b
     * @throws IllegalArgumentException if b is zero
     */
    public double divide(double a, double b) {
        if (b == 0) {
            throw new IllegalArgumentException("Cannot divide by zero");
        }
        return a / b;
    }

    /**
     * Calculates the square of a number.
     *
     * @param a the number
     * @return a squared
     */
    public double square(double a) {
        return a * a;
    }

    /**
     * Calculates the absolute value of a number.
     *
     * @param a the number
     * @return absolute value of a
     */
    public double absolute(double a) {
        return Math.abs(a);
    }
}

