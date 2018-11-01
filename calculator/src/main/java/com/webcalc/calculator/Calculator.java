package com.webcalc.calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.function.BinaryOperator;

public class Calculator {

  static final int DEFAULT_MAX_FRACTION_DIGITS = 2;

  private final NumberFormat formatter;

  public Calculator() {
    formatter = DecimalFormat.getNumberInstance(Locale.GERMANY);
    if (formatter instanceof DecimalFormat)
      ((DecimalFormat) formatter).setParseBigDecimal(true);
  }

  String eval(String input, int maxFractionDigits) {
    String[] tokens = input.split(" ");
    BigDecimal v1 = parse(tokens[0]);
    BigDecimal v2 = parse(tokens[1]);
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

  private BigDecimal parse(String string) {
    try {
      return (BigDecimal) formatter.parse(string);
    } catch (ParseException e) {
      throw new RuntimeException("Cannot recognize a number: '" + string + "'", e);
    }
  }

  private String format(BigDecimal result, int maxFractionDigits) {
    NumberFormat formatter = (NumberFormat) this.formatter.clone();
    formatter.setMaximumFractionDigits(maxFractionDigits);
    return formatter.format(result);
  }
}
