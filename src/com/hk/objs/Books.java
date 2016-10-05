/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hk.objs;

import com.hk.database.Database;
import java.util.ArrayList;
import com.hk.objs.Categories;

/**
 *
 * @author hoangkien
 */
public class Books {

    private int bookId;
    private String bookName;
    private String author;
    private String publishCom;
    private Categories category;
    private String shelf;
    private int price;
    private int publishYear;

    public int getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }

    public Books() {
        this.bookId = -1; // -1 by Default, set for new book
        this.bookName = null;
        this.author = null;
        this.publishCom = null;
        this.category = null;
        this.shelf = null;
        this.price = 0;
        this.publishYear = 0;
    }

    public Books(String bookName, String author, String publishCom,
            Categories category, String shelf, int price, int publishYear) {
        this.bookId = -1; // -1 by Default, set for new book
        this.bookName = bookName;
        this.author = author;
        this.publishCom = publishCom;
        this.category = category;
        this.shelf = shelf;
        this.price = price;
        this.publishYear = publishYear;
    }

    public int getBookId() {
        return bookId;
    }

    public boolean setBookId(int bookId) {
        if (this.bookId == -1) {
            this.bookId = bookId;
            return true;
        } else {
            return false;
        }
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

    public Categories getCategory() {
        return this.category;
    }

    public void setCategory(Categories category) {
        this.category = category;
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

    public boolean remove() {
        return Database.removeBook(this);
    }

    public static ArrayList<Books> findBookByName(String name) {
        return Database.findBookByName(name);
    }

    public static ArrayList<Books> findBookByAuthor(String author) {
        return Database.findBookByAuthor(author);
    }

    public static ArrayList<Books> findBookByCategory(String category) {
        return Database.findBookByCategory(category);
    }

    public static ArrayList<Books> getAllBooks() {
        return Database.getAllBooks();
    }
}
