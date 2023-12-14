package com.duyvu.controller;


import com.duyvu.container.HttpServlet;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * A concrete class that extends the HttpServlet as the Servlet
 *
 * @author duyvu
 */
public class HelloWorldServlet extends HttpServlet {

    @Override
    public void init() {
        System.out.println("init() from Hello World");
    }

    @Override
    public void doGet() {
        System.out.println("doGet() from HelloWorld");
    }

    @Override
    public void doPost() {
        super.doPost(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
}
