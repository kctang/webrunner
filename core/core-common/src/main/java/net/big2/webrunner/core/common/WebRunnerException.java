package net.big2.webrunner.core.common;

public class WebRunnerException extends Exception {
    public WebRunnerException(String message) {
        super(message);
    }

    public WebRunnerException(String message, Throwable cause) {
        super(message, cause);
    }
}
