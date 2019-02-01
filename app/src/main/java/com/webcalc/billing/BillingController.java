package com.webcalc.billing;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillingController {

  @GetMapping("/balance")
  public String getBalance() {
    return "0";
  }
}
