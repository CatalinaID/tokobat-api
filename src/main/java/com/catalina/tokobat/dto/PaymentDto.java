package com.catalina.tokobat.dto;

/**
 * Created by ichakid on 2/17/2016.
 */
public class PaymentDto extends ResponseDto {
    private String urlToPay;

    public PaymentDto() {
    }

    public String getUrlToPay() {
        return urlToPay;
    }

    public void setUrlToPay(String urlToPay) {
        this.urlToPay = urlToPay;
    }
}
