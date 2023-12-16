package com.duyvu.container;


import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A mimic version of HttpRequest capturing the request details sent from clients
 */

enum HttpMethod {
    GET, POST, PUSH, DELETE
}

@Getter
@Setter
public class HttpRequest {

    private HttpMethod method = null;      // A HTTP protocol method
    private final BufferedReader in;             // InputStream buffer from client
    private String path = null;            // e.g. /hello?user=123 -> path is /hello
    private final Map<String, String> requestHeaders;   // header's content, split by :
    private final Map<String, String> requestParameters; // e.g. user=123, pass=123

    public HttpRequest(BufferedReader in) {
        this.in = in;
        requestHeaders = new HashMap<>();
        requestParameters = new HashMap<>();
    }

    /**
     * Parse the query string to the hash map
     * e.g. name=123&pass=123 -> {name: 123, pass: 123}
     *
     * @param queryString: a query string extracted from the url
     */
    private void parseRequestParameters(String queryString) {
        // e.g. name=123&pass=123
        String[] parameters = queryString.split("&");
        Arrays.stream(parameters).forEach(param -> {
            String[] paramArr = param.split("=");
            requestParameters.put(paramArr[0], paramArr[1]);
        });
    }

    /**
     * Parse each pair of header into the header map
     *
     * @param headerLine: A pair of header
     */
    private void parseRequestHeaderLine(String headerLine) {
        // e.g. Host: localhost:8080
        String[] pairArr = headerLine.split(": ");
        requestHeaders.put(pairArr[0], pairArr[1]);
    }

    /**
     * A function to parse http request to the object HttpRequest
     *
     * @return true if success, else return false
     */
    public boolean parseHttpRequest() {
        try {
            // GET /hello?user=123 HTTP/1.1
            // GET /hello HTTP/1.1
            String line = in.readLine();

            // Extracting path and params
            // GET HTTP/1.1 => WRONG
            String[] firstLineArray = line.split(" ");
            if (firstLineArray.length != 3) return false;

            // GET, POST
            method = HttpMethod.valueOf(firstLineArray[0]);

            // Read query string, path
            // e.g. /hello?user=123 => good
            // e.g. /hello?user => not good
            String url = firstLineArray[1];
            if (!url.isBlank()) {

                // Extract the path and the queryString
                int queryStringIndex = url.indexOf("?");
                if (queryStringIndex > -1 && HttpMethod.GET.equals(method)) {

                    // e.g. /hello?user=123, for GET method
                    String queryString = url.substring(queryStringIndex + 1);
                    parseRequestParameters(queryString);
                    path = url.substring(0, queryStringIndex);
                } else {
                    // e.g. /hello, for POST, GET method
                    path = url;
                }
            }

            // Read header lines
            while (!line.isBlank()) {
                line = in.readLine();
                if (!"".equals(line)) {
                    parseRequestHeaderLine(line);
                }
            }

            // If POST request, then having extra body

            // POST /test HTTP/1.1
            // Host: foo.example
            // Content-Type: application/x-www-form-urlencoded
            // Content-Length: 27
            // [blank space]
            // field1=value1&field2=value2  <= read this line

            if (HttpMethod.POST.equals(method)) {
                String queryString = in.readLine();
                parseRequestParameters(queryString);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return true;
    }
}
