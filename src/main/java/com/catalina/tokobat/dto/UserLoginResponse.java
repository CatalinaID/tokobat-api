package com.catalina.tokobat.dto;

import java.io.Serializable;

/**
 *
 * @author icha
 */
public class UserLoginResponse extends ResponseDto implements Serializable {
    private String msisdn;
    private String status;
    private String token;

    public static final String LOGIN_VALID = "VALID";
    public static final String LOGIN_INVALID = "INVALID";
    public static final String LOGIN_BLOCKED = "BLOCKED";

    public UserLoginResponse() {
        super();
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
