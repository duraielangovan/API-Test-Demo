package com.veyyon.at.common.valueObjects;

import javax.xml.bind.annotation.*;
import java.util.Arrays;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Response")
public class ResponseVO {
    @XmlAttribute
    private String message;
    @XmlAttribute
    private String code;
    @XmlAttribute
    private String statusCodeDescription;
    @XmlAttribute
    private String suggestedAction;
    @XmlElement
    private String[] additionalMsg;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatusCodeDescription() {
        return statusCodeDescription;
    }

    public void setStatusCodeDescription(String statusCodeDescription) {
        this.statusCodeDescription = statusCodeDescription;
    }

    public String getSuggestedAction() {
        return suggestedAction;
    }

    public void setSuggestedAction(String suggestedAction) {
        this.suggestedAction = suggestedAction;
    }

    public String[] getAdditionalMsg() {
        return additionalMsg;
    }

    public void setAdditionalMsg(String[] additionalMsg) {
        this.additionalMsg = additionalMsg;
    }

    @Override
    public String toString() {
        return "ResponseVO{" +
                "message='" + message + '\'' +
                ", code='" + code + '\'' +
                ", statusCodeDescription='" + statusCodeDescription + '\'' +
                ", suggestedAction='" + suggestedAction + '\'' +
                ", additionalMsg=" + Arrays.toString(additionalMsg) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseVO that = (ResponseVO) o;
        return Objects.equals(message, that.message) && Objects.equals(code, that.code) && Objects.equals(statusCodeDescription, that.statusCodeDescription) && Objects.equals(suggestedAction, that.suggestedAction) && Arrays.equals(additionalMsg, that.additionalMsg);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(message, code, statusCodeDescription, suggestedAction);
        result = 31 * result + Arrays.hashCode(additionalMsg);
        return result;
    }
}
