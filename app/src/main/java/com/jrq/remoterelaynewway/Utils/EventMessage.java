package com.jrq.remoterelaynewway.Utils;

/**
 * Created by jrq on 2016-09-13.
 */
// POJO for EventBus
public class EventMessage {
    private final String message;
    private final String status;

    public EventMessage(String status, String message) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }
}
