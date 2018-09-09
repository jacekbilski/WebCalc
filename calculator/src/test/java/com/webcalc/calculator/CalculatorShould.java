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
  })
  void subtract(String input, String expectedResult) {
    String result = calculator.eval(input);
    assertThat(result).isEqualTo(expectedResult);
  }
}
