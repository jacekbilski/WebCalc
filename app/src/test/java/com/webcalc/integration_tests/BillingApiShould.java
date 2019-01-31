package com.webcalc.integration_tests;

import com.webcalc.app.WebCalcApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(classes = WebCalcApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class BillingApiShould {

  private static final String DEFAULT_USERNAME = "mmustermann";
  private static final String DEFAULT_PASSWORD = "9786f3gb4508c2393q7y";

  @Test
  void returnsZeroWhenNoCalculationsDone() {
    given()
        .auth().preemptive().basic(DEFAULT_USERNAME, DEFAULT_PASSWORD)
    .when()
        .get("/balance")
    .then()
        .body(equalTo("0"));
  }
}
