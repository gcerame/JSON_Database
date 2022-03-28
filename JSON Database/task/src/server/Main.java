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

    public static void main(String[] args) {


        String address = "127.0.0.1";
        int port = 23456;
        System.out.println("Server started!");
        database = new Database();

        try (ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(address));
             Socket socket = server.accept();
             BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
            String inputLine;
            while ((inputLine = input.readLine()) != null) {
                String[] userRequest;
                System.out.println(inputLine);
                userRequest = inputLine.split(" ");
                Request request = new Request();
                JCommander.newBuilder()
                        .addObject(request)
                        .build()
                        .parse(userRequest);
                processType(request);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }


    public static void processType(Request request) {

        switch (request.getType()) {
            case "get":
                String record = database.getRecord(request);
                break;
            case "set":
                break;
            case "delete":
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + request.getType());
        }
    }
}
