package com.webcalc.calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Stack;
import java.util.UUID;
import java.util.function.Consumer;

public class Calculator {

  static final int DEFAULT_MAX_FRACTION_DIGITS = 2;

  private final NumberFormat formatter;

  private CalculatorObserver observer;

  private final Map<String, String> customFunctions = new HashMap<>();

  public Calculator() {
    formatter = DecimalFormat.getNumberInstance(Locale.GERMANY);
    if (formatter instanceof DecimalFormat)
      ((DecimalFormat) formatter).setParseBigDecimal(true);
  }

  public String eval(UUID userId, String input, int maxFractionDigits) {
    String[] tokens = input.trim().split(" ");
    var stack = new Stack<BigDecimal>();
    eval(userId, stack, tokens, maxFractionDigits);
    return format(stack.pop(), maxFractionDigits);
  }

  private void eval(UUID userId, Stack<BigDecimal> stack, String[] tokens, int maxFractionDigits) {
    for (String token : tokens) {
      var tokenType = getTokenType(token);
      if (tokenType instanceof Number) {
        stack.push(((Number) tokenType).number);
      }
      if (tokenType instanceof CustomFunction) {
        eval(userId, stack, ((CustomFunction) tokenType).def.split(" "), maxFractionDigits);
      }
      if (tokenType instanceof PredefinedFunction) {
        function(((PredefinedFunction) tokenType).f, maxFractionDigits).accept(stack);
        if (observer != null) {
          observer.evaluated(userId, token);
        }
      }
    }
  }

  private Consumer<Stack<BigDecimal>> function(String function, int maxFractionDigits) {
    return stack -> {
      BigDecimal result;
      switch (function) {
        case "+":
          result = stack.pop().add(stack.pop());
          break;
        case "-":
          var a = stack.pop();
          var b = stack.pop();
          result = b.subtract(a);
          break;
        case "*":
          result = stack.pop().multiply(stack.pop());
          break;
        case "/":
          var c = stack.pop();
          var d = stack.pop();
          result = d.divide(c, maxFractionDigits, RoundingMode.HALF_UP);
          break;
        case "π":
          result = BigDecimal.valueOf(Math.PI);
          break;
        case "^2":
          result = stack.pop().pow(2);
          break;
        default:
          throw new RuntimeException("Unsupported function: " + function);
      }
      stack.push(result);
    };
  }

  private BigDecimal parse(String string) {
    try {
      return (BigDecimal) formatter.parse(string);
    } catch (ParseException e) {
      throw new RuntimeException("Cannot recognize a number: '" + string + "'", e);
    }
  }

  private String format(BigDecimal result, int maxFractionDigits) {
    NumberFormat formatter = (NumberFormat) this.formatter.clone();
    formatter.setMaximumFractionDigits(maxFractionDigits);
    return formatter.format(result);
  }

  public void addObserver(CalculatorObserver observer) {
    this.observer = observer;
  }

  public void defineCustomFunction(String definition) {
    var nameEnd = definition.indexOf(" ");
    customFunctions.put(definition.substring(0, nameEnd), definition.substring(nameEnd + 1));
  }

  private TokenType getTokenType(String token) {
    if (customFunctions.containsKey(token)) {
      return new CustomFunction(customFunctions.get(token));
    }
    try {
      return new Number(parse(token));
    } catch (Exception ignored) {
      return new PredefinedFunction(token);
    }
  }

  private interface TokenType {}

  private static class Number implements TokenType {
    final BigDecimal number;

    Number(BigDecimal number) {
      this.number = number;
    }
  }

  private static class PredefinedFunction implements TokenType {
    final String f;

    PredefinedFunction(String f) {
      this.f = f;
    }
  }

  private static class CustomFunction implements TokenType {
    final String def;

    CustomFunction(String def) {
      this.def = def;
    }
  }
}
