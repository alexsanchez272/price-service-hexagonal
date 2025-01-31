package com.masc.price_service.e2e;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PriceConsultationSteps {

    @LocalServerPort
    private int port;

    private RequestSpecification request;
    private Response response;
    private String consultationDate;

    @Given("the price service is running")
    public void thePriceServiceIsRunning() {
        request = given()
                .baseUri("http://localhost:" + port)
                .basePath("/api/prices");
    }

    @When("I request the price for product {long} of brand {long}")
    public void iRequestThePriceForProductOfBrand(Long productId, Long brandId) {
        request = request
                .queryParam("productId", productId)
                .queryParam("brandId", brandId);
    }

    @When("the consultation date is {string}")
    public void theConsultationDateIs(String date) {
        this.consultationDate = date;
        response = request
                .queryParam("startDate", date)
                .when()
                .get();
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int statusCode) {
        response.then()
                .statusCode(statusCode);
    }

    @Then("the price list should be {int}")
    public void thePriceListShouldBe(int priceList) {
        response.then()
                .body("priceList", equalTo(priceList));
    }

    @Then("the final price should be {double}")
    public void theFinalPriceShouldBe(double price) {
        response.then()
                .body("price", equalTo((float)price));
    }

    @Then("the brand id should be {int}")
    public void theBrandIdShouldBe(int brandId) {
        response.then()
                .body("brandId", equalTo(brandId));
    }
}
