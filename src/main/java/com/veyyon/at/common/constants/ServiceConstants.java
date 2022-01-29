package com.veyyon.at.common.constants;

public class ServiceConstants {

    public enum Format {
        XML("XML"),
        JSON("JSON");

        private String format;
        Format(String format) {
            this.format=format;
        }
        @Override
        public String toString() {
            return this.format;
        }
    }

    public enum Method {
        GET("GET"),
        PUT("PUT"),
        DELETE("DELETE"),
        POST("POST");

        private String method;
        Method(String format) {
            this.method=format;
        }

        @Override
        public String toString() {
            return this.method;
        }
    }

    public enum ResponseCode{
        SUCCESS_200(200),
        SUCCESS_204(204),
        ERROR_400(400),
        ERROR_500(500),
        ERROR_404(404),
        ERROR_401(401),
        ERROR_501(501);

        private int code;
        ResponseCode(int code) {
            this.code = code;
        }
        public  int code(){
            return code;
        }

        @Override
        public String toString() {
            return String.valueOf(code);
        }
    }

    public enum DataType{
        VALID("VALID"),
        INVALID("INVALID"),
        DUPLICATE("DUPLICATE"),
        EXISTING("EXISTING"),
        NON_EXISTING("NON_EXISTING");

        private String type;
        DataType(String type) {
            this.type = type;
        }
        @Override
        public String toString() {
            return String.valueOf(type);
        }
    }
}
