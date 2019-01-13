package main;

import main.handler.KeyEventHandler;
import main.handler.LoginHandler;
import main.handler.LogoutHandler;
import main.handler.MovementHandler;
import main.messages.KeyEventMessage;
import main.messages.LoginMessage;
import main.messages.LogoutMessage;
import main.messages.MovementMessage;
import main.server.Server;

import java.util.ArrayList;
import java.util.List;

public class Main {


    public static void main(String [] args) {

        // vars for server


        /*
        LoginHandler<LoginMessage> hLogin = new LoginHandler<>(allowedUserNames, loggedInUsers);
        LogoutHandler<LogoutMessage> hLogout = new LogoutHandler<>();
        MovementHandler<MovementMessage> hMovement = new MovementHandler();
        KeyEventHandler<KeyEventMessage> hKeyEvent = new KeyEventHandler<>();

        Thread tLogin = new Thread(hLogin);
        Thread tLogout = new Thread(hLogout);
        Thread tMovement = new Thread(hMovement);
        Thread tKeyEvent = new Thread(hKeyEvent);

        tLogin.start();
        tLogout.start();
        tMovement.start();
        tKeyEvent.start();


        manager.registerMessageHandler(hLogin);
        manager.registerMessageHandler(hLogout);
        manager.registerMessageHandler(hMovement);
        manager.registerMessageHandler(hKeyEvent);
         */
        Server manager = new Server(9090);

        Thread mainThread = new Thread(manager);

        mainThread.start();
    }
}
