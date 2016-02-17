package com.catalina.tokobat.dto;

import org.springframework.core.enums.StringCodedLabeledEnum;

import java.math.BigDecimal;

/**
 * Created by ichakid on 2/17/2016.
 */
public class TicketDto {
    public BigDecimal amount;
    public String traceNumber;
    public String status;
    public String ticketID;
}
