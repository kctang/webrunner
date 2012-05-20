package net.big2.webrunner.core.web;

public class CrudSupportControllerException extends Exception {
    public CrudSupportControllerException(String message) {
        super(message);
    }

    public CrudSupportControllerException(String message, Throwable cause) {
        super(message, cause);
    }
}
