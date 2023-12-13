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

/**
 *
 * @author duyvu
 */
public class SimpleWebContainer {

    private final int port;

    public SimpleWebContainer(int port) {
        this.port = port;
    }

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

    // Entry point to start a web container
    public static void main(String[] args) {
        SimpleWebContainer container = new SimpleWebContainer(8080);
        container.start();
    }
}
