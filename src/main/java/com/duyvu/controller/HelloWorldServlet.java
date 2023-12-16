package com.duyvu.controller;


import com.duyvu.container.HttpRequest;
import com.duyvu.container.HttpResponse;
import com.duyvu.container.HttpServlet;

import java.io.PrintWriter;

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
    public void doGet(HttpRequest request, HttpResponse response) {
        PrintWriter out = response.getPrintWriter();
        out.println("HTTP/1.1 200 OK");
        out.println("Content-Type: text/html");
        out.println();
        out.println("<html><body>");
        out.println("<p>doGet() in HelloServlet</p>");
        out.println("</body></html>");
        out.flush();
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {

    }
}
