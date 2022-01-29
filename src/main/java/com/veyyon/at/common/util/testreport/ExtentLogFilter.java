package com.veyyon.at.common.util.testreport;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ExtentLogFilter implements Filter {

    @Autowired
    private ExtentLogger extentLogger;

    @Override
    public Response filter(FilterableRequestSpecification filterableRequestSpecification,
                           FilterableResponseSpecification filterableResponseSpecification, FilterContext filterContext) {

        Response response = filterContext.next(filterableRequestSpecification,filterableResponseSpecification);
        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append("\n\n");
        requestBuilder.append("REQUEST METHOD: ");
        requestBuilder.append("\n");
        requestBuilder.append(filterableRequestSpecification.getMethod());
        requestBuilder.append("\n\n");
        requestBuilder.append("REQUEST URL: ");
        requestBuilder.append("\n");
        requestBuilder.append(filterableRequestSpecification.getURI());

        if((!Objects.equals(filterableRequestSpecification.getHeaders(),null)) &&
         (!Objects.equals(filterableRequestSpecification.getHeaders().toString(),null))){
            requestBuilder.append("REQUEST PARAMETERS : ");
            requestBuilder.append("\n");
            requestBuilder.append(filterableRequestSpecification.getHeaders().toString());
            requestBuilder.append("\n\n");
        }

        if((!Objects.equals(filterableRequestSpecification.getBody(),null)) &&
                (!Objects.equals(filterableRequestSpecification.getBody().toString(),null))){
            requestBuilder.append("REQUEST BODY : ");
            requestBuilder.append("\n");
            requestBuilder.append(filterableRequestSpecification.getBody().toString());
            requestBuilder.append("\n\n");
        }
        extentLogger.logRequestInfo(requestBuilder.toString());
        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append("\n\n");
        responseBuilder.append("Response STATUS CODE: ");
        responseBuilder.append("\n");
        responseBuilder.append(response.getStatusLine());

        if((!Objects.equals(response.getCookies(),null)) &&
                (!Objects.equals(response.getCookies().toString(),null))){
            responseBuilder.append("Response COOKIES : ");
            responseBuilder.append("\n");
            responseBuilder.append(response.getCookies().toString());
            responseBuilder.append("\n\n");
        }

        if((!Objects.equals(response.getHeaders(),null)) &&
                (!Objects.equals(response.getHeaders().toString(),null))){
            responseBuilder.append("Response PARAMETERS : ");
            responseBuilder.append("\n");
            responseBuilder.append(response.getHeaders().toString());
            responseBuilder.append("\n\n");
        }

        if((!Objects.equals(response.getBody(),null)) &&
                (!Objects.equals(response.getBody().toString(),null))){
            responseBuilder.append("Response BODY : ");
            responseBuilder.append("\n");
            responseBuilder.append(response.getBody().toString());
            responseBuilder.append("\n\n");
        }
        extentLogger.logRequestInfo(responseBuilder.toString());
        return response;
    }

}
