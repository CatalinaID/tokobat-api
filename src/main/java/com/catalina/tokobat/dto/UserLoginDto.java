package com.catalina.tokobat.dto;

import java.io.Serializable;

/**
 *
 * @author icha
 */
public class UserLoginDto extends ResponseDto implements Serializable {
    private String name;

    public UserLoginDto() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
