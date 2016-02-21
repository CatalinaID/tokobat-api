package com.catalina.tokobat.dto;

import com.catalina.tokobat.entity.Transaction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author icha
 */
public class ListTransactionApotekDto implements Serializable {
    public List<TransactionSummary> transactions;
    public int total;
    
    public ListTransactionApotekDto(List<Transaction> transList) {
        transactions = new ArrayList<>();
        for (Transaction trans: transList) {
            TransactionSummary summary = new TransactionSummary(
                    trans.getId(),
                    trans.getUser().getName(),
                    trans.isReadBy(),
                    trans.getDateCreate()
            );
            transactions.add(summary);
        }
        total = transactions.size();
    }
    
    public class TransactionSummary implements Serializable {
        public long transId;
        public String senderName;
        public boolean read;
        public String dateCreate;
        
        public TransactionSummary(long transId, String senderName,
                boolean read, Calendar dateCreate) {
            this.transId = transId;
            this.senderName = senderName;
            this.read = read;
            this.dateCreate = dateCreate.;
        }
    }
}
