package com.securonix.at.common.util.testreport;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReportListener extends AbstractTestExecutionListener {

    private static final ThreadLocal<ExtentTest> TEST_THREAD_LOCAL = new ThreadLocal<>();
    private ExtentReports reports;
    private ExtentLogger extentLogger;

    /**
     *
     * @param inTestContext
     */
    public void init(TestContext inTestContext){
        reports = inTestContext.getApplicationContext().getBean(ExtentReports.class);
        extentLogger = inTestContext.getApplicationContext().getBean(ExtentLogger.class);
    }

    @Override
    public void beforeTestClass(TestContext inTestContext){
        init(inTestContext);
        ExtentTest test=null;
        test = reports.createTest(inTestContext.getClass().getSimpleName()+" Before class");
        test.assignCategory(inTestContext.getClass().getName());
        setExtentTest(test);
    }
    @Override
    public void beforeTestMethod(TestContext inTestContext) throws Exception {
        ExtentTest test=null;
        test = TEST_THREAD_LOCAL.get();
        List<ITestResult> currentTestResults = getTestResults();
        ITestResult tr=null;
        if(currentTestResults.size()>1){
            tr = currentTestResults.get(0);
            if(tr.getMethod().getConstructorOrMethod().getMethod().getAnnotation(BeforeClass.class)!=null)
            {
                if(tr.getStatus()==2){
                    test.log(Status.FAIL, MarkupHelper.createLabel(tr.getName(), ExtentColor.RED));
                    test.log(Status.FAIL,tr.getThrowable());
                }
            }
        }
        else if (!test.getModel().hasLog()){
            test.getExtent().removeTest(test);
        }
        test = reports.createTest(inTestContext.getTestMethod().getName());
        test.assignCategory(inTestContext.getClass().getName());
        setExtentTest(test);
        reports.flush();
    }

    @Override
    public void beforeTestExecution(TestContext testContext) throws Exception {
    }
    @Override
    public void afterTestExecution(TestContext testContext) throws Exception {
        reports.flush();
    }
    @Override
    public void afterTestMethod(TestContext inTestContext) throws Exception {
        List<ITestResult> results = getTestResults();
        for (ITestResult result : results) {
            reportTestResult(result,inTestContext.getTestMethod());
        }
        reports.flush();
        ExtentTest test=null;
        test = reports.createTest(inTestContext.getClass().getSimpleName()+"AfterClass");
        test.assignCategory(inTestContext.getClass().getName());
        setExtentTest(test);
    }

    @Override
    public void afterTestClass(TestContext inTestContext) throws Exception {
        ExtentTest test=null;
        test = TEST_THREAD_LOCAL.get();
        List<ITestResult> currentTestResults = getTestResults();
        ITestResult tr=null;
        if(currentTestResults.size()>1){
            tr = currentTestResults.get(0);
            if(tr.getMethod().getConstructorOrMethod().getMethod().getAnnotation(AfterClass.class)!=null)
            {
                if(tr.getStatus()==2){
                    test.log(Status.FAIL, MarkupHelper.createLabel(tr.getName(), ExtentColor.RED));
                    test.log(Status.FAIL,tr.getThrowable());
                }
            }
        }
        else if (!test.getModel().hasLog()){
            test.getExtent().removeTest(test);
        }
        reports.flush();
    }
    /**
     * Set the thread local version of test
     * @param test
     */
    private void setExtentTest(ExtentTest test) {
        TEST_THREAD_LOCAL.set(test);
        extentLogger.setExtentTest(TEST_THREAD_LOCAL.get());
    }

    private List<ITestResult> getTestResults() {
        ITestContext context = Reporter.getCurrentTestResult().getTestContext();
        List<ITestResult> allResults = new ArrayList<>();
        allResults.addAll(context.getPassedTests().getAllResults());
        allResults.addAll(context.getFailedTests().getAllResults());
        allResults.addAll(context.getSkippedTests().getAllResults());
        allResults.addAll(context.getFailedConfigurations().getAllResults());
        context.getPassedTests().getAllResults().clear();
        context.getFailedTests().getAllResults().clear();
        context.getSkippedTests().getAllResults().clear();
        context.getFailedConfigurations().getAllResults().clear();

        return allResults;
    }

    /**
     *
     * @param result
     * @param testMethod
     */
    private void reportTestResult(ITestResult result, Method testMethod) {

        ExtentTest test = null;
        test= TEST_THREAD_LOCAL.get();
        boolean beforeMethod = false;
        boolean afterMethod = false;

        if(result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(BeforeMethod.class)!=null){
            beforeMethod=true;
        }

        if(result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(AfterMethod.class)!=null){
            afterMethod=true;
        }

        if(result.getParameters().length>0){
            Object[] params = result.getParameters();
            String name = (String) params[0];
            test.getModel().setName(test.getModel().getName()+"-"+name);
        }
        if(!Objects.equals(result.getAttribute("retry"),null)
                || Objects.equals(result.getAttribute("retry"),"true")){
            reports.removeTest(test);
        }else
        {
            switch (result.getStatus()){
                case 1:
                    test.log(Status.PASS,MarkupHelper.createLabel(result.getName(),ExtentColor.GREEN));
                    break;
                case 2:
                    if(beforeMethod || afterMethod){
                        test.log(Status.INFO,MarkupHelper.createLabel(result.getName(),ExtentColor.RED));
                        test.log(Status.INFO,result.getThrowable());
                    }else if(Objects.equals(result.getMethod().getMethodName(),testMethod.getName())){
                        test.log(Status.FAIL,MarkupHelper.createLabel(result.getName(),ExtentColor.RED));
                        test.log(Status.FAIL,result.getThrowable());
                    }
                    break;
                case 3:
                    test.log(Status.SKIP,MarkupHelper.createLabel(result.getName(),ExtentColor.ORANGE));
                    if(Objects.equals(result.getThrowable(),null)){
                        test.log(Status.FAIL,result.getThrowable());
                    }
                    break;
            }

        }
    }

}
