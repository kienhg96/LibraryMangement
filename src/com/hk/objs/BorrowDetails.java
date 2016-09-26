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
public class BorrowDetails {
    private int borrowDetailId;
    private int borrowId;
    private int bookId;
    private boolean isReturn;
    private Date returnDate;

    public int getBorrowDetailId() {
        return borrowDetailId;
    }

    public void setBorrowDetailId(int borrowDetailId) {
        this.borrowDetailId = borrowDetailId;
    }

    public int getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(int borrowId) {
        this.borrowId = borrowId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public boolean isIsReturn() {
        return isReturn;
    }

    public void setIsReturn(boolean isReturn) {
        this.isReturn = isReturn;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public BorrowDetails(int borrowId, int bookId, boolean isReturn, Date returnDate) {
        this.borrowId = borrowId;
        this.bookId = bookId;
        this.isReturn = isReturn;
        this.returnDate = returnDate;
    }
    
    public boolean save() {
        return Database.saveBorrowDetail(this);
    }
}
