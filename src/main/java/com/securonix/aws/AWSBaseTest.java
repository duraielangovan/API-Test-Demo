package com.securonix.aws;

import com.securonix.at.common.base.ServiceBaseTest;
import com.veyyonorg.at.services.RestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class AWSBaseTest extends ServiceBaseTest
{

    @Autowired
    protected RestHelper restHelper;

    protected String getAwsSeviceUrl()
    {
        return "url";
    }
}

