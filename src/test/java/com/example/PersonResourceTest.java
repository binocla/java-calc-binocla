package com.example;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.StringContains.*;

@QuarkusTest
public class PersonResourceTest {

    @Test
    public void testAddEndpoint() {
        given()
                .when()
                .get("/person/Default")
                .then()
                .statusCode(200)
                .body(containsStringIgnoringCase("Default"));
    }
}
