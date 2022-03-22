package net.cyrillicsoftware.xmltopubmedconverter.exception;

import org.springframework.http.HttpStatus;

public class BasicException extends RuntimeException {

    private HttpStatus status;
    private String message;

    public BasicException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public BasicException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
