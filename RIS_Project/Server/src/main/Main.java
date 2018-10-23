package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Main {


    public static void main(String [] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(9090);

        try {
            while (true) {
                Socket socket = serverSocket.accept();

                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                System.out.println("i hend bekomma: " + br.readLine());

                br.close();
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
