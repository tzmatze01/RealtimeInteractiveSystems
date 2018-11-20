package main;

import main.handler.LoginHandler;
import main.handler.LogoutHandler;
import main.handler.MovementHandler;
import main.handler.NetworkMessageHandler;
import main.messages.LoginMessage;
import main.messages.LogoutMessage;
import main.messages.Message;
import main.messages.MovementMessage;
import main.messages.type.MessageType;
import main.server.ServerManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {


    public static void main(String [] args) {

        ServerManager manager = new ServerManager(9090);

        LoginHandler<LoginMessage> hLogin = new LoginHandler<>();
        LogoutHandler<LogoutMessage> hLogout = new LogoutHandler<>();
        MovementHandler<MovementMessage> hMovement = new MovementHandler();


        Thread tLogin = new Thread(hLogin);
        Thread tLogout = new Thread(hLogout);
        Thread tMovement = new Thread(hMovement);

        tLogin.start();
        tLogout.start();
        tMovement.start();

        manager.registerMessageHandler(hLogin);
        manager.registerMessageHandler(hLogout);
        manager.registerMessageHandler(hMovement);

        Thread mainThread = new Thread(manager);

        mainThread.start();

        /*
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(9090);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(true) {
            System.out.print("Waiting for mssgs...");

            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // read the incoming messages
            ObjectInputStream ois = null;
            try {

                ois = new ObjectInputStream(socket.getInputStream());



                Message message = ((Message) ois.readObject());

                System.out.println("mssg: "+message.getType());


            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        */

        // unterscheiden was für messages
        // richtigen Hanler aus pool suchen
        // handler thread starten
        // ---> ??Queue vorlesung
    }
}
