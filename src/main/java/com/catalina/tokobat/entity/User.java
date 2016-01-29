package com.catalina.tokobat.entity;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Created by Alifa on 3/11/2015.
 */
@Entity
@Table(name = "tk_user")
public class User {
    @TableGenerator(table = "tbl_sequence", name = "user_id_table",
            allocationSize = 100, initialValue = 0, pkColumnName = "seq_name",
            valueColumnName = "seq_count", pkColumnValue = "seq_user")
    @GeneratedValue(strategy = GenerationType.TABLE, generator="user_id_table")
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "msisdn", nullable = true, unique = true)
    private String msisdn;

    @Column(name = "UID", nullable = true)
    private String uid;

    @Column(name = "name", nullable = true)
    private String name;

    @Column(name = "session")
    private String session;

    @Column(name = "date_create")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dateCreate;

    @PrePersist
    protected void onCreate() {
        dateCreate = Calendar.getInstance();
    }

    /* Constructor */
    public User() {

    }

    public User(String msisdn, String name, String uid) {
        this.msisdn = msisdn;
        this.name = name;
        this.uid = uid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }


    public Calendar getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Calendar dateCreate) {
        this.dateCreate = dateCreate;
    }
}
