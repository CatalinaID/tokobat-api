package com.catalina.tokobat.dto;

import com.catalina.tokobat.entity.User;

/**
 * Created by Alifa on 2/7/2016.
 */
public class UserDto  extends ResponseDto{
    private String username;
    private String name;

    public UserDto(String message, long id) {
        super(message, id);
    }

    public UserDto(String username, String uid, String name, String session) {
        this.username = username;
        this.name = name;
    }

    public UserDto(String message, long id,User user) {
        super(message, id);
        this.username = user.getUsername();
//        this.msisdn = user.getMsisdn();
//        this.uid = user.getUid();
        this.name = user.getName();
//        this.session = user.getSession();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
