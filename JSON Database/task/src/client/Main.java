package client;

import com.beust.jcommander.JCommander;
import com.google.gson.Gson;
import server.commands.Request;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    static final String CLIENT_STARTED = "Client started!";

    public static void main(String[] args) {
        Gson gson = new Gson();
        Scanner sc = new Scanner(System.in);
        String address = "127.0.0.1";
        int port = 23456;

        try (
                Socket socket = new Socket(InetAddress.getByName(address), port);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream())
        ) {
            System.out.println(CLIENT_STARTED);
            Request request = new Request();
            JCommander.newBuilder()
                    .addObject(request)
                    .build()
                    .parse(args);
            request.valuesListToString();
            if (!request.getType().equals("set")) {
                request.nullValue();
            }

            String JSONRequest = gson.toJson(request);
            System.out.println("Sent: " + JSONRequest);

            output.writeUTF(JSONRequest);
            String inputFromServer = input.readUTF();
            if (!inputFromServer.isEmpty()) {
                System.out.println("Received: " + inputFromServer);

            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }


}
