package base;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.After;
import org.junit.Before;

public class BaseTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://localhost:3000";
    }

    @After
    public void tearDown() {
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .delete("/api/books")
                .then()
                .extract()
                .response();
    }
}
