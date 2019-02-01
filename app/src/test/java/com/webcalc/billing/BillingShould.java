package com.webcalc.billing;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;
import static org.assertj.core.api.Assertions.assertThat;

class BillingShould {

  private final Billing billing = new Billing();

  @Test
  void returnBalanceZero_whenNoCalculationsWereDone() {
    BigDecimal balance = billing.getBalance();
    assertThat(balance).isEqualByComparingTo(ZERO);
  }
}
