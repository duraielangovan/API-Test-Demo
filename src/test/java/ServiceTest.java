
import com.veyyon.at.common.base.ServiceBaseClient;
import com.veyyon.at.common.util.testreport.CustomSoftAssert;
import com.veyyon.at.common.base.ServiceBaseTest;
import com.veyyon.at.common.valueObjects.ResponseVO;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import models.Pet;
import models.Player;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ServiceTest extends ServiceBaseTest{

    @Test
    public void myTest() {
        System.out.println(testConfig.getServiceEnv());
        Random rand = new Random();
        String url = "https://64f4df6e-faf2-499a-8328-0e5702cd4797.mock.pstmn.io";
       //String url =  "https://j481jz8137.execute-api.us-west-2.amazonaws.com:443";

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
