package com.webcalc.billing;

import com.webcalc.calculator.Calculator;

public class BillingCalculator extends Calculator {

  private final Calculator target;

  public BillingCalculator(Calculator target) {
    this.target = target;
  }

  @Override
  public String eval(String input, int maxFractionDigits) {
    return target.eval(input, maxFractionDigits);
  }
}
