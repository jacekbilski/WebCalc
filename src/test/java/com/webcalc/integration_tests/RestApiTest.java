package com.webcalc.integration_tests;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

class RestApiTest {

  @Test
  void invokesWebCalcRestApi() {
    given()
        .body("1 2 +")
    .when()
        .post("/eval")
    .then()
        .body(equalTo("3"));
  }
}
