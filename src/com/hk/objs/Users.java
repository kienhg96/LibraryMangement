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
public class Users {
    private String username;
    private String password;
    private String fullname;
    private Date birthday;
    private String address;
    private String phone;
    private Date expirationDate;

    public Users(String username, String password, String fullName, 
            Date birthday, String address, String phone, Date expirationDate) {
        this.username = username;
        this.password = Database.hashPassword(password);
        this.fullname = fullName;
        this.birthday = birthday;
        this.address = address;
        this.phone = phone;
        this.expirationDate = expirationDate;
    }

    public String getUsername() {
        return username;
    }

//    public void setUsername(String username) {
//        this.username = username;
//    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = Database.hashPassword(password);
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullName) {
        this.fullname = fullName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
    
    public boolean save() {
        return Database.saveUser(this);
    }
}