package com.webcalc.billing;

import com.webcalc.calculator.CalculatorObserver;

import java.math.BigDecimal;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;

public class Billing implements CalculatorObserver {

  private BigDecimal balance = ZERO;

  public BigDecimal getBalance() {
    return balance;
  }

  @Override
  public void evaluated(String function) {
    switch (function) {
      case "+":
      case "-":
        balance = balance.add(ONE);
        break;
      case "*":
        balance = balance.add(new BigDecimal("5"));
        break;
      case "/":
        balance = balance.add(TEN);
        break;
    }
  }
}
