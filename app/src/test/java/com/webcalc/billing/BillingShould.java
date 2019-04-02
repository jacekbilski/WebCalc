package com.webcalc.billing;

import com.webcalc.calculator.Calculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static org.assertj.core.api.Assertions.assertThat;

class BillingShould {

  private final Billing billing = new Billing();
  private final Calculator calculator = new Calculator();

  @BeforeEach
  void setUp() {
    calculator.addObserver(billing);
  }

  @Test
  void returnBalanceZero_whenNoCalculationsWereDone() {
    BigDecimal balance = billing.getBalance();
    assertThat(balance).isEqualByComparingTo(ZERO);
  }

  @Test
  void returnBalanceOfOne_afterCalculatingOneAddition() {
    calculator.eval("1 2 +", 0);
    BigDecimal balance = billing.getBalance();
    assertThat(balance).isEqualByComparingTo(ONE);
  }

  @Test
  void returnBalanceOfTwo_afterCalculatingTwoAdditions() {
    calculator.eval("1 2 +", 0);
    calculator.eval("1 2 +", 0);
    BigDecimal balance = billing.getBalance();
    assertThat(balance).isEqualByComparingTo(new BigDecimal("2"));
  }

  @Test
  void returnBalanceOfFive_afterCalculatingOneMultiplication() {
    calculator.eval("3 2 *", 0);
    BigDecimal balance = billing.getBalance();
    assertThat(balance).isEqualByComparingTo(new BigDecimal("5"));
  }
}
