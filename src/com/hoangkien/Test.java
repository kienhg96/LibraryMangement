/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hoangkien;

public class Test {

//    void f1(short x) {
//        System.out.println("short");
//    }

    void f1(int x) {
        System.out.println("int");
    }

    void f1(long x) {
        System.out.println("long");
    }

    void f1(float x) {
        System.out.println("float");
    }

    public static void main(String[] args) {
        new Test().f1((short)214);
    }
}
