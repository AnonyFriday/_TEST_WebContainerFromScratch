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
import java.util.Map;

/**
 * @author duyvu
 */
public class SocketHandler extends Thread {

    private final Socket socket;
    private final Map<String, HttpServlet> urlMapping;

    public SocketHandler(Socket socket, Map<String, HttpServlet> urlMapping) {
        this.socket = socket;
        this.urlMapping = urlMapping;
    }

    @Override
    public void run() {
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            // Reading msg from the input stream
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            HttpRequest httpRequest = new HttpRequest(in);

            // If cannot parse to the Http Request, then show internal error
            if (!httpRequest.parseHttpRequest()) {
                out = new PrintWriter(socket.getOutputStream(), true);
                out.println("HTTP/1.1 500 Internal Server Error");
                out.println("Content-Type: text/html");
                out.println("");
                out.println("<html><body>");
                out.println("<h1>Cannot process your request. Please try again.</h1>");
                out.println("</html></body>");
            } else {
                // If parsing succesful but the servlet is not found
                // which means the path is not found => 404
                if (urlMapping.get(httpRequest.getPath()) == null) {
                    out = new PrintWriter(socket.getOutputStream(), true);
                    out.println("HTTP/1.1 404 Not Found");
                    out.println("Content-Type: text/html");
                    out.println("");
                    out.println("<html><body>");
                    out.println("<h1>Servlet not found. Please try again</h1>");
                    out.println("</html></body>");
                } else {
                    // If parsing sucessful and the servlet supporting route is availabel
                    out = new PrintWriter(socket.getOutputStream(), true);
                    out.println("HTTP/1.1 200 OK");
                    out.println("Content-Type: text/html");
                    out.println();
                    out.println("<html><body>");
                    out.println("Current time: " + LocalDateTime.now());
                    out.println("</body></html>");
                }
            }

            System.out.println("==========================");

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            // Closing the socket after handling the communication succesfully
            try {
//                in.close();
//                out.close();
                socket.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
