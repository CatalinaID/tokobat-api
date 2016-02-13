package com.catalina.tokobat.controller;

import com.catalina.tokobat.common.Constants;
import com.catalina.tokobat.dao.TransactionDao;
import com.catalina.tokobat.dto.ListTransactionApotekDto;
import com.catalina.tokobat.dto.ResponseDto;
import com.catalina.tokobat.dto.TransactionDto;
import com.catalina.tokobat.entity.Transaction;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author icha
 */
@Controller
@RequestMapping(value="/transactions")
public class TransactionController {

    private static Logger log = LoggerFactory.getLogger(UserController.class);

    @Inject
    private TransactionDao transDAO;

    @RequestMapping(method = RequestMethod.PUT, value = "/{transId}")
    public ResponseEntity<ResponseDto> setOrderStatus(
            @PathVariable long transId,
            @RequestParam String status,
            @RequestParam(required=false) String apotekNotes) {
        
        Transaction trans = transDAO.getTransactionById(transId);
        if (trans == null) {
            String msg = "Transaction id = " + transId + " not found";
            log.error(msg);
            return new ResponseEntity<>(
                    new ResponseDto(msg, Constants.ERROR_INDEX),
                    HttpStatus.NOT_FOUND
            );
        }
        if (!checkStatus(status, trans)) {
            String msg = "Status parameter not valid";
            log.error(msg);
            return new ResponseEntity<>(
                    new ResponseDto(msg, Constants.ERROR_INDEX),
                    HttpStatus.BAD_REQUEST
            );
        }
        if (status.equals(Transaction.STATUS_ACCEPTED)
                || status.equals(Transaction.STATUS_DECLINED)) {
            trans.setApotekNotes(apotekNotes);
        }
        trans.setStatus(status);
        transDAO.updateTransaction(trans);
        log.info("Update transaction with id = " + transId);
        return new ResponseEntity<>(
                new ResponseDto(Constants.DEFAULT_SUCCESS, 
                        Constants.SUCCESS_INDEX), HttpStatus.OK);
    }
    
    private boolean checkStatus(String status, Transaction trans) {
        switch(status) {
            case Transaction.STATUS_DECLINED:
                if (!trans.getStatus().equals(Transaction.STATUS_WAITING)) {
                    return false;
                }
                break;
            case Transaction.STATUS_ACCEPTED:
                if (!trans.getStatus().equals(Transaction.STATUS_WAITING)) {
                    return false;
                }
                break;
            case Transaction.STATUS_READY: 
                if (!trans.getStatus().equals(Transaction.STATUS_ACCEPTED)) {
                    return false;
                }
                break;
            case Transaction.STATUS_FINISHED: 
                if (!trans.getStatus().equals(Transaction.STATUS_READY)) {
                    return false;
                }
                break;
            default:
                return false;
        }
        return true;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/detail")
    public @ResponseBody
    TransactionDto getOrderDetail(
            @RequestParam(value = "id") long transId, Model model) {

        log.info("get order detail transId  " + transId );

        try {
            Transaction trans = transDAO.getTransactionById(transId);
            trans.setReadBy(true);
            transDAO.updateTransaction(trans);
            TransactionDto transactionDto = new TransactionDto(Constants.DEFAULT_SUCCESS,trans);
            return transactionDto;
        } catch (Exception e) {

        }
        TransactionDto transactionDto = new TransactionDto(Constants.DEFAULT_FAIL,Constants.ERROR_INDEX);
        return  transactionDto;
    }
/*
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    ListTransactionApotekDto getAllOrdersForApotek(
            @RequestParam long apotekId) {
        
        List<Transaction> transList = 
                transDAO.listTransactionsByApotek(apotekId);
        ListTransactionApotekDto res = new ListTransactionApotekDto(transList);
        return res;
    } */
}
