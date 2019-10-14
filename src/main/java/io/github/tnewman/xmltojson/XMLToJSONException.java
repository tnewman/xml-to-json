package io.github.tnewman.xmltojson;

public class XMLToJSONException extends RuntimeException {
    public XMLToJSONException(String message) {
        super(message);
    }

    public XMLToJSONException(Throwable cause) {
        super(cause);
    }
}
