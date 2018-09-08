package com.webcalc.calculator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CalculatorShould {

  @Test
  void sum() {
    Calculator calculator = new Calculator();
    String result = calculator.eval("1 2 +");
    assertThat(result).isEqualTo("3");
  }
}
