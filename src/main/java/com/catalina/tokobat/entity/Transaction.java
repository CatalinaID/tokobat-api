package com.catalina.tokobat.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;

/**
 * Created by Alifa on 1/29/2016.
 */
@Entity
@Table(name = "tk_transaction")
public class Transaction {
    @TableGenerator(table = "tbl_sequence", name = "user_id_table",
            allocationSize = 100, initialValue = 0, pkColumnName = "seq_name",
            valueColumnName = "seq_count", pkColumnValue = "seq_user")
    @GeneratedValue(strategy = GenerationType.TABLE, generator="user_id_table")
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "name", nullable = true)
    private String name;

    @ManyToOne(cascade={CascadeType.MERGE})
    @JoinColumn(name="user_mssidn")
    private User user;

    @ManyToOne(cascade={CascadeType.MERGE})
    @JoinColumn(name="apotek_id")
    private Apotek apotek;

    @Column(name = "imgPath")
    private String imgPath;

    @Column(name = "description")
    private String description;

    @Column(name = "notes")
    private String notes;

    @Column(name = "total")
    BigDecimal total;

    @Column(name = "apotek_notes")
    String apotekNotes;

    @Column(name = "readBy")
    boolean readBy;

    @Column(name = "status")
    private String status;

    @Column(name = "date_create")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dateCreate;

    @PrePersist
    protected void onCreate() {
        dateCreate = Calendar.getInstance();
        status="waiting";
    }

    public Transaction() {

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
}
