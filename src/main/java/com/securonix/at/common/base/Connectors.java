package com.securonix.at.common.base;

public enum Connectors {

    CARBON_BLACK("com.securonix.connectors.vmwarecarbonblack.service.VMWareCarbonBlackConnector",
            "soar/connector-vmwarecarbonblack/vmwarecarbonblackconnector"),
    RECORDED_FUTURE("com.securonix.connectors.vmwarecarbonblack.service.VMWareCarbonBlackConnector",
            "soar/connector-vmwarecarbonblack/vmwarecarbonblackconnector"),
    VIRUS_TOTAL("com.securonix.connectors.vmwarecarbonblack.service.VMWareCarbonBlackConnector",
                         "soar/connector-vmwarecarbonblack/vmwarecarbonblackconnector"),
    OFFICE365("com.securonix.connectors.vmwarecarbonblack.service.VMWareCarbonBlackConnector",
                         "soar/connector-vmwarecarbonblack/vmwarecarbonblackconnector"),
    SENTINEL_ONE("com.securonix.connectors.vmwarecarbonblack.service.VMWareCarbonBlackConnector",
                         "soar/connector-vmwarecarbonblack/vmwarecarbonblackconnector"),
    DOMAIN_TOOLS("com.securonix.connectors.vmwarecarbonblack.service.VMWareCarbonBlackConnector",
                         "soar/connector-vmwarecarbonblack/vmwarecarbonblackconnector"),
    SLACK("com.securonix.connectors.vmwarecarbonblack.service.VMWareCarbonBlackConnector",
                         "soar/connector-vmwarecarbonblack/vmwarecarbonblackconnector");


    private String clazz;
    private String endPoint;

    Connectors(String clazz,String endPoint){
        this.clazz=clazz;
        this.endPoint=endPoint;
    }

    public String clazz(){
        return this.clazz;
    }
    public String endPoint(){
        return this.endPoint;
    }

}
