package com.webcalc.calculator;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculatorController {

  private final Calculator calculator;

  public CalculatorController(Calculator calculator) {
    this.calculator = calculator;
  }

  @PostMapping("/eval")
  public String calculate(@RequestBody String body) {
    return calculator.eval(body);
  }

  @PutMapping("/maxFractionDigits")
  public void setMaxFractionDigits(@RequestBody String body) {
    calculator.setMaxFractionDigits(Integer.parseInt(body));
  }
}
