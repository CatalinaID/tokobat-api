package com.catalina.tokobat.dto;

import com.catalina.tokobat.common.Constants;
import com.catalina.tokobat.entity.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alifa on 2/13/2016.
 */
public class ListTransactionDto extends ResponseDto {
    List<Transaction> transactions;

    public ListTransactionDto(List<Transaction> l) {
        transactions = l;
        this.id= Constants.SUCCESS_INDEX;
        this.message=Constants.DEFAULT_SUCCESS;
    }

    public ListTransactionDto(String message,long id) {
        super(message, id);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}