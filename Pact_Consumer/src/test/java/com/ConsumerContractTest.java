package com;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit.PactProviderRule;
import au.com.dius.pact.consumer.junit.PactVerification;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import io.pactfoundation.consumer.dsl.LambdaDsl;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.hamcrest.CoreMatchers.any;

public class ConsumerContractTest {

    @Rule
    public PactProviderRule provider = new PactProviderRule("provider_service", "www.pact.capitolone.com",
        8080, this);

    @Pact(consumer = "test_consumer")
    public RequestResponsePact pactUserExists(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        return builder
            .given("test GET User details")
            .uponReceiving("GET REQUEST")
            .path("/pact")
            .method("GET")
            .headers(headers)
            .willRespondWith()
            .status(200)
            .body(getUserDetails())
      .toPact();
    }

    private DslPart getUserDetails()
    {
        return LambdaDsl.newJsonBody(
            (o) -> {
                o.numberType("id");
                o.stringType("name");
                o.stringType("city");
                o.stringType("state");
                o.stringType("country");
            }
        ).build();
    }

    @Test
    @PactVerification
    public void givenGet_whenSendRequest_shouldReturn200WithProperHeaderAndBody() {

        // when
        Response response = RestAssured
            .given()
            .port(provider.getConfig().getPort())
            .contentType(ContentType.JSON)
            .get("/pact");

        response.then().assertThat()
            .statusCode(HttpStatus.OK.value())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body("id", any(Integer.class))
            .body("name", any(Integer.class))
            .body("city", any(String.class))
            .body("state", any(String.class))
            .body("country", any(String.class));
    }

}
