package com.webcalc.calculator;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculatorController {

  private final Calculator calculator;

  public CalculatorController(Calculator calculator) {
    this.calculator = calculator;
  }

  @PostMapping("/eval")
  public String calculate() {
    return calculator.eval("");
  }
}
