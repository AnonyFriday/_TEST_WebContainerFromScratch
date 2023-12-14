package com.duyvu.controller;

import com.duyvu.container.HttpServlet;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * A concrete class that extends the HttpServlet
 *
 * @author duyvu
 */
public class SignupServlet extends HttpServlet {

    @Override
    public void init() {
        System.out.println("init() called from signup");
    }

    @Override
    public void doGet() {
        System.out.println("From Signup doGet");
    }

    @Override
    public void doPost() {
        super.doPost(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
}
