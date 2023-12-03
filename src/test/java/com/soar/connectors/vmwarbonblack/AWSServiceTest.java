package com.soar.connectors.vmwarbonblack;

import com.securonix.at.common.base.ServiceBaseTest;
import com.securonix.at.common.constants.HeadersUtil;
import com.securonix.at.common.util.testreport.CustomSoftAssert;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import models.Pet;
import org.testng.annotations.Test;

public class AWSServiceTest extends ServiceBaseTest
{

    @Test
    public void myTest() {
        System.out.println(testConfig.getServiceEnv());
        String url = "https://64f4df6e-faf2-499a-8328-0e5702cd4797.mock.pstmn.io";           
        Headers headers = HeadersUtil.getHeaders("JSON", "true");
        
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
