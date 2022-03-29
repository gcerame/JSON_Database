package server;

import com.beust.jcommander.JCommander;
import server.commands.Request;
import server.database.Database;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class Main {
    private static Database database;

    public static void main(String[] args) throws IOException {


        String address = "127.0.0.1";
        int port = 23456;
        database = new Database();
        try (ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(address))) {
            System.out.println("Server started!");
            boolean exit = false;

            while (true) {
                try (
                        Socket socket = server.accept();
                        DataInputStream input = new DataInputStream(socket.getInputStream());
                        DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {

                    String requestString = input.readUTF();
                    if (requestString.isEmpty() || requestString.isBlank()) {
                        continue;
                    }

//                    System.out.println("Received: " + requestString);
                    String[] requestArray = requestString.split(" ");
                    Request request = new Request();
                    JCommander.newBuilder()
                            .addObject(request)
                            .build()
                            .parse(requestArray);
                    switch (request.getType()) {
                        case "exit":
                            output.writeUTF("OK");
                            exit = true;
                            System.exit(1);
                            break;
                        case "get":
                            String record = database.getRecord(request);
                            output.writeUTF(record);
                            break;
                        case "set":
                            output.writeUTF(database.setRecord(request));
                            break;
                        case "delete":
                            output.writeUTF(database.deleteRecord(request));
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

