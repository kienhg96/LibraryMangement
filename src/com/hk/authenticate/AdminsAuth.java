/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hk.authenticate;
import com.hk.objs.Admins;
import com.hk.database.Database;

/**
 *
 * @author hoangkien
 */
public class AdminsAuth {
    private static Admins admin = null;
    public static boolean login(String username, String password){
        admin = Database.checkAdminLogin(username, Database.hashPassword(password));
        if (admin == null) {
            return false;
        }
        else {
            return true;
        }
    }
    public static void logout() {
        admin = null;
    }
    public static Admins getAdmin(){
        return admin;
    }
}
