package com.webcalc.billing;

import com.webcalc.calculator.Calculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
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

  @Test
  void returnBalanceOfOne_afterCalculatingOneSubtraction() {
    calculator.eval("3 3 -", 0);
    BigDecimal balance = billing.getBalance();
    assertThat(balance).isEqualByComparingTo(ONE);
  }

  @Test
  void returnBalanceOfTen_afterCalculatingOneDivision() {
    calculator.eval("5 2.5 /", 2);
    BigDecimal balance = billing.getBalance();
    assertThat(balance).isEqualByComparingTo(TEN);
  }

  @DisplayName("Bill complex operations")
  @ParameterizedTest(name = "input: ''{0}'', expected balance: ''{1}''")
  @CsvSource({
      "1 2 *, 5",
      "1 3 0 * +, 6",
      "-1 -1 12 + -, 2",
      "3 6 2 / *, 15",
  })
  void complexBilling(String input, BigDecimal expectedBalance) {
    calculator.eval(input, 2);
    BigDecimal balance = billing.getBalance();
    assertThat(balance).isEqualByComparingTo(expectedBalance);
  }
}
