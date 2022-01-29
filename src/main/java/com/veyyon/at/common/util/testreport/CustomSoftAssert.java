package com.veyyon.at.common.util.testreport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.testng.asserts.IAssert;
import org.testng.asserts.SoftAssert;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CustomSoftAssert extends SoftAssert {

    @Autowired
    ExtentLogger extentLogger;
    @Override
    public void onAssertSuccess(IAssert<?> assertCommand) {
        String message = String.format("Expected [%s] found [%s] - [%s]",assertCommand.getExpected(),
                assertCommand.getActual(),assertCommand.getMessage());
        extentLogger.logPass(message);
    }

    @Override
    public void onAssertFailure(IAssert<?> assertCommand, AssertionError ex) {
        String message = String.format("Expected [%s] found [%s] - [%s]",assertCommand.getExpected(),
                assertCommand.getActual(),assertCommand.getMessage());
        extentLogger.logFail(message);
    }

}
