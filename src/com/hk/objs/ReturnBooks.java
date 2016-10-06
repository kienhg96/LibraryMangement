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
public class ReturnBooks {
    private Date returnDate;
    private int penalty;

    public ReturnBooks() {
        this.returnDate = null;
        this.penalty = 0;
    }
    
    public ReturnBooks(Date returnDate, int penalty) {
        this.returnDate = returnDate;
        this.penalty = penalty;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public int getPenalty() {
        return penalty;
    }

    public void setPenalty(int penalty) {
        this.penalty = penalty;
    }
    
    public boolean save(BorrowDetails detail){
        return Database.saveReturnBook(detail.getBorrowDetailId(), this);
    } 
}
