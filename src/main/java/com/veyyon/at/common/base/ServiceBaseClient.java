package com.veyyon.at.common.base;

import com.veyyon.at.common.util.UrlUtil;
import com.veyyon.at.common.util.testreport.ExtentLogger;
import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public abstract class ServiceBaseClient {

    @Autowired
    private ExtentLogger extentLogger;
    @Autowired
    private UrlUtil urlUtil;

    protected Response executeGetCall(String url, Headers headers, Map<String,String> queryParam){
        extentLogger.logInfo("url : "+ url);
        Headers headers1 = new Headers();

        return RestAssured.given()
                .headers(headers)
                .queryParams(queryParam)
                .when().get(url).then()
                .extract().response();


    }

    protected Response executeGetCall(String url, Headers headers){
        extentLogger.logInfo("url : "+ url);
        return RestAssured
                .given()
             //   .headers(headers)
                .when()
                .get(url)
                .then()
                .extract().response();
    }

    protected Response executePostCall(String url, Headers headers, Object requestVO){
        extentLogger.logInfo("url : "+ url);
        return RestAssured.given()
                .headers(headers)
                .body(requestVO)
                .when().get(url).then()
                .extract().response();
    }

    protected Response executePutCall(String url, Headers headers, Object requestVO){
        extentLogger.logInfo("url : "+ url);
        return RestAssured.given()
                .headers(headers)
                .body(requestVO)
                .when().get(url).then()
                .extract().response();
    }


}
