package com.catalina.tokobat.dto;

import com.catalina.tokobat.entity.Transaction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author ichakid
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
                    trans.getDateCreate(),
                    trans.getStatus()
            );
            transactions.add(summary);
        }
        total = transactions.size();
    }
    
    public class TransactionSummary implements Serializable {
        public long transId;
        public String senderName;
        public boolean read;
        public Calendar dateCreate;
        public String status;
        
        public TransactionSummary(long transId, String senderName,
                boolean read, Calendar dateCreate, String status) {
            this.transId = transId;
            this.senderName = senderName;
            this.read = read;
            this.dateCreate = dateCreate;
            this.status = status;
        }
    }
}
