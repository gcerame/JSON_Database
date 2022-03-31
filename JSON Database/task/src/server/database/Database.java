package server.database;

import server.commands.Request;
import server.commands.Response;

import java.util.HashMap;
import java.util.Map;

public class Database {

    private Map<String, String> database = new HashMap<>();

    public Response getRecord(String key) {
        Response response = new Response();
        String value = database.get(key);
        if (value == null) {
            response.setResponse("ERROR");
            response.setReason("No such key");
        } else {
            response.setResponse("OK");
            response.setValue(value);
        }
        return response;

    }

    public String setRecord(Request request) {

        database.put(request.getKey(), request.getValue());

        return "OK";

    }

    public Response deleteRecord(Request request) {
        Response response = new Response();
        String deletedRecord = database.remove(request.getKey());

        if (deletedRecord == null) {
            response.setResponse("ERROR");
            response.setReason("No such key");
        } else {
            response.setResponse("OK");
        }
        return response;
    }

}
