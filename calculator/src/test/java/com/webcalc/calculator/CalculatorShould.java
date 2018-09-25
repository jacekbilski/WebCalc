package com.webcalc.calculator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class CalculatorShould {

  private final Calculator calculator = new Calculator();

  @DisplayName("Sum")
  @ParameterizedTest(name = "input: ''{0}'', expected result: ''{1}''")
  @CsvSource({
      "1 2 +, 3",
      "2 3 +, 5",
      "0 -1 +, -1",
      "-13 122 +, 109",
  })
  void sum(String input, String expectedResult) {
    String result = calculator.eval(input);
    assertThat(result).isEqualTo(expectedResult);
  }

  @DisplayName("Subtract")
  @ParameterizedTest(name = "input: ''{0}'', expected result: ''{1}''")
  @CsvSource({
      "1 2 -, -1",
      "3 0 -, 3",
      "2 -2 -, 4",
  })
  void subtract(String input, String expectedResult) {
    String result = calculator.eval(input);
    assertThat(result).isEqualTo(expectedResult);
  }

  @DisplayName("Multiply")
  @ParameterizedTest(name = "input: ''{0}'', expected result: ''{1}''")
  @CsvSource({
      "1 2 *, 2",
      "3 0 *, 0",
      "-12 12 *, -144",
      "-3 -5 *, 15",
  })
  void multiply(String input, String expectedResult) {
    String result = calculator.eval(input);
    assertThat(result).isEqualTo(expectedResult);
  }

  @DisplayName("Divide")
  @ParameterizedTest(name = "input: ''{0}'', expected result: ''{1}''")
  @CsvSource({
      "2 1 /, 2",
  })
  void divide(String input, String expectedResult) {
    String result = calculator.eval(input);
    assertThat(result).isEqualTo(expectedResult);
  }
}
