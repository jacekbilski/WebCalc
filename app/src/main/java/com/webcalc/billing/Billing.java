package com.webcalc.billing;

import com.webcalc.calculator.CalculatorObserver;

import java.math.BigDecimal;

public class Billing implements CalculatorObserver {

  private BigDecimal balance = BigDecimal.ZERO;

  public BigDecimal getBalance() {
    return balance;
  }

  @Override
  public void evaluated(String function) {
    switch (function) {
      case "+":
      case "-":
        balance = balance.add(BigDecimal.ONE);
        break;
      case "*":
        balance = balance.add(new BigDecimal("5"));
        break;
    }
  }
}
