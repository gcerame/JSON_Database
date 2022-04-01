package server.database;

import server.commands.Request;
import server.commands.Response;

import java.util.HashMap;
import java.util.Map;

public class Database {
    static final String ERROR = "Error";
    static final String NO_SUCH_KEY = "No such key";
    static final String OK = "OK";

    private Map<String, String> database = new HashMap<>();

    public Response getRecord(String key) {
        Response response = new Response();
        String value = database.get(key);
        if (value == null) {
            response.setResponse(ERROR);
            response.setReason(NO_SUCH_KEY);
        } else {
            response.setResponse(OK);
            response.setValue(value);
        }
        return response;

    }

    public Response setRecord(Request request) {
        Response response = new Response();
        database.put(request.getKey(), request.getValue());

        response.setResponse(OK);
        response.setValue(null);
        response.setReason(null);

        return response;

    }

    public Response deleteRecord(Request request) {
        Response response = new Response();
        String deletedRecord = database.remove(request.getKey());

        if (deletedRecord == null) {
            response.setResponse(ERROR);
            response.setReason(NO_SUCH_KEY);
        } else {
            response.setResponse(OK);
        }
        return response;
    }

}
