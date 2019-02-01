package com.webcalc.billing;

import com.webcalc.calculator.Calculator;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static org.assertj.core.api.Assertions.assertThat;

class BillingShould {

  private final Billing billing = new Billing();
  private final Calculator calculator = new BillingCalculator(new Calculator(), billing);

  @Test
  void returnBalanceZero_whenNoCalculationsWereDone() {
    BigDecimal balance = billing.getBalance();
    assertThat(balance).isEqualByComparingTo(ZERO);
  }

  @Test
  void returnBalanceOfOne_afterCalculatingOneSum() {
    calculator.eval("1 2 +", 0);
    BigDecimal balance = billing.getBalance();
    assertThat(balance).isEqualByComparingTo(ONE);
  }
}
