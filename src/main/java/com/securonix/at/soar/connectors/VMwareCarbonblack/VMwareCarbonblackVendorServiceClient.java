package com.securonix.at.soar.connectors.VMwareCarbonblack;

import com.securonix.at.common.base.ServiceBaseClient;
import io.restassured.http.Headers;
import io.restassured.response.Response;

public class VMwareCarbonblackVendorServiceClient extends ServiceBaseClient {

    String GET_PLAYER_PATH = "/rest/api/player/{{id}}";
    public Response getPlayer(String url, String id, Headers headers){
        url = (url+GET_PLAYER_PATH).replace("{{id}}",id);
        return super.executeGetCall(url,headers);
    }

    public Response getPetById(String petId, Headers headers){
        String path = "/test/pets/" + petId;
        String url = "https://j481jz8137.execute-api.us-west-2.amazonaws.com:443" + path;
        return super.executeGetCall(url,headers);
    }
}
