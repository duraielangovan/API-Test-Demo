package com.veyyon.at.common.util.testreport;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExtentLogger {

    private static final Logger logger = LoggerFactory.getLogger(ExtentLogger.class);
    private static final ThreadLocal<ExtentTest> TEST_THREAD_LOCAL = new ThreadLocal<>();

    @Autowired
    private boolean getReportLogConsole;

    public void setExtentTest(ExtentTest inExtentTest){
        TEST_THREAD_LOCAL.set(inExtentTest);
    }
    public ExtentTest getExtentTest(){
        return TEST_THREAD_LOCAL.get();
    }

    public void logInfo(String inMessage){
        getExtentTest().info(inMessage);
        if(getReportLogConsole){
            logger.info(inMessage);
        }
    }

    public void logRequestInfo(String inMessage){
        getExtentTest().info(MarkupHelper.createCodeBlock(inMessage));
        if(getReportLogConsole){
            logger.info(inMessage);
        }
    }

    public void logPass(String inMessage){
        getExtentTest().log(Status.PASS, MarkupHelper.createLabel(inMessage, ExtentColor.GREEN));
        if(getReportLogConsole){
            logger.info(inMessage);
        }
    }

    public void logFail(String inMessage){
        getExtentTest().log(Status.PASS, MarkupHelper.createLabel(inMessage, ExtentColor.RED));
        if(getReportLogConsole){
            logger.info(inMessage);
        }
    }
}
