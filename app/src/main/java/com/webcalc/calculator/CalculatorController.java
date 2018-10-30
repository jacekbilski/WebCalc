package com.webcalc.calculator;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class CalculatorController {

  private static final String MAX_FRACTION_DIGITS = "maxFractionDigits";

  private final Calculator calculator;

  public CalculatorController(Calculator calculator) {
    this.calculator = calculator;
  }

  @PostMapping("/eval")
  public String calculate(@RequestBody String body, HttpSession session) {
    return calculator.eval(body, (Integer) session.getAttribute(MAX_FRACTION_DIGITS));
  }

  @PutMapping("/maxFractionDigits")
  public void setMaxFractionDigits(@RequestBody String body, HttpSession session) {
    session.setAttribute(MAX_FRACTION_DIGITS, Integer.parseInt(body));
  }
}
