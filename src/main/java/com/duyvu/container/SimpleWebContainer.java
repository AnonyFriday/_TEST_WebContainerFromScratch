/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.duyvu.container;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.InputStream;
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

    // =================================
    // == Constructors
    // =================================
    public SimpleWebContainer(int port, String configFileName) {
        this.port = port;
        this.configFileName = configFileName;
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
                Thread socketThreadHanlder = new SocketHandler(socket);
                socketThreadHanlder.start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Load the config.propeties file as the url mapping to the servlet
     * https://viettuts.vn/vi-du-java-io/read-properties-file-trong-java
     */
    public void readPropertiesFile() {

        //Get the inputstream to load the config file into the program
        InputStream input = getClass()
                .getClassLoader()
                .getResourceAsStream(configFileName);

        // Checking if found the config file
        if (input == null) {
            throw new RuntimeException("Unable to find file: "
                    + configFileName);
        } else {
            // Reading a config file if reading successfully
            Properties properties = new Properties();
            try {
                properties.load(input);
            } catch (IOException ex) {
                ex.getStackTrace();
            }
        }
    }

    // Entry point to start a web container
    public static void main(String[] args) {
        SimpleWebContainer container
                = new SimpleWebContainer(8080, ""
                        + "config.properties");

        // Read url mappings from properties file
        container.readPropertiesFile();

        // Start the container
        container.start();
    }
}
