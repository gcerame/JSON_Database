package server;

import com.beust.jcommander.JCommander;
import server.commands.Request;
import server.database.Database;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;


public class Main {
    private static Database database;

    public static void main(String[] args) {


        String address = "127.0.0.1";
        int port = 23456;
        System.out.println("Server started!");
        database = new Database();

        while (true) {
            try (ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(address));
                 Socket socket = server.accept();
                 DataInputStream input = new DataInputStream(socket.getInputStream());
                 DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {

                while (true) {
                    String requestString = input.readUTF();
                    if (requestString.isEmpty() || requestString.isBlank()) {
                        continue;
                    }

                    System.out.println(requestString);
                    String[] requestArray = requestString.split(" ");
                    Request request = new Request();
                    JCommander.newBuilder()
                            .addObject(request)
                            .build()
                            .parse(requestArray);
                    switch (request.getType()) {
                        case "get":
                            String record = database.getRecord(request);
                            output.writeUTF(record);
                            break;
                        case "set":
                            database.setRecord(request);
                            break;
                        case "delete":
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + request.getType());
                    }
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }


    }


    public static void processType(Request request) {



    }
}
