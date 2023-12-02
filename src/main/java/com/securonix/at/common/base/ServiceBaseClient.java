package com.securonix.at.common.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.securonix.at.common.util.UrlUtil;
import com.securonix.at.common.util.testreport.ExtentLogger;
import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
        Response response =  RestAssured
                .given()
                .headers(headers)
                .when()
                .get(url)
                .then()
                .extract().response();

        return response;

    }

    protected Response executePostCall(String url, Headers headers, Object requestVO){
       return executePostCall(url,headers,new ObjectMapper().convertValue(requestVO,Map.class));
           }

    protected Response executePostCall(String url, Headers headers, Map<Object,Object> requestBody){
        extentLogger.logInfo("url : "+ url);
        return RestAssured.given()
                .headers(headers)
                .body(requestBody)
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
