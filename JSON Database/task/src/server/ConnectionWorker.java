package server;

import com.google.gson.Gson;
import server.commands.Request;
import server.commands.Response;
import server.database.Database;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionWorker implements Runnable {
    private final DataOutputStream outputStream;
    private final DataInputStream inputStream;
    private final Socket clientSocket;
    private final ServerSocket serverSocket;
    private Database database;

    public ConnectionWorker(Socket clientSocket, ServerSocket serverSocket, Database database) throws IOException {
        this.outputStream = new DataOutputStream(clientSocket.getOutputStream());
        this.inputStream = new DataInputStream(clientSocket.getInputStream());
        this.clientSocket = clientSocket;
        this.serverSocket = serverSocket;
        this.database = database;
    }

    @Override
    public void run() {

        String clientMessage = null;
        try {
            clientMessage = inputStream.readUTF().trim();
        } catch (IOException e) {
        }

        if (clientMessage.isEmpty() || clientMessage.isBlank()) {
            return;
        }

        Gson gson = new Gson();
        Request request = gson.fromJson(clientMessage, Request.class);
        Response response = new Response();
        String JSONResponse;

        try {
            switch (request.getType()) {
                case "exit":
                    response.setResponse("OK");
                    JSONResponse = gson.toJson(response);
                    outputStream.writeUTF(JSONResponse);
                    serverSocket.close();
                    clientSocket.close();
                    System.exit(1);
                    break;
                case "get":
                    response = database.getRecord(request);
                    JSONResponse = gson.toJson(response);
                    outputStream.writeUTF(JSONResponse);
                    break;
                case "set":
                    response = database.setRecord(request);
                    JSONResponse = gson.toJson(response);
                    outputStream.writeUTF(JSONResponse);
                    break;
                case "delete":
                    response = database.deleteRecord(request);
                    JSONResponse = gson.toJson(response);
                    outputStream.writeUTF(JSONResponse);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + request.getType());
            }
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }
}
