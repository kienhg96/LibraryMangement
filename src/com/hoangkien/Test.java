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
public class Test {

//    public static void main(String[] args) {
//        JTextField xField = new JTextField(5);
//        JTextField yField = new JTextField(5);
//        //xField.setPreferredSize(new Dimension(100, 100));
//        JPanel myPanel = new JPanel();
//        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.PAGE_AXIS));
//
//        JPanel p1 = new JPanel();
//        JPanel p2 = new JPanel();
//        p1.add(new JLabel("x:"));
//        p1.add(xField);
//        p2.add(new JLabel("y:"));
//        p2.add(yField);
//        
//        myPanel.add(p1);
//        myPanel.add(p2);
//        
//        int result = JOptionPane.showConfirmDialog(null, myPanel,
//                "Please Enter X and Y Values", JOptionPane.OK_CANCEL_OPTION);
//        System.out.println("Done");
//        if (result == JOptionPane.OK_OPTION) {
//            System.out.println("x value: " + xField.getText());
//            System.out.println("y value: " + yField.getText());
//        }
//    }
//    public static void main(String[] args) {
//        String patternString = ".*abc.*";
//        Pattern pattern = Pattern.compile(patternString);
//        String str = "12abac8";
//        System.out.println(pattern.matcher(str).matches());
//    }
 
    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month - 1];
    }

    public static void main(String[] args) {
        System.out.println(new Test().getMonth(1));
    }
}
