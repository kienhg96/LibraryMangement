/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hk.objs;

import com.hk.database.Database;
import java.util.Date;

/**
 *
 * @author hoangkien
 */
public class Borrows {
    private int borrowId;
    private Date borrowDate;
    private String borrowUser;
    private Date expirationDate;

    public int getBorrowId() {
        return borrowId;
    }

    public Borrows(Date borrowDate, String borrowUser, Date expirationDate) {
        this.borrowDate = borrowDate;
        this.borrowUser = borrowUser;
        this.expirationDate = expirationDate;
    }

    public void setBorrowId(int borrowId) {
        this.borrowId = borrowId;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public String getBorrowUser() {
        return borrowUser;
    }

    public void setBorrowUser(String borrowUser) {
        this.borrowUser = borrowUser;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
    
    public boolean save() {
        return Database.saveBorrow(this);
    }
    
}
