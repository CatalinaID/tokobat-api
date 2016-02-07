package com.catalina.tokobat.dto;

/**
 * Created by Alifa on 2/7/2016.
 */
public class ResponseDto {

    protected String message;
    protected long id;

    public ResponseDto() {

    }

    public ResponseDto(String message, long id) {
        this.message = message;
        this.id = id;
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
}
