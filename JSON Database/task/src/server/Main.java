package server;

import com.google.gson.Gson;
import server.commands.Request;
import server.commands.Response;
import server.database.Database;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class Main {

    public static void main(String[] args) {

        final String ADDRESS = "127.0.0.1";
        final int PORT = 23456;
        Database database = new Database();

        try (ServerSocket server = new ServerSocket(PORT, 50, InetAddress.getByName(ADDRESS))) {
            System.out.println("Server started!");

            while (true) {
                try (Socket socket = server.accept();
                     DataInputStream input = new DataInputStream(socket.getInputStream());
                     DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {

                    String requestString = input.readUTF();

                    if (requestString.isEmpty() || requestString.isBlank()) {
                        continue;
                    }

                    Gson gson = new Gson();
                    Request request = gson.fromJson(requestString, Request.class);
                    Response response = new Response();
                    String JSONResponse;

                    switch (request.getType()) {
                        case "exit":
                            response.setResponse("OK");
                            JSONResponse = gson.toJson(response);
                            output.writeUTF(JSONResponse);
                            System.exit(1);
                            break;
                        case "get":
                            response = database.getRecord(request.getKey());
                            JSONResponse = gson.toJson(response);
                            output.writeUTF(JSONResponse);
                            break;
                        case "set":
                            response.setResponse(database.setRecord(request));
                            JSONResponse = gson.toJson(response);
                            output.writeUTF(JSONResponse);
                            break;
                        case "delete":
                            JSONResponse = gson.toJson(database.deleteRecord(request));
                            output.writeUTF(JSONResponse);
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + request.getType());
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

