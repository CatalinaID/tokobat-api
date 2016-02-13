package com.catalina.tokobat.dto;

import com.catalina.tokobat.entity.Apotek;
import com.catalina.tokobat.entity.Transaction;
import com.catalina.tokobat.entity.User;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * Created by Alifa on 2/13/2016.
 */
public class TransactionDto extends ResponseDto{

    private long id;

    private String name;
    private User user;
    private Apotek apotek;
    private String imgPath;
    private String description;
    private String notes;
    BigDecimal total;
    String apotekNotes;
    boolean readBy;
    private String status;
    private Calendar dateCreate;

    public TransactionDto(String message,long id) {
        super(message, id);
    }

    public TransactionDto(String message,Transaction transaction) {
        super(message, transaction.getId());
        this.name = transaction.getName();
        this.user = transaction.getUser();
        this.apotek = transaction.getApotek();
        this.imgPath = transaction.getImgPath();
        this.notes = transaction.getNotes();
        this.total = transaction.getTotal();
        this.apotekNotes = transaction.getApotekNotes();
        this.readBy = transaction.isReadBy();
        this.status = transaction.getStatus();
        this.dateCreate = transaction.getDateCreate();

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Apotek getApotek() {
        return apotek;
    }

    public void setApotek(Apotek apotek) {
        this.apotek = apotek;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getApotekNotes() {
        return apotekNotes;
    }

    public void setApotekNotes(String apotekNotes) {
        this.apotekNotes = apotekNotes;
    }

    public boolean isReadBy() {
        return readBy;
    }

    public void setReadBy(boolean readBy) {
        this.readBy = readBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Calendar getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Calendar dateCreate) {
        this.dateCreate = dateCreate;
    }
}
