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
    EvaluationContext ctx = new EvaluationContext(userId, maxFractionDigits, stack);
    eval(ctx, tokens);
    return format(stack.pop(), maxFractionDigits);
  }

  private void eval(EvaluationContext ctx, String[] tokens) {
    for (String token : tokens)
      getTokenType(token).apply(ctx);
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
      return new Number(token);
    } catch (Exception ignored) {
      return new PredefinedFunction(token);
    }
  }

  private static class EvaluationContext {
    final UUID userId;
    final int maxFractionDigits;
    final Stack<BigDecimal> stack;

    private EvaluationContext(UUID userId, int maxFractionDigits, Stack<BigDecimal> stack) {
      this.userId = userId;
      this.maxFractionDigits = maxFractionDigits;
      this.stack = stack;
    }
  }

  private interface TokenType {
    void apply(EvaluationContext ctx);
  }

  private class Number implements TokenType {
    private final BigDecimal number;

    Number(String number) {
      this.number = parse(number);
    }

    @Override
    public void apply(EvaluationContext ctx) {
      ctx.stack.push(number);
    }

    private BigDecimal parse(String string) {
      try {
        return (BigDecimal) formatter.parse(string);
      } catch (ParseException e) {
        throw new RuntimeException("Cannot recognize a number: '" + string + "'", e);
      }
    }
  }

  private class PredefinedFunction implements TokenType {
    private final String f;

    PredefinedFunction(String f) {
      this.f = f;
    }

    @Override
    public void apply(EvaluationContext ctx) {
      function(f).accept(ctx);
      if (observer != null) {
        observer.evaluated(ctx.userId, f);
      }
    }

    private Consumer<EvaluationContext> function(String function) {
      return ctx -> {
        BigDecimal result;
        switch (function) {
          case "+":
            result = ctx.stack.pop().add(ctx.stack.pop());
            break;
          case "-":
            var a = ctx.stack.pop();
            var b = ctx.stack.pop();
            result = b.subtract(a);
            break;
          case "*":
            result = ctx.stack.pop().multiply(ctx.stack.pop());
            break;
          case "/":
            var c = ctx.stack.pop();
            var d = ctx.stack.pop();
            result = d.divide(c, ctx.maxFractionDigits, RoundingMode.HALF_UP);
            break;
          case "π":
            result = BigDecimal.valueOf(Math.PI);
            break;
          case "^2":
            result = ctx.stack.pop().pow(2);
            break;
          default:
            throw new RuntimeException("Unsupported function: " + function);
        }
        ctx.stack.push(result);
      };
    }
  }

  private class CustomFunction implements TokenType {
    final String def;

    CustomFunction(String def) {
      this.def = def;
    }

    @Override
    public void apply(EvaluationContext ctx) {
      eval(ctx, def.split(" "));
    }
  }
}
