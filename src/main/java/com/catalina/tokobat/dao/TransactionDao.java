package com.catalina.tokobat.dao;

import com.catalina.tokobat.entity.Transaction;

import java.util.List;

/**
 *
 * @author icha
 */
public interface TransactionDao {
    Transaction getTransactionById(long id);
    Transaction updateTransaction(Transaction trans);
    List<Transaction> listTransactionsByApotek(long apotekId);
}