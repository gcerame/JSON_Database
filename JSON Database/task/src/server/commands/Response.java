package server.commands;

import com.google.gson.Gson;

public class Response {

    private String response;

    private String reason;

    public String getResponse() {
        return response;
    }

    public String getReason() {
        return reason;
    }

    public String getValue() {
        return value;
    }

    private String value = null;

    public void setResponse(String response) {
        this.response = response;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setValue(String value) {
        this.value = value;
    }


}
