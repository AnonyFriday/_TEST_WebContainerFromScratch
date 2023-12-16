/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.duyvu.container;

/**
 * A mimic version of the HttpServlet from the Servlet architecture
 *
 * @author duyvu
 */
public abstract class HttpServlet {

    // =================================
    // == HttpServlet Lifecycle
    // =================================
    public void init() {
        System.out.println("HttpServlet Init Default Impl...");
    }

    public void service(HttpRequest request, HttpResponse response) {
        HttpMethod method = request.getMethod();
        if (HttpMethod.GET.equals(method)) {
            doGet(request, response);
        } else if (HttpMethod.POST.equals(method)) {
            doPost(request, response);
        } else {
            throw new RuntimeException("Method not Supported!");
        }
    }

    public void destroy() {

    }

    // =================================
    // == Http Methods
    // =================================
    public void doGet(HttpRequest request, HttpResponse response) {
        System.out.println("doGet() method in Default Impl ...");
    }

    public void doPost(HttpRequest request, HttpResponse response) {
        System.out.println("doGet() method in Default Impl ...");
    }
}
