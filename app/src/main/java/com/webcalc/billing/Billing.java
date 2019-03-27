package com.webcalc.billing;

import com.webcalc.calculator.CalculatorObserver;

import java.math.BigDecimal;

public class Billing implements CalculatorObserver {

  private BigDecimal balance = BigDecimal.ZERO;

  public BigDecimal getBalance() {
    return balance;
  }

  @Override
  public void evaluated() {
    balance = balance.add(BigDecimal.ONE);
  }
}
