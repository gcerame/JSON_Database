package server.database;

import server.commands.Request;

public class Database {

    private String[] database = new String[1000];

    public String getRecord(Request request) {
        String record = null;
        int index = request.getIndex();
        if (index < 0 || index > 1000) {
            return "ERROR";
        }

        record = database[index];
        if (record == null || record.equals("")) {
            record = "ERROR";
        }
        return record;


    }

    public String setRecord(Request request) {
        int index = request.getIndex();
        String string = request.getValue();

        database[index] = string;
        return "OK";

    }

    public String deleteRecord(Request request) {
        int index = request.getIndex();
        if (index < 0 || index > 1000) return "Error";

        database[index] = "";
        return "OK";

    }

}
