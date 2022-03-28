package client;




import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner (System.in);

        String address = "127.0.0.1";
        int port = 23456;
        try(
                Socket socket = new Socket(InetAddress.getByName(address), port);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream())
        ){
            String userInput = sc.nextLine();

            output.writeUTF(userInput);


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }



    }


}
