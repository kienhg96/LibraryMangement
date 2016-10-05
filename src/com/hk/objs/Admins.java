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
public class Admins {
    private String username;
    private String password;
    private String fullname;
    private String phone;

    public Admins(String username) {
        this.username = username;
        this.password = null;
        this.fullname = null;
        this.phone = null;
    }
    
    public Admins(String username, String password, String fullname, String phone) {
        this.username = username;
        this.password = Database.hashPassword(password);
        this.fullname = fullname;
        this.phone = phone;
    }

    public void setRawPassword(String raw) {
        this.password = raw;
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

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public boolean save() {
        return Database.saveAdmin(this);
    }
}
