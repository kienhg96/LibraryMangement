/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hk.objs;

import com.hk.database.Database;

/**
 *
 * @author hoangkien
 */
public class Books {
    private int bookId;
    private String bookName;
    private String author;
    private String publishCom;
    private int categoryId;
    private String shelf;
    private int price;

    public Books(String bookName, String author, String publishCom, int categoryId, String shelf, int price) {
        this.bookName = bookName;
        this.author = author;
        this.publishCom = publishCom;
        this.categoryId = categoryId;
        this.shelf = shelf;
        this.price = price;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishCom() {
        return publishCom;
    }

    public void setPublishCom(String publishCom) {
        this.publishCom = publishCom;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    
    public boolean save() {
        return Database.saveBook(this);
    }
}
