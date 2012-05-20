package net.big2.webrunner.core.web;

public class AjaxResponse {
    private String status;
    private String message;
    private Object data;

    public static final String STATUS_OK = "ok";
    public static final String STATUS_ERROR = "error";

    public AjaxResponse(Object data) {
        this(STATUS_OK, null, data);
    }

    public AjaxResponse(String status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
