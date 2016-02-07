package com.catalina.tokobat.dto;

/**
 * Created by Alifa on 2/7/2016.
 */
public class ApotekLoginResponse extends ResponseDto {
    private long code;
    private String name;

    public ApotekLoginResponse(long code, String message, long id, String name) {
        this.code = code;
        this.message = message;
        this.id = id;
        this.name = name;
    }

    public ApotekLoginResponse(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
