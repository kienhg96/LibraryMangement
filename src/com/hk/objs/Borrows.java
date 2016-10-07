/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hk.objs;

import com.hk.database.Database;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author hoangkien
 */
public class Borrows {

    private int borrowId;
    private Date borrowDate;
    private Users borrowUser;
    private Date expirationDate;
    private int deposit;
    private ArrayList<BorrowDetails> borrowDetailList;

    public Borrows() {
        this.borrowId = -1;
        this.borrowDate = null;
        this.borrowUser = null;
        this.expirationDate = null;
        this.deposit = 0;
        this.borrowDetailList = new ArrayList<>();
    }

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public ArrayList<BorrowDetails> getBorrowDetailList() {
        return borrowDetailList;
    }

    public void setBorrowDetailList(ArrayList<BorrowDetails> borrowDetailList) {
        this.borrowDetailList = borrowDetailList;
    }
    
    public boolean addBorrowDetail(BorrowDetails detail) {
        if (Database.checkBookAvailable(detail.getBook())) {
            this.borrowDetailList.add(detail);
            return true;
        }
        else {
            return false;
        }
    }

    public int getBorrowId() {
        return borrowId;
    }

    public Borrows(Date borrowDate, Users borrowUser, Date expirationDate,
            int deposit, ArrayList<BorrowDetails> borrowDetailList) {
        this.borrowDate = borrowDate;
        this.borrowUser = borrowUser;
        this.expirationDate = expirationDate;
        this.deposit = deposit;
        this.borrowDetailList = borrowDetailList;
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

    public Users getBorrowUser() {
        return borrowUser;
    }

    public void setBorrowUser(Users borrowUser) {
        this.borrowUser = borrowUser;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public boolean save() {
        if (Database.saveBorrow(this)) {
            for (int i = 0; i < this.borrowDetailList.size(); i++) {
                borrowDetailList.get(i).save(this);
            }
            return true;
        }
        else {
            return false;
        }
    }
    
    public static ArrayList<Borrows> getAllBorrowListByUser(Users user) {
        ArrayList<Borrows> list = null;
        list = Database.getAllBorrowListByUser(user);
        for (Borrows item: list) {
            item.setBorrowDetailList(Database.getAllBorrowDetailListByBorrow(item));
            for (BorrowDetails detail: item.getBorrowDetailList()) {
                detail.setReturnBook(Database.getReturnBookByBorrowDetail(detail));
            }
        }
        return list;
    }
}
