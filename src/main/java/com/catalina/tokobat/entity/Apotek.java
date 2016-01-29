package com.catalina.tokobat.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;

/**
 * Created by Alifa on 1/29/2016.
 */
@Entity
@Table(name = "tk_apotek")
public class Apotek {
    @TableGenerator(table = "tbl_sequence", name = "user_id_table",
            allocationSize = 100, initialValue = 0, pkColumnName = "seq_name",
            valueColumnName = "seq_count", pkColumnValue = "seq_user")
    @GeneratedValue(strategy = GenerationType.TABLE, generator="user_id_table")
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "name", nullable = true)
    private String name;

    @Column(name = "hash")
    private String hash;

    @Column(name = "salt")
    private String salt;

    @Column(name = "address")
    private String address;

    @Column(name = "lat")
    private String lat;

    @Column(name = "lng")
    private String lng;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "date_create")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dateCreate;

    @PrePersist
    protected void onCreate() {
        dateCreate = Calendar.getInstance();
    }

    /* Constructor */
    public Apotek() {

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

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }


    public Calendar getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Calendar dateCreate) {
        this.dateCreate = dateCreate;
    }
}
