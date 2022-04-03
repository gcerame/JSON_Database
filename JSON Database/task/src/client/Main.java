package client;

import com.beust.jcommander.JCommander;
import com.google.gson.Gson;
import server.commands.Request;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    static final String CLIENT_STARTED = "Client started!";
    static final String ADDRESS = "127.0.0.1";
    static final int PORT = 23456;
    static final String REQUESTFILE_PATH = "/Users/ceramesupersound/IntelliJ Projects/JSON Database/JSON Database/task/src/client/data/";

    public static void main(String[] args) {
        Gson gson = new Gson();

        try  {
            Socket socket = new Socket(InetAddress.getByName(ADDRESS), PORT);
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            System.out.println(CLIENT_STARTED);
            Request request = new Request();

            JCommander.newBuilder()
                    .addObject(request)
                    .build()
                    .parse(args);
            request.valuesListToString();
            request.setKey();

            String JSONRequest = gson.toJson(request);
            if (request.getType()!=null && !request.getType().equals("set")) {
                request.nullValue();
                JSONRequest = gson.toJson(request);

            }
            if (request.getFileName()!=null){
                File file = new File(REQUESTFILE_PATH+request.getFileName());
                Scanner sc = new Scanner (file);
                JSONRequest=sc.nextLine();

            }
            System.out.println("Sent: " + JSONRequest);



            output.writeUTF(JSONRequest);
            String inputFromServer = input.readUTF();
            if (!inputFromServer.isEmpty()) {
                System.out.println("Received: " + inputFromServer);

            }
            socket.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }


}
