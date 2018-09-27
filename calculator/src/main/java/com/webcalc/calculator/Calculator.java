package com.webcalc.calculator;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class Calculator {

  private NumberFormat formatter = NumberFormat.getNumberInstance(Locale.GERMANY);

  String eval(String input) {
    String[] tokens = input.split(" ");
    BigDecimal v1 = new BigDecimal(tokens[0]);
    BigDecimal v2 = new BigDecimal(tokens[1]);
    BigDecimal result;
    switch (tokens[2]) {
      case "+":
        result = v1.add(v2);
        break;
      case "-":
        result = v1.subtract(v2);
        break;
      case "*":
        result = v1.multiply(v2);
        break;
      case "/":
        result = v1.divide(v2);
        break;
      default:
        throw new RuntimeException("Unsupported operation: " + tokens[2]);
    }
    return formatter.format(result);
  }
}
