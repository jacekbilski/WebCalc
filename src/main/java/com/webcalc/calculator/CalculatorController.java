package com.webcalc.calculator;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculatorController {

  @PostMapping("/eval")
  public String calculate() {
    return new Calculator().eval("");
  }
}
