package server.database;

import com.google.gson.Gson;
import server.commands.Request;
import server.commands.Response;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Database {
    static final String ERROR = "ERROR";
    static final String NO_SUCH_KEY = "No such key";
    static final String OK = "OK";
    static final String PATH = "/Users/ceramesupersound/IntelliJ Projects/JSON Database/JSON Database/task/src/server/data/db.json";
    ReadWriteLock lock;
    Lock readLock, writeLock;


    public Database() {
        lock = new ReentrantReadWriteLock();
        readLock = lock.readLock();
        writeLock = lock.writeLock();
        init();
    }

    private void init() {
        try {
            writeLock.lock();
            FileWriter writer = new FileWriter(PATH);
            writer.write("{}");
            writer.close();

            writeLock.unlock();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Response getRecord(Request request) {
        Response response = new Response();
        String value;

        try {
            readLock.lock();

            FileReader reader = new FileReader(PATH);
            Map<String, String> database = new Gson().fromJson(reader, Map.class);
            reader.close();
            response.setValue(database.get(request.getKey()));

            readLock.unlock();

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (response.getValue() == null) {
            response.setResponse(ERROR);
            response.setReason(NO_SUCH_KEY);
        } else {
            response.setResponse(OK);
        }
        return response;

    }

    public Response setRecord(Request request) {
        Response response = new Response();

        try {
            writeLock.lock();

            FileReader reader = new FileReader(PATH);
            Map<String, String> database = new Gson().fromJson(reader, Map.class);
            reader.close();

            database.put(request.getKey(), request.getValue());
            FileWriter writer = new FileWriter(PATH);
            writer.write(new Gson().toJson(database));
            writer.close();
            writeLock.unlock();

            response.setResponse(OK);
            response.setReason(null);


        } catch (Exception ignored) {

        }


        return response;

    }

    public Response deleteRecord(Request request) {
        Response response = new Response();

        try {
            writeLock.lock();
            FileReader reader = new FileReader(PATH);
            Map<String, String> database = new Gson().fromJson(reader, Map.class);
            reader.close();
            writeLock.unlock();

            if (database.containsKey(request.getKey())) {
                database.remove(request.getKey());
                writeLock.lock();
                FileWriter writer = new FileWriter(PATH);
                writer.write(new Gson().toJson(database));
                writer.close();
                writeLock.unlock();
                response.setResponse(OK);

            } else {
                response.setResponse(ERROR);
                response.setReason(NO_SUCH_KEY);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

}
