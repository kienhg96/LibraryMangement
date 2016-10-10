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
    private Books book;
    private Date expirationDate;

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
    private ReturnBooks returnBook;

    public BorrowDetails() {
        this.borrowDetailId = -1;
        this.book = null;
        this.returnBook = null;
    }

    public ReturnBooks getReturnBook() {
        return returnBook;
    }

    public void setReturnBook(ReturnBooks returnBook) {
        this.returnBook = returnBook;
    }

    public int getBorrowDetailId() {
        return borrowDetailId;
    }

    public void setBorrowDetailId(int borrowDetailId) {
        this.borrowDetailId = borrowDetailId;
    }

    public Books getBook() {
        return book;
    }

    public void setBook(Books book) {
        this.book = book;
    }

    public boolean save(Borrows parentBorrows) {
        boolean result = true;
        if (Database.saveBorrowDetail(parentBorrows.getBorrowId(), this)) {
            if (this.returnBook != null) {
                result = this.returnBook.save(this);
            }
        } else {
            result = false;
        }
        return result;
    }
}
