package com.catalina.tokobat.dto;

import java.math.BigDecimal;

/**
 * Created by Alifa on 2/7/2016.
 */
public class ApotekDto extends ResponseDto {

    private String name;
    private String username;
    private String address;
    private String lat;
    private String lng;
    private BigDecimal balance;

    public ApotekDto(long id, String name, String username, String address, String lat, String lng, BigDecimal balance, String msg) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.balance = balance;
        this.message=msg;
    }

    public ApotekDto(long id, String name, String username, String address, String lat, String lng) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
    }

    public ApotekDto(long id, String msg) {
        this.id = id;
        this.message = msg;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
