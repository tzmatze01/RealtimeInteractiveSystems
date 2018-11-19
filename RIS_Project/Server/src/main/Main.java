package main;

import main.handler.LoginHandler;
import main.handler.LogoutHandler;
import main.handler.MovementHandler;
import main.messages.LoginMessage;
import main.messages.LogoutMessage;
import main.messages.MovementMessage;
import main.messages.type.MessageType;
import main.server.ServerManager;

public class Main {


    public static void main(String [] args) {


        ServerManager manager = new ServerManager(9090);

        LoginHandler<LoginMessage> hLogin = new LoginHandler<>();
        LogoutHandler<LogoutMessage> hLogout = new LogoutHandler<>();
        MovementHandler<MovementMessage> hMovement = new MovementHandler();

        Thread tLogin = new Thread(hLogin);
        Thread tLogout = new Thread(hLogin);
        Thread tMovement = new Thread(hLogin);

        tLogin.start();
        tLogout.start();
        tMovement.start();

        manager.register(hLogin);
        manager.register(hLogout);
        manager.register(hMovement);

        manager.run();

        //MessageType type = MessageType.LOGIN;

    }
}
