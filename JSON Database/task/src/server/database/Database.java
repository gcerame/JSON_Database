package server.database;

import server.commands.Request;

public class Database {

    private String[] database = new String[1000];

    public String getRecord(Request request) {
        String record = null;
        int index = request.getIndex();
        try {
            record = database[index - 1];
            if (record==null || record.equals("")) {
                record = "ERROR";
            }
            return record;

        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid index requested");
        }
        return record;
    }

    public void setRecord(Request request){
        int index = request.getIndex();
        String string = request.getValue();

        database[index]=string;

    }

    public void deleteRecord(Request request){
        int index = request.getIndex();

        database[index]="";

    }

}
