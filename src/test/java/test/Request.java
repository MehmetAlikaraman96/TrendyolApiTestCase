package test;

import base.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;


public class Request extends BaseTest {

    String jsonBody = "{ \"id\": 1, \"author\": \"John Smith\", \"title\": \"Reliability of late night\n" +
            "deployments\" }";
    String incorrectJsonBody = "{ \"id\": 1, \"title\": \"Reliability of late night\n" +
            "deployments\" }";
    String emptyFieldJsonBody = "{ \"id\": 1, \"author\": \"\", \"title\": \"Reliability of late night\n" +
            "deployments\" }";

    @Test
    public void it_should_get_empty_store() {
        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .get("/api/books")
                .then()
                .extract()
                .response();
        Assert.assertNull(response.getBody());
    }

    @Test
    public void it_should_put_error_when_missing_field() {
        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(incorrectJsonBody)
                .put("/api/books/1")
                .then()
                .extract()
                .response();
        Assert.assertEquals("Field 'author' is required", response.getBody().jsonPath().getString("error"));
    }

    @Test
    public void it_should_get_error_when_empty_field() {
        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(emptyFieldJsonBody)
                .put("/api/books/1")
                .then()
                .extract()
                .response();
        Assert.assertEquals("Field 'author' is required", response.getBody().jsonPath().getString("error"));
    }

    @Test
    public void it_should_put_item() {
        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .put("/api/books/1")
                .then()
                .extract()
                .response();
    }

    @Test
    public void it_should_get_item() {
        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .get("/api/books/1")
                .then()
                .extract()
                .response();
        Assert.assertEquals(1, response.getBody().jsonPath().getInt("id"));
        Assert.assertEquals("John Smith", response.getBody().jsonPath().getString("author"));
        Assert.assertEquals("SRE 101", response.getBody().jsonPath().getString("title"));
    }

    @Test
    public void it_should_put_error_when_item_is_duplicate() {
        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .put("/api/books/1")
                .then()
                .extract()
                .response();
        Assert.assertEquals("Field 'author' is required", response.getBody().jsonPath().getString("error"));

    }

}
