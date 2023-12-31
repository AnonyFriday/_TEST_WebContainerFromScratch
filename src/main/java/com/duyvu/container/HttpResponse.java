package com.duyvu.container;

import java.io.OutputStream;
import java.io.PrintWriter;

import lombok.AccessLevel;
import lombok.Getter;

/**
 * A minic version fo the HttpResponse which encapsulate - Response with Character Stream (Output
 * Stream) - Response with Byte Stream (Print Writer)
 */
@Getter(AccessLevel.PUBLIC)
public class HttpResponse {
    private OutputStream out;
    private PrintWriter printWriter;

    public HttpResponse(OutputStream out) {
        this.out = out;
        this.printWriter = new PrintWriter(out);
    }
}
