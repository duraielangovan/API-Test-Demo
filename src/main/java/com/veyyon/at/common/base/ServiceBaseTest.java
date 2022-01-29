package com.veyyon.at.common.base;

import com.veyyon.at.TestConfig;
import com.veyyon.at.common.util.UrlUtil;
import com.veyyon.at.common.util.testreport.CustomSoftAssert;
import com.veyyon.at.common.util.testreport.ExtentLogFilter;
import com.veyyon.at.common.util.testreport.ExtentLogger;
import com.veyyon.at.common.util.testreport.ReportListener;
import io.restassured.RestAssured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;

@SpringBootTest(classes = TestConfig.class)
@TestExecutionListeners(ReportListener.class)
public class ServiceBaseTest extends AbstractTestNGSpringContextTests {

    @Autowired
    protected TestConfig testConfig;

    @Autowired
    protected ApplicationContext context;

    @Autowired
    protected ExtentLogFilter extentLogFilter;

    @Autowired
    protected UrlUtil urlUtil;

    @Autowired
    protected ExtentLogger extentLogger;

    @Autowired
    protected PlayersServiceClient playersServiceClient;


    @BeforeClass(alwaysRun = true)
    public void setUp(){
        RestAssured.useRelaxedHTTPSValidation("TLSv1.2");

        if(RestAssured.filters().contains(extentLogFilter)){
            RestAssured.filters(extentLogFilter);
        }
        //RestAssured.proxy("localhost",8080);
    }

    protected String urlSetup(String inEnv, String inPath, String inDB){
        String myDB = testConfig.getDbEnv();
        return urlUtil.buildUrl(inEnv,inPath,inDB);

    }
    public CustomSoftAssert getCustomSoftAssert(){
        return context.getBean(CustomSoftAssert.class);
    }

}
