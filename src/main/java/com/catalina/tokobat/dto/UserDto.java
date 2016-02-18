package com.catalina.tokobat.dto;

import com.catalina.tokobat.entity.User;

/**
 * Created by Alifa on 2/7/2016.
 */
public class UserDto  extends ResponseDto{
    private String msisdn;
    private String uid;
    private String name;
    private String session;

    public UserDto(String message, long id) {
        super(message, id);
    }

    public UserDto(String msisdn, String uid, String name, String session) {
        this.msisdn = msisdn;
        this.uid = uid;
        this.name = name;
        this.session = session;
    }

    public UserDto(String message, long id,User user) {
        super(message, id);
//        this.msisdn = user.getMsisdn();
//        this.uid = user.getUid();
        this.name = user.getName();
//        this.session = user.getSession();
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}
