package com.soar.connectors.vmwarbonblack;

import com.securonix.at.common.base.ServiceBaseTest;
import com.securonix.at.common.util.testreport.CustomSoftAssert;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import models.Pet;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class VMwareCarbonBlackConnectorTest extends ServiceBaseTest {

    @Test
    public void myTest() {
        testConfig.getDbEnv();
        System.out.println(testConfig.getServiceEnv());
        String url = "https://64f4df6e-faf2-499a-8328-0e5702cd4797.mock.pstmn.io";
        List<Header> lstHeader = new ArrayList<>();
        lstHeader.add(new Header("Content-Type","application/json"));
        Headers headers = new Headers(lstHeader);
        Response response =
                playersServiceClient.getPlayer(url,"3",headers);
        CustomSoftAssert softAssert=getCustomSoftAssert();
        softAssert.assertEquals(response.getStatusCode(),200, "Status code is 200");
        String responseStr = response.asString();
        extentLogger.logInfo(responseStr);
        response =
                playersServiceClient.getPetById("3",headers);
        Pet pl = response.getBody().as(Pet.class);
        extentLogger.logInfo(pl.getId() + "-" + pl.getType() + "-" + pl.getPrice());
        softAssert.assertEquals(response.getStatusCode(),200 ,"Status code is 200");
        extentLogger.logInfo(response.body().asString());
    }
}
