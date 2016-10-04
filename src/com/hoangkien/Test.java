/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hoangkien;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            list.add(new Integer(i));
        }
        Collections.shuffle(list);
        for (int i = 0; i < 3; i++) {
            System.out.println(list.get(i));
        }
    }
}
