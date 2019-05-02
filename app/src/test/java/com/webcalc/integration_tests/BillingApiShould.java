package com.webcalc.integration_tests;

import com.webcalc.app.WebCalcApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(classes = WebCalcApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class BillingApiShould {

  private static final String USER1_NAME = "mmustermann";
  private static final String USER1_PASS = "9786f3gb4508c2393q7y";

  private static final String USER2_NAME = "user2_name";
  private static final String USER2_PASS = "user2_pass";

  @Test
  void returnsZeroWhenNoCalculationsDone() {
    given()
        .auth().preemptive().basic(USER1_NAME, USER1_PASS)
    .when()
        .get("/balance")
    .then()
        .body(equalTo("0"));
  }

  @Test
  void chargeOnlyUser1_when1IsCalculating() {
    given()
        .auth().preemptive().basic(USER1_NAME, USER1_PASS)
        .body("1 2 +")
    .when()
        .post("/eval");

    given()
        .auth().preemptive().basic(USER1_NAME, USER1_PASS)
    .when()
        .get("/balance")
    .then()
        .body(equalTo("1"));

    given()
        .auth().preemptive().basic(USER2_NAME, USER2_PASS)
    .when()
        .get("/balance")
    .then()
        .body(equalTo("0"));
  }
}
