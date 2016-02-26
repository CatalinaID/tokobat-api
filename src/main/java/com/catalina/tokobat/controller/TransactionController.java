package com.catalina.tokobat.controller;

import com.catalina.tokobat.common.Constants;
import com.catalina.tokobat.dao.ApotekDao;
import com.catalina.tokobat.dao.TransactionDao;
import com.catalina.tokobat.dao.UserDao;
import com.catalina.tokobat.dto.*;
import com.catalina.tokobat.entity.Transaction;

import javax.imageio.ImageIO;
import javax.inject.Inject;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openstack4j.api.OSClient;
import org.openstack4j.model.common.Identifier;
import org.openstack4j.model.common.Payloads;
import org.openstack4j.model.storage.object.SwiftAccount;
import org.openstack4j.openstack.OSFactory;
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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import org.openstack4j.model.common.DLPayload;

/**
 *
 * @author icha
 */
@Controller
@RequestMapping(value="/transactions")
public class TransactionController {

    private static Logger log = LoggerFactory.getLogger(TransactionController.class);

    @Inject
    private TransactionDao transDAO;

    @Inject
    private UserDao userDao;

    @Inject
    private ApotekDao apotekDao;

    @RequestMapping(method = RequestMethod.PUT, value = "/{transId}")
    public ResponseEntity<ResponseDto> setOrderStatus(
            @PathVariable long transId,
            @RequestParam String status,
            @RequestParam(required=false) String apotekNotes,
            @RequestParam(required=false) BigDecimal totalPrice) {
        
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
        if (status.equals(Transaction.STATUS_ACCEPTED)){
            if ((totalPrice == null) || (totalPrice.compareTo(BigDecimal.ZERO) <= 0)) {
                String msg = "Total price parameter not valid";
                return new ResponseEntity<>(
                        new ResponseDto(msg, Constants.ERROR_INDEX),
                        HttpStatus.BAD_REQUEST
                );
            }
            trans.setTotal(totalPrice);
        }
        trans.setStatus(status);
        transDAO.updateTransaction(trans);
        log.info("Update transaction with id = " + transId);
        return new ResponseEntity<>(
                new ResponseDto(Constants.DEFAULT_SUCCESS, 
                        Constants.SUCCESS_INDEX), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete/{transId}")
    public ResponseEntity<ResponseDto> deleteOrder(
            @PathVariable long transId) {

        Transaction trans = transDAO.getTransactionById(transId);
        if (trans == null) {
            String msg = "Transaction id = " + transId + " not found";
            log.error(msg);
            return new ResponseEntity<>(
                    new ResponseDto(msg, Constants.ERROR_INDEX),
                    HttpStatus.NOT_FOUND
            );
        }
        log.info("Transaction status = " + trans.getStatus() );
        if (!trans.getStatus().equals(Transaction.STATUS_WAITING)) {
            String msg = "Order has been "+trans.getStatus();
            log.error(msg);
            return new ResponseEntity<>(
                    new ResponseDto(msg, Constants.ERROR_INDEX),
                    HttpStatus.BAD_REQUEST
            );
        }

        transDAO.deleteTransaction(trans.getId());
        log.info("Delete transaction with id = " + transId);
        return new ResponseEntity<>(
                new ResponseDto(Constants.DEFAULT_SUCCESS,
                        Constants.SUCCESS_INDEX), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/order")
    public @ResponseBody
    TransactionDto registerName(@RequestParam(value = "name") String name,
                         @RequestParam(value = "userId") long userId,
                         @RequestParam(value = "apotekId") long apotekId,
                         @RequestParam(required=false,value = "imgPath")  String imgPath,
                         @RequestParam(required=false,value = "catatan")  String catatan,
                         @RequestParam(required=false,value = "deskripsi") String deskripsi,

                         Model model) {
        try {
            log.info("order user  " + userId + " name = " + name);
            Transaction transaction = new Transaction();
            transaction.setName(name);
            transaction.setImgPath(imgPath);
            transaction.setUser(userDao.getUserById(userId));
            transaction.setApotek(apotekDao.getApotekById(apotekId));
            if (catatan!=null)
                transaction.setNotes(catatan);
            if (deskripsi!=null)
                transaction.setDescription(deskripsi);

            transaction = transDAO.add(transaction);
            if (transaction!=null) {
                TransactionDto transDto = new TransactionDto( Constants.DEFAULT_SUCCESS, transaction);
                return transDto;
            }
        } catch (Exception e) {
            TransactionDto transDto = new TransactionDto(e.getMessage(),Constants.ERROR_INDEX);
        }
        TransactionDto transDto = new TransactionDto(Constants.DEFAULT_FAIL,Constants.ERROR_INDEX);
        return  transDto;
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

    @RequestMapping(method = RequestMethod.GET, value = "/listByUser")
    public @ResponseBody
    List<Transaction> listOrderByUser(
            @RequestParam(value = "userId") long userId, Model model) {

        log.info("get order detail userId  " + userId );

        List<Transaction> list = new ArrayList<>();

        try {
            list = transDAO.listTransactionsByUser(userId);
            for (Transaction trans: list) {
                if (checkPayment(trans)) {
                    trans.setStatus(Transaction.STATUS_PAID);
                }
            }
        } catch (Exception e) {

        }
        return list;
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    ListTransactionApotekDto getAllOrdersForApotek(
            @RequestParam long apotekId) {

        List<Transaction> transList = 
                transDAO.listTransactionsByApotek(apotekId);
        ListTransactionApotekDto res = new ListTransactionApotekDto(transList);
        return res;
    }

    @RequestMapping(method = RequestMethod.GET, value="/{orderId}/payment")
    public ResponseEntity<PaymentDto> payment (
            @PathVariable long orderId,
            @RequestParam String returnUrl) {
        PaymentDto res = new PaymentDto();
        Transaction trans = transDAO.getTransactionById(orderId);
        if (trans == null) {
            res.setMessage("Transaction id = " + orderId +" not found");
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }
        if (!trans.getStatus().equals(Transaction.STATUS_ACCEPTED)) {
            res.setMessage("Payment not allowed to this transaction");
            return new ResponseEntity<PaymentDto>(res, HttpStatus.BAD_REQUEST);
        }
        Map<String, String> params = new HashMap<>();
        params.put("amount", "" + trans.getTotal());
        String traceNumber = "" + (new Date()).getTime();
        params.put("tracenumber", traceNumber);
        params.put("returnurl", returnUrl);

        String respon = (new RestTemplate()).getForObject(Constants.TICKET_URI
                + "?amount={amount}&tracenumber={tracenumber}&returnurl={returnurl}",
                String.class, params);
        ObjectMapper mapper = new ObjectMapper();
        TicketDto ticket;
        try {
            ticket = mapper.readValue(respon, TicketDto.class);
            trans.setTraceNumber(traceNumber);
            trans.setTicket(ticket.ticketID);
            transDAO.updateTransaction(trans);
            res.setUrlToPay(Constants.IPG_URI + "/payment.html?id=" + ticket.ticketID);
            res.setId(trans.getId());
        } catch (Exception e) {
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
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

    private boolean checkPayment(Transaction trans) {
        boolean res = false;
        if ((trans.getStatus().equals(Transaction.STATUS_ACCEPTED)) && (trans.getTicket() != null)) {
            Map<String, String> params = new HashMap<>();
            params.put("id", trans.getTicket());
            try {
                String respon = (new RestTemplate()).getForObject(Constants.IPG_URI
                        + "/validation.html?id={id}", String.class, params);
                String[] items = respon.trim().split(",");
                if ((trans.getTraceNumber().equals(items[3])) && (items[4].equals("SUCCESS"))) {
                    trans.setStatus(Transaction.STATUS_PAID);
                    res = true;
                } else if ((trans.getTraceNumber().equals(items[3])) && (items[4].equals("FAILED"))) {
                    trans.setTicket(null);
                    trans.setTraceNumber(null);
                }
                transDAO.updateTransaction(trans);
            } catch (Exception ex) {
                return false;
            }
        }
        return res;
    }

    private File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException
    {
        File convFile = new File( multipart.getOriginalFilename());
        multipart.transferTo(convFile);
        return convFile;
    }

    @RequestMapping(value = "/resep-upload", headers = "content-type=multipart/*", method = RequestMethod.POST)
    @ResponseBody
    public UploadResponseDto uploadProductImg(@RequestParam MultipartFile file)
    {
        String etag ="";
        try {
            BufferedImage image = ImageIO.read(file.getInputStream());
            String name = "resep-" + new Timestamp(System.currentTimeMillis());;
            File outputFile = new File(name+".png");
            ImageIO.write(image, "png", outputFile);

            String envServices = System.getenv("VCAP_SERVICES");

            JSONParser parser = new JSONParser();
            //Object obj = parser.parse(new FileReader("src/main/webapp/tokobat-api_VCAP_Services.json"));

            //JSONObject jsonObject = (JSONObject) obj;

            Object obj = parser.parse(envServices);
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray vcapArray = (JSONArray) jsonObject.get("Object-Storage");
            JSONObject vcap = (JSONObject) vcapArray.get(0);
            JSONObject credentials = (JSONObject) vcap.get("credentials");
            String userId = credentials.get("userId").toString();


            log.info("VCAP userId  " + userId );

            String password = credentials.get("password").toString();
            String auth_url = credentials.get("auth_url").toString() + "/v3";
            String domain = credentials.get("domainName").toString();
            String project = credentials.get("project").toString();
            Identifier domainIdent = Identifier.byName(domain);
            Identifier projectIdent = Identifier.byName(project);

            OSClient os = OSFactory.builderV3()
                    .endpoint(auth_url)
                    .credentials(userId, password)
                    .scopeToProject(projectIdent, domainIdent)
                    .authenticate();

            SwiftAccount account = os.objectStorage().account().get();
            etag = os.objectStorage().objects().put(Constants.CONTAINER_IMG, name, Payloads.create(multipartToFile(file)));

            return new UploadResponseDto(Constants.DEFAULT_SUCCESS, Constants.SUCCESS_INDEX,name);
        } catch(Exception ex) {

            return new UploadResponseDto(etag, Constants.ERROR_INDEX);

        }

        //return new UploadResponseDto(Constants.ERROR_MESSAGE, Constants.ERROR_INDEX);
    }
    
    @RequestMapping(value = "/get-resep", headers = "Accept=image/jpeg, image/jpg,"
            + " image/png, image/gif", method = RequestMethod.GET)
    public @ResponseBody BufferedImage getImage(@RequestParam String imgPath){
        try {
            String envServices = System.getenv("VCAP_SERVICES");

            JSONParser parser = new JSONParser();
            Object obj = parser.parse(envServices);
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray vcapArray = (JSONArray) jsonObject.get("Object-Storage");
            JSONObject vcap = (JSONObject) vcapArray.get(0);
            JSONObject credentials = (JSONObject) vcap.get("credentials");
            String userId = credentials.get("userId").toString();

            String password = credentials.get("password").toString();
            String auth_url = credentials.get("auth_url").toString() + "/v3";
            String domain = credentials.get("domainName").toString();
            String project = credentials.get("project").toString();
            Identifier domainIdent = Identifier.byName(domain);
            Identifier projectIdent = Identifier.byName(project);

            OSClient os = OSFactory.builderV3()
                    .endpoint(auth_url)
                    .credentials(userId, password)
                    .scopeToProject(projectIdent, domainIdent)
                    .authenticate();

            SwiftAccount account = os.objectStorage().account().get();
            DLPayload pl = os.objectStorage().objects().download(Constants.CONTAINER_IMG, imgPath);
            return ImageIO.read(pl.getInputStream());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
