package main;

import main.server.Server;

public class Main {


    public static void main(String [] args) {

        // vars for server


        /*
        LoginHandler<LoginMessage> hLogin = new LoginHandler<>(allowedUserNames, loggedInUsers);
        LogoutHandler<LogoutMessage> hLogout = new LogoutHandler<>();
        MovementHandler<MOMovMessage> hMovement = new MovementHandler();
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
