package main;

import main.handler.LoginHandler;
import main.handler.LogoutHandler;
import main.client.ClientManager;
import main.handler.MovementHandler;
import main.messages.LoginMessage;
import main.messages.LogoutMessage;
import main.messages.MovementMessage;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Main extends JFrame {


    public Main() {

        initUI();
    }

    private void initUI() {

        // TODO level == N meteoriotes on screen
        // gameplan consist of lenght -> num level, rows (N) -> meteorites, collectables, enemies

        /*
        int[][] gamePlan = {{10,0,0}, {15,3,0}, {20,5,1}, {25,5,3}};

        World world = new World(screenWidth, screenHeight, gamePlan);
        world.setFocusable(true);
        world.addKeyListener(world);

        add(world);
        */

        ClientManager clientManager = new ClientManager("localhost", 9090);

        LoginHandler<LoginMessage> hLogin = new LoginHandler<>();
        LogoutHandler<LogoutMessage> hLogout = new LogoutHandler<>();
        MovementHandler<MovementMessage> hMovement = new MovementHandler();

        Thread tLogin = new Thread(hLogin);
        Thread tLogout = new Thread(hLogout);
        Thread tMovement = new Thread(hMovement);

        tLogin.start();
        tLogout.start();
        tMovement.start();

        clientManager.registerMessageHandler(hLogin);
        clientManager.registerMessageHandler(hLogout);
        clientManager.registerMessageHandler(hMovement);

        clientManager.addKeyListener(clientManager);
        clientManager.setFocusable(true);


        add(clientManager);

        setSize(800, 600);

        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        Thread clientThread = new Thread(clientManager);
        clientThread.start();
    }

    public static void main(String [] args) throws IOException {

        EventQueue.invokeLater(() -> {
            Main ex = new Main();
            ex.setVisible(true);
        });
    }
}
