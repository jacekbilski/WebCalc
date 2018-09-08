package com.webcalc.app;

import com.webcalc.calculator.Calculator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.webcalc")
public class WebCalcApplication {

  public static void main(String[] args) {
    SpringApplication.run(WebCalcApplication.class, args);
  }

  @Bean
  public Calculator calculator() {
    return new Calculator();
  }
}
