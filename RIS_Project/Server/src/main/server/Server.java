package main.server;

import main.game.World;
import main.handler.*;
import main.handler.cookie.ConnectionCookie;
import main.manager.Manager;
import main.messages.KeyEventMessage;
import main.messages.LoginMessage;
import main.messages.LogoutMessage;
import main.messages.MovementMessage;
import main.messages.type.MessageType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// netzwerk listener

public class Server implements Runnable, ActionListener {

    private int port;
    private ServerSocket serverSocket;

    private boolean isAlive;
    private List<SocketManager> sockets;

    private List<String> loggedInUsers;
    private String[] allowedUserNames;
    private int[][] gamePlan;

    private World world;

    private Timer timer;
    private final int DELAY = 10;


    public Server(int port) {

        this.port = port;

        this.isAlive = true;
        this.sockets = new ArrayList<>();

        this.allowedUserNames = new String[]{"hans", "peter", "test"};
        this.gamePlan = new int[][]{{10,0,0}, {15,3,0}, {20,5,1}, {25,5,3}};

        this.loggedInUsers = new ArrayList<>();

        this.world = new World();

        timer = new Timer(DELAY, this);
        timer.start();
        timer.start();
    }

    @Override
    public void run() {


        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.print("Waiting for players...");

        // wait for two players
        while(sockets.size() < 2)
        {
            Socket socket = null;

            try {
                socket = serverSocket.accept();
            }
            catch (IOException e){
                e.printStackTrace();
            }

            ConnectionCookie cc = new ConnectionCookie(false);

            SocketManager sm = new SocketManager(socket, cc);

            LoginHandler<LoginMessage> hLogin = new LoginHandler<>(allowedUserNames, cc);
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

            sm.registerMessageHandler(hLogin);
            sm.registerMessageHandler(hLogout);
            sm.registerMessageHandler(hMovement);
            sm.registerMessageHandler(hKeyEvent);

            Thread socketThread = new Thread(sm);
            socketThread.start();

            sockets.add(sm);
        }


    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO step
    }
}
