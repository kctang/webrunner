package net.big2.webrunner.core.jpa.crud.support;

public class CrudSupportException extends Exception {
    public CrudSupportException(String message, Throwable cause) {
        super(message, cause);
    }

    public CrudSupportException(String message) {
        super(message);
    }
}
