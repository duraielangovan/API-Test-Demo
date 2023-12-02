package com.securonix.at.common.constants;

import io.restassured.http.Header;
import io.restassured.http.Headers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HeadersUtil {

    public static final String XML_HEADERS = "application/json";
    public static final String JSON_HEADERS = "application/xml";
    public static final String CONTENT_TYPE = "Content-type";
    public static final String X_APPLICATION = "X-Application-type";
    public static final String X_SERVER = "X-SERVER";
    public static final String ACCEPT = "Accept";



    public static Headers getHeaders(String inContentType, String inXApplication,String inXServer)
    {
        return new Headers(getHeadersList(inContentType,inXApplication, inXServer));
    }
    public static Headers addAdditionalHeader(Headers inHeaders, String inKey, String inValue)
    {
        List<Header> lstHeader = new ArrayList<>();
        lstHeader.addAll(inHeaders.asList());
        lstHeader.add(new Header(inKey, inValue));
        return new Headers(lstHeader);
    }

    private static List<Header> getHeadersList(String inContentType, String inXapplication,String inXServer)
    {
        List<Header> lstHeader = new ArrayList<>();

        String contentType = Objects.equals("XML",inContentType)
                ? XML_HEADERS : JSON_HEADERS;
        lstHeader.add(new Header(CONTENT_TYPE, contentType));
        lstHeader.add(new Header(ACCEPT, contentType));

        if(!Objects.equals(inXapplication, null))
        {
            lstHeader.add(new Header(X_APPLICATION, inXapplication));
        }
        if(!Objects.equals(inXServer, null))
        {
            lstHeader.add(new Header(X_SERVER, inXServer));;
        }
        return lstHeader;

    }
}
