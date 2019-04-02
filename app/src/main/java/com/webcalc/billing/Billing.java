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
    if ("+".equals(function))
      balance = balance.add(BigDecimal.ONE);
    else
      balance = balance.add(new BigDecimal("5"));
  }
}
