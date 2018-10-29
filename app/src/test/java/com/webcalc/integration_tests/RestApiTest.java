package com.webcalc.integration_tests;

import com.webcalc.app.WebCalcApplication;
import io.restassured.filter.session.SessionFilter;
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

  @Test
  void settingMaxFractionDigits() {
    given()
        .body("5")
    .when()
        .put("/maxFractionDigits")
    .then()
        .statusCode(200);

    given()
        .body("8 7 /")
    .when()
        .post("/eval")
    .then()
        .body(equalTo("1,14286"));
  }

  @Test
  void maxFractionDigitsAffectsOnlyCurrentSession() {
    SessionFilter session1 = new SessionFilter();
    SessionFilter session2 = new SessionFilter();

    given()
        .filter(session1)
        .body("3")
    .when()
        .put("/maxFractionDigits")
    .then()
        .statusCode(200);

    given()
        .filter(session2)
        .body("5")
    .when()
        .put("/maxFractionDigits")
    .then()
        .statusCode(200);

    given()
        .filter(session1)
        .body("8 7 /")
    .when()
        .post("/eval")
    .then()
        .body(equalTo("1,143"));

    given()
        .filter(session2)
        .body("8 7 /")
    .when()
        .post("/eval")
    .then()
        .body(equalTo("1,14286"));
  }
}
