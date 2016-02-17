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

    @Column(name = "username", nullable = true, unique = true)
    private String username;

    @Column(name = "name", nullable = true)
    private String name;

    @Column(name = "hash")
    private String hash;

    @Column(name = "salt")
    private String salt;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Calendar getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Calendar dateCreate) {
        this.dateCreate = dateCreate;
    }
}
