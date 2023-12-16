/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.duyvu.container;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author duyvu
 */
public class SimpleWebContainer {

    // =================================
    // == Fields
    // =================================
    private final int port;
    private final String configFileName;
    private final Map<String, HttpServlet> urlMapping;

    // =================================
    // == Constructors
    // =================================
    public SimpleWebContainer(int port, String configFileName) {
        this.port = port;
        this.configFileName = configFileName;
        this.urlMapping = new HashMap<>();
    }

    // =================================
    // == Methods
    // =================================

    /**
     * Starting a server
     */
    public void start() {

        try {
            // Start a server using TCP protocol
            ServerSocket serverSocket = new ServerSocket(port);

            while (!serverSocket.isClosed()) {
                // Accept the socket from the client
                Socket socket = serverSocket.accept();
                new SocketHandler(socket, urlMapping).start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Load the config.propeties file as the url mapping to the servlet
     * In the Web Dynamic, it equals to web.xml
     * https://viettuts.vn/vi-du-java-io/read-properties-file-trong-java
     */
    public void readPropertiesFile() {

        // Get the inputstream to load the config file into the program
        // Just like checking web.xml
        InputStream input = getClass().getClassLoader().getResourceAsStream(configFileName);

        // Checking if found the config file
        if (input == null) {
            throw new RuntimeException("Unable to find file: " + configFileName);
        } else {
            // Reading a config file if reading successfully
            Properties properties = new Properties();
            try {
                // Load config to application
                properties.load(input);

                // Finding the servlet instance based on servlet's name and mapping with the endpoint
                properties.forEach((url, servletName) -> {
                    HttpServlet servlet = getServletInstance((String) servletName);

                    // If the servlet not null, then init()
                    if (servlet != null) {
                        servlet.init();
                    }

                    // Attach the URL with servlet class and added to the map
                    urlMapping.put((String) url, servlet);
                });

            } catch (IOException ex) {
                ex.getStackTrace();
            }
        }
    }

    /**
     * Get the servlet instance by passing a class name mapping with config url file
     *
     * @param className: a servlet name passed from the config.properties
     * @return an instance of HttpServlet
     */
    private HttpServlet getServletInstance(String className) {

        // Using the reflection api to reflect the class and get the instance of it
        try {
            return (HttpServlet) Class.forName(className).getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException e) {
            e.getStackTrace();
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    // Entry point to start a web container
    public static void main(String[] args) {
        SimpleWebContainer container = new SimpleWebContainer(8080, "config.properties");

        // Read url mappings from properties file
        container.readPropertiesFile();

        // [Testing] Url the and the servlet instance
        container.urlMapping.forEach((url, servletInstance) -> {
            System.out.println("URL: " + url);
            System.out.println("Servlet: " + servletInstance.getClass());
        });

        // Start the container
        container.start();
    }
}
