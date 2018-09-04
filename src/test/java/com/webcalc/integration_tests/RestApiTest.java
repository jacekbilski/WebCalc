package com.webcalc.integration_tests;

import com.webcalc.app.WebCalcApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(classes = WebCalcApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
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
