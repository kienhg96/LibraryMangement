/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hk.authenticate;
import com.hk.objs.Users;
import com.hk.database.Database;

/**
 *
 * @author hoangkien
 */
public class UsersAuth {
    private static Users user = null;
    public static boolean login(String username, String password){
        user = Database.checkUserLogin(username, Database.hashPassword(password));
        if (user == null) {
            return false;
        }
        else {
            return true;
        }
    }
    public static void logout() {
        user = null;
    }
    public static Users getUser() {
        return user;
    }
}
