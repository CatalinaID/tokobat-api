package com.catalina.tokobat.dto;

import com.catalina.tokobat.entity.Transaction;
import java.io.Serializable;
import java.util.ArrayList;
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
                    trans.isReadBy()
            );
            transactions.add(summary);
        }
        total = transactions.size();
    }
    
    public class TransactionSummary implements Serializable {
        public long transId;
        public String senderName;
        public boolean read;
        
        public TransactionSummary(long transId, String senderName,
                boolean read) {
            this.transId = transId;
            this.senderName = senderName;
            this.read = read;
        }
    }
}
