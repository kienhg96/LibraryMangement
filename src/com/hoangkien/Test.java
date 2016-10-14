/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hoangkien;

import com.hk.authenticate.AdminsAuth;
import com.hk.database.Database;
import com.hk.objs.Users;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.text.DateFormatSymbols ;

/**
 *
 * @author hoangkien
 */

public class Test{
    private int x;
   public void show(){
       System.out.println(x);
   }
    public static void main(String[] args) {
       new Test().show();
    }
}
