package com.webcalc.calculator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CalculatorShould {

  private final Calculator calculator = new Calculator();

  private final UUID userId = UUID.randomUUID();

  @DisplayName("Sum")
  @ParameterizedTest(name = "input: ''{0}'', expected result: ''{1}''")
  @CsvSource({
      "1 2 +, 3",
      "2 3 +, 5",
      "0 -1 +, -1",
      "-13 122 +, 109",
      "'1,2 3,4 +', '4,6'",
  })
  void sum(String input, String expectedResult) {
    String result = calculator.eval(userId, input, Calculator.DEFAULT_MAX_FRACTION_DIGITS);
    assertThat(result).isEqualTo(expectedResult);
  }

  @DisplayName("Subtract")
  @ParameterizedTest(name = "input: ''{0}'', expected result: ''{1}''")
  @CsvSource({
      "1 2 -, -1",
      "3 0 -, 3",
      "2 -2 -, 4",
      "'1,2 0,5 -', '0,7'",
  })
  void subtract(String input, String expectedResult) {
    String result = calculator.eval(userId, input, Calculator.DEFAULT_MAX_FRACTION_DIGITS);
    assertThat(result).isEqualTo(expectedResult);
  }

  @DisplayName("Multiply")
  @ParameterizedTest(name = "input: ''{0}'', expected result: ''{1}''")
  @CsvSource({
      "1 2 *, 2",
      "3 0 *, 0",
      "-12 12 *, -144",
      "-3 -5 *, 15",
      "'-2,1 3,4 *', '-7,14'",
      "'3,333 4,444 *', '14,81'",
  })
  void multiply(String input, String expectedResult) {
    String result = calculator.eval(userId, input, Calculator.DEFAULT_MAX_FRACTION_DIGITS);
    assertThat(result).isEqualTo(expectedResult);
  }

  @DisplayName("Divide")
  @ParameterizedTest(name = "input: ''{0}'', max fraction digits: ''{2}'', expected result: ''{1}''")
  @CsvSource({
      "2 1 /, 2, 2",
      "1 2 /, '0,5', 2",
      "2 3 /, '0,67', 2",
      "9 -4 /, '-2,25', 9",
      "-47 -13 /, '3,615384615', 9",
      "3 2 /, 2, 0",
  })
  void divide(String input, String expectedResult, int maxFractionDigits) {
    String result = calculator.eval(userId, input, maxFractionDigits);
    assertThat(result).isEqualTo(expectedResult);
  }

  @DisplayName("Complex calculations")
  @ParameterizedTest(name = "input: ''{0}'', expected result: ''{1}''")
  @CsvSource({
      "1 2 3 + +, 6",
      "2 3 4 + *, 14",
      "'3,2 2,8 2 / -', '1,8'",
      "1 2 3 4 5 * * * *, 120",
      "1 2 + 3 *, 9",
  })
  void complexCalculations(String input, String expectedResult) {
    String result = calculator.eval(userId, input, Calculator.DEFAULT_MAX_FRACTION_DIGITS);
    assertThat(result).isEqualTo(expectedResult);
  }

  @DisplayName("Constants")
  @ParameterizedTest(name = "input: ''{0}'', max fraction digits: ''{2}'', expected result: ''{1}''")
  @CsvSource({
      "π, '3,14159', 5",
  })
  void constants(String input, String expectedResult, int maxFractionDigits) {
    String result = calculator.eval(userId, input, maxFractionDigits);
    assertThat(result).isEqualTo(expectedResult);
  }

  @DisplayName("Square")
  @ParameterizedTest(name = "input: ''{0}'', expected result: ''{1}''")
  @CsvSource({
      "0 ^2, 0",
      "2 ^2, 4",
      "-3 ^2, 9",
      "'4,2 ^2', '17,64'"
  })
  void square(String input, String expectedResult) {
    String result = calculator.eval(userId, input, Calculator.DEFAULT_MAX_FRACTION_DIGITS);
    assertThat(result).isEqualTo(expectedResult);
  }

  @DisplayName("Custom functions")
  @ParameterizedTest(name = "function: ''{0}'', input: ''{1}'', max fraction digits: ''{3}'', expected result: ''{2}''")
  @CsvSource({
      "circle_area ^2 π *, 3 circle_area, '28,27', 2",
      "square_area ^2, 3 square_area, 9, 2",
  })
  void supportDefiningAndEvaluatingCustomFunctions(String definition, String input, String expectedResult, int maxFractionDigits) {
    calculator.defineCustomFunction(definition);
    String result = calculator.eval(userId, input, maxFractionDigits);
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void handleNestingOfCustomFunctions() {
    calculator.defineCustomFunction("f1 +");
    calculator.defineCustomFunction("f2 f1");
    String result = calculator.eval(userId, "1 2 3 4 f1 f2 f1", 0);
    assertThat(result).isEqualTo("10");
  }
}
