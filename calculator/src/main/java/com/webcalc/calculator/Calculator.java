package com.webcalc.calculator;

import java.math.BigDecimal;

public class Calculator {

  String eval(String input) {
    String[] tokens = input.split(" ");
    BigDecimal v1 = new BigDecimal(tokens[0]);
    BigDecimal v2 = new BigDecimal(tokens[1]);
    return v1.add(v2).toPlainString();
  }
}
