package net.cyrillicsoftware.xmltopubmedconverter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
public class WrongFormatException extends RuntimeException{

    private String receivedType;
    private String expectedType;

    public WrongFormatException(String message, String receivedType, String expectedType) {
        super("Error: File is in " + receivedType + " format but it should be in " + expectedType + " format");
        this.receivedType = receivedType;
        this.expectedType = expectedType;
    }

    public String getReceivedType() {
        return receivedType;
    }

    public void setReceivedType(String receivedType) {
        this.receivedType = receivedType;
    }

    public String getExpectedType() {
        return expectedType;
    }

    public void setExpectedType(String expectedType) {
        this.expectedType = expectedType;
    }
}
