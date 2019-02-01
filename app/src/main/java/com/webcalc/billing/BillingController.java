package com.webcalc.billing;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillingController {

  private final Billing billing;

  public BillingController(Billing billing) {
    this.billing = billing;
  }

  @GetMapping("/balance")
  public String getBalance() {
    return billing.getBalance().toPlainString();
  }
}
