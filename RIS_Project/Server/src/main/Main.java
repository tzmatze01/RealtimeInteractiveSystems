package main;

import main.handler.KeyEventHandler;
import main.handler.LoginHandler;
import main.handler.LogoutHandler;
import main.handler.MovementHandler;
import main.messages.KeyEventMessage;
import main.messages.LoginMessage;
import main.messages.LogoutMessage;
import main.messages.MovementMessage;
import main.server.ServerManager;

public class Main {


    public static void main(String [] args) {

        // vars for server
        String[] allowedUserNames = {"hans", "peter", "test"};
        int[][] gamePlan = {{10,0,0}, {15,3,0}, {20,5,1}, {25,5,3}};
        

        ServerManager manager = new ServerManager(9090);

        LoginHandler<LoginMessage> hLogin = new LoginHandler<>(allowedUserNames);
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

        Thread mainThread = new Thread(manager);

        mainThread.start();
    }
}
