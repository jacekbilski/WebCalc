package com.webcalc.calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.function.BinaryOperator;

public class Calculator {

  private NumberFormat formatter = NumberFormat.getNumberInstance(Locale.GERMANY);

  private int maxFractionDigits;

  void setMaxFractionDigits(int maxFractionDigits) {
    this.maxFractionDigits = maxFractionDigits;
    formatter.setMaximumFractionDigits(maxFractionDigits);
  }

  String eval(String input) {
    String[] tokens = input.split(" ");
    BigDecimal v1 = new BigDecimal(tokens[0]);
    BigDecimal v2 = new BigDecimal(tokens[1]);
    BinaryOperator<BigDecimal> f = function(tokens[2]);
    BigDecimal result = f.apply(v1, v2);
    return formatter.format(result);
  }

  private BinaryOperator<BigDecimal> function(String function) {
    switch (function) {
      case "+":
        return BigDecimal::add;
      case "-":
        return BigDecimal::subtract;
      case "*":
        return BigDecimal::multiply;
      case "/":
        return (v1, v2) -> v1.divide(v2, maxFractionDigits, RoundingMode.HALF_UP);
      default:
        throw new RuntimeException("Unsupported function: " + function);
    }
  }
}
