package com.securonix.at.common.base;

import com.securonix.at.TestConfig;
import com.securonix.at.common.util.testreport.CustomSoftAssert;
import com.securonix.at.common.util.testreport.ExtentLogFilter;
import com.securonix.at.common.util.testreport.ExtentLogger;
import com.securonix.at.common.util.UrlUtil;
import com.securonix.at.common.util.testreport.ReportListener;
import io.restassured.RestAssured;
import kafka.consumer.SecuronixKafkaConsumer;
import kafka.handler.RecordsHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;

import java.util.List;

@SpringBootTest(classes = TestConfig.class)
@TestExecutionListeners(ReportListener.class)
public class ServiceBaseTest extends AbstractTestNGSpringContextTests
{
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

    public <T> SecuronixKafkaConsumer<T> runConsumer(String topicName, List<T> inList, T clazz)
    {

        RecordsHandler<T> recordsKafkaHandler = new RecordsHandler<>(inList);
        return
                new SecuronixKafkaConsumer.ConsumerBuilder<T>()
                        .setBroker(getKafkaServer())
                        .setTopicName(topicName)
                        .setSecurityProtocol(testConfig.getKafkaSecurityProtocol())
                        .setSslKeyPassword(testConfig.getKafkaKeyPassword())
                        .setSslTrustStoreLocation(testConfig.getKafkaTruststorePath())
                        .setSslTrustStorePassword(testConfig.getKafkaTruststorePassword())
                        .setSslKeyStoreLocation(testConfig.getKafkaKeystorePath())
                        .setSslKeyStorePassword(testConfig.getKafkaKeystorePassword())
                        .setRecordsHandler(recordsKafkaHandler)
                        .setvalueClass((Class<T>) clazz.getClass())
                        .build();

    }

    public String getKafkaServer(){
        return "qbcaxokgwjpf.securonix.net:9093,cakmllpmtmvw.securonix.net:9093,zfrsocsraaar.securonix.net:9093";
    }
}
