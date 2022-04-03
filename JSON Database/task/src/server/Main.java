package server;

import com.google.gson.Gson;
import server.commands.Request;
import server.commands.Response;
import server.database.Database;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


//Todo: get rid of magic strings
public class Main {
    private static ServerSocket serverSocket;
    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 23456;

    public static void main(String[] args) {


        Database database = new Database();

        createServerSocket();
        System.out.println("Server started!");
        createClientSocket(database);
        try {
            serverSocket.close();
        } catch (Exception ignored) {
        }

    }

    private static void createClientSocket(Database database) {
        try {
            while (!serverSocket.isClosed()) {
                final Socket clientSocket = serverSocket.accept();
                if (clientSocket != null) {
                    new Thread(new ConnectionWorker(clientSocket, serverSocket, database)).start();
                }
            }

        } catch (IOException ignored) {
        }
    }

    private static void createServerSocket() {
        while (true) {
            try {
                serverSocket = new ServerSocket(PORT, 50, InetAddress.getByName(ADDRESS));
                return;
            } catch (IOException e) {
            }
        }
    }
}

