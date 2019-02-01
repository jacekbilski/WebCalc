package com.webcalc.billing;

import com.webcalc.calculator.Calculator;

import static java.math.BigDecimal.ONE;

public class BillingCalculator extends Calculator {

  private final Calculator target;
  private final Billing billing;

  public BillingCalculator(Calculator target, Billing billing) {
    this.target = target;
    this.billing = billing;
  }

  @Override
  public String eval(String input, int maxFractionDigits) {
    billing.addToBalance(ONE);
    return target.eval(input, maxFractionDigits);
  }
}
