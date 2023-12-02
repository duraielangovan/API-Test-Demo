package com.veyyonorg.at.services;

import com.securonix.at.common.util.testreport.ExtentLogger;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.springframework.stereotype.Component;

import java.util.Map;

import static io.restassured.RestAssured.given;

@Component
public class RestHelper {

    private static ExtentLogger  extentlogger= new ExtentLogger();

    public Response get(Headers inHeaders, Map<String, String> inQueryParams, String inUrl, int inStatusCode)
    {
        return
                given()
                        .headers(inHeaders)
                        .queryParams(inQueryParams)
                        .when()
                        .get(inUrl)
                        .then()
                        .statusCode(inStatusCode)
                        .extract().response();
    }

    public Response get(Headers inHeaders, String inUrl)
    {
        return
                given()
                        .headers(inHeaders)
                        .when()
                        .get(inUrl)
                        .then()
                        .extract().response();
    }
    public <T> Response put(Headers inHeaders, String inUrl, T inRequestBody, int inStatusCode)
    {
        return
                given()
                        .headers(inHeaders)
                        .body(inRequestBody)
                        .when()
                        .put(inUrl)
                        .then()
                        .statusCode(inStatusCode)
                        .extract().response();
    }

    public <T> Response post(Headers inHeaders, String inUrl, T inRequestBody, int inStatusCode)
    {
        return
                given()
                        .headers(inHeaders)
                        .body(inRequestBody)
                        .when()
                        .post(inUrl)
                        .then()
                        .statusCode(inStatusCode)
                        .extract().response();
    }

    public Response delete(Headers inHeaders, String inUrl, int inStatusCode)
    {
        return
                given()
                        .headers(inHeaders)
                        .when()
                        .delete(inUrl)
                        .then()
                        .statusCode(inStatusCode)
                        .extract().response();
    }

}
