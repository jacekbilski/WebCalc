package com.webcalc.calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.function.BinaryOperator;

public class Calculator {

  static final int DEFAULT_MAX_FRACTION_DIGITS = 2;

  String eval(String input, int maxFractionDigits) {
    String[] tokens = input.split(" ");
    BigDecimal v1 = new BigDecimal(tokens[0]);
    BigDecimal v2 = new BigDecimal(tokens[1]);
    BinaryOperator<BigDecimal> f = function(tokens[2], maxFractionDigits);
    BigDecimal result = f.apply(v1, v2);
    return format(result, maxFractionDigits);
  }

  private BinaryOperator<BigDecimal> function(String function, int maxFractionDigits) {
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

  private String format(BigDecimal result, int maxFractionDigits) {
    NumberFormat formatter = NumberFormat.getNumberInstance(Locale.GERMANY);
    formatter.setMaximumFractionDigits(maxFractionDigits);
    return formatter.format(result);
  }
}
