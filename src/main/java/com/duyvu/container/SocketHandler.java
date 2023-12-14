/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.duyvu.container;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author duyvu
 */
public class SocketHandler extends Thread {

    private final Socket socket;
    private final Map<String, HttpServlet> urlMappings;

    public SocketHandler(Socket socket) {
        this.socket = socket;

        // TODO: Process the request
        this.urlMappings = new HashMap<>();
    }

    @Override
    public void run() {
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            // Reading msg from the input stream
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = in.readLine();

            while (!line.isBlank()) {
//                System.out.println(line);
                line = in.readLine();
            }

            // Sending to the client
            out = new PrintWriter(socket.getOutputStream());
            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: text/html");
            out.println();
            out.println("<html><body>");
            out.println("Current time: " + LocalDateTime.now());
            out.println("</body></html>");

            out.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {

            // Closing the socket after handling the communication succesfully
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
