package com.webcalc.billing;

import com.webcalc.calculator.CalculatorObserver;

import java.math.BigDecimal;

public class Billing implements CalculatorObserver {

  public BigDecimal getBalance() {
    return BigDecimal.ZERO;
  }
}
