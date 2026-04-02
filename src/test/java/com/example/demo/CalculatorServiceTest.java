package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CalculatorService Tests")
class CalculatorServiceTest {

    private CalculatorService calculatorService;

    @BeforeEach
    void setUp() {
        calculatorService = new CalculatorService();
    }

    // Addition Tests
    @Test
    @DisplayName("should add two positive numbers")
    void testAddPositiveNumbers() {
        double result = calculatorService.add(5, 3);
        assertEquals(8, result, "5 + 3 should equal 8");
    }

    @Test
    @DisplayName("should add positive and negative numbers")
    void testAddMixedNumbers() {
        double result = calculatorService.add(5, -3);
        assertEquals(2, result, "5 + (-3) should equal 2");
    }

    @Test
    @DisplayName("should add zeros")
    void testAddZeros() {
        double result = calculatorService.add(0, 0);
        assertEquals(0, result, "0 + 0 should equal 0");
    }

    // Subtraction Tests
    @Test
    @DisplayName("should subtract two positive numbers")
    void testSubtractPositiveNumbers() {
        double result = calculatorService.subtract(10, 4);
        assertEquals(6, result, "10 - 4 should equal 6");
    }

    @Test
    @DisplayName("should subtract resulting in negative")
    void testSubtractResultingNegative() {
        double result = calculatorService.subtract(3, 5);
        assertEquals(-2, result, "3 - 5 should equal -2");
    }

    // Multiplication Tests
    @Test
    @DisplayName("should multiply two positive numbers")
    void testMultiplyPositiveNumbers() {
        double result = calculatorService.multiply(4, 5);
        assertEquals(20, result, "4 * 5 should equal 20");
    }

    @Test
    @DisplayName("should multiply by zero")
    void testMultiplyByZero() {
        double result = calculatorService.multiply(100, 0);
        assertEquals(0, result, "Any number * 0 should equal 0");
    }

    @Test
    @DisplayName("should multiply positive and negative numbers")
    void testMultiplyMixedSigns() {
        double result = calculatorService.multiply(4, -5);
        assertEquals(-20, result, "4 * (-5) should equal -20");
    }

    // Division Tests
    @Test
    @DisplayName("should divide two positive numbers")
    void testDividePositiveNumbers() {
        double result = calculatorService.divide(20, 4);
        assertEquals(5, result, "20 / 4 should equal 5");
    }

    @Test
    @DisplayName("should throw exception when dividing by zero")
    void testDivideByZeroThrowsException() {
        assertThrows(IllegalArgumentException.class,
            () -> calculatorService.divide(10, 0),
            "Dividing by zero should throw IllegalArgumentException");
    }

    @Test
    @DisplayName("should divide with fractional result")
    void testDivideFractional() {
        double result = calculatorService.divide(7, 2);
        assertEquals(3.5, result, "7 / 2 should equal 3.5");
    }

    // Square Tests
    @Test
    @DisplayName("should square a positive number")
    void testSquarePositiveNumber() {
        double result = calculatorService.square(5);
        assertEquals(25, result, "5 squared should equal 25");
    }

    @Test
    @DisplayName("should square a negative number")
    void testSquareNegativeNumber() {
        double result = calculatorService.square(-4);
        assertEquals(16, result, "(-4) squared should equal 16");
    }

    @Test
    @DisplayName("should square zero")
    void testSquareZero() {
        double result = calculatorService.square(0);
        assertEquals(0, result, "0 squared should equal 0");
    }

    // Absolute Tests
    @Test
    @DisplayName("should return absolute value of negative number")
    void testAbsoluteNegative() {
        double result = calculatorService.absolute(-15);
        assertEquals(15, result, "Absolute value of -15 should be 15");
    }

    @Test
    @DisplayName("should return absolute value of positive number")
    void testAbsolutePositive() {
        double result = calculatorService.absolute(15);
        assertEquals(15, result, "Absolute value of 15 should be 15");
    }

    @Test
    @DisplayName("should return zero for absolute value of zero")
    void testAbsoluteZero() {
        double result = calculatorService.absolute(0);
        assertEquals(0, result, "Absolute value of 0 should be 0");
    }
}

