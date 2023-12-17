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
    public void doGet(HttpRequest request, HttpResponse response) {
        PrintWriter out = response.getPrintWriter();
        out.println("<html><body>");
        out.println("<form method=\"POST\">");
        out.println("Username: <input type=\"text\" id=\"user\" name=\"user\" value=\"Tina\"><br>");
        out.println("Password: <input type=\"password\" id=\"password\" name=\"password\" value=\"Xing\"><br><br>");
        out.println("<input type=\"submit\" value=\"Submit\">");
        out.println("</body></html>");
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        PrintWriter out = response.getPrintWriter();
        out.println("<html><body>");
        out.println("<p>Username: " + request.getParamValue("user") + "</p>");
        out.println("<p>Password: " + request.getParamValue("password") + "</p>");
        out.println("</body></html>");
    }

    @Override
    public void destroy() {
        System.out.println("destroy() from signup servlet.");
    }
}
