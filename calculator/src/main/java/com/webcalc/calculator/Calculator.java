package com.webcalc.calculator;

import java.math.BigDecimal;

public class Calculator {

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
      default:
        throw new RuntimeException("Unsupported operation: " + tokens[2]);
    }
    return result.toPlainString();
  }
}
