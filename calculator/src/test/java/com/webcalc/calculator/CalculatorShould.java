package com.webcalc.calculator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class CalculatorShould {

  @DisplayName("Sum")
  @ParameterizedTest(name = "input: ''{0}'', expected result: ''{1}''")
  @CsvSource({
      "1 2 +, 3",
      "2 3 +, 5",
  })
  void sum(String input, String expectedResult) {
    Calculator calculator = new Calculator();
    String result = calculator.eval(input);
    assertThat(result).isEqualTo(expectedResult);
  }
}
