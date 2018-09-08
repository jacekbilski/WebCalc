package com.webcalc.calculator;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculatorController {

  private final Calculator calculator;

  public CalculatorController() {
    calculator = new Calculator();
  }

  @PostMapping("/eval")
  public String calculate() {
    return calculator.eval("");
  }
}
