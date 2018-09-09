package com.webcalc.calculator;

import java.math.BigDecimal;

public class Calculator {

  String eval(String input) {
    String[] tokens = input.split(" ");
    BigDecimal v1 = new BigDecimal(tokens[0]);
    BigDecimal v2 = new BigDecimal(tokens[1]);
    BigDecimal result;
    if ("+".equals(tokens[2])) {
      result = v1.add(v2);
    } else {
      result = v1.subtract(v2);
    }
    return result.toPlainString();
  }
}
