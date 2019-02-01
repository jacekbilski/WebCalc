package com.webcalc.billing;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;

public class Billing {

  private BigDecimal balance = ZERO;

  public BigDecimal getBalance() {
    return balance;
  }

  public void addToBalance(BigDecimal amount) {
    balance = balance.add(amount);
  }
}
