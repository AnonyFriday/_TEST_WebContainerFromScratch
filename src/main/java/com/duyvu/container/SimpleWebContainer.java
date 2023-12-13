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
                // And close preventing the open socket loading by using try-with-resource

                try (Socket socket = serverSocket.accept()) {
                    // Reading msg from the input stream
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String line = in.readLine();

                    while (!line.isBlank()) {
                        System.out.println(line);
                        line = in.readLine();
                    }

                    // Sending to the client
                    PrintWriter out = new PrintWriter(socket.getOutputStream());
                    out.println("HTTP/1.1 200 OK");
                    out.println("Content-Type: text/html");
                    out.println();
                    out.println("<html><body>");
                    out.println("Current time: " + LocalDateTime.now());
                    out.println("</body></html>");

                    out.flush();
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(SimpleWebContainer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        SimpleWebContainer container = new SimpleWebContainer(8080);
        container.start();
    }
}
