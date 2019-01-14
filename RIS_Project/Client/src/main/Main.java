package main;

import main.game.World;
import main.handler.*;
import main.client.ClientManager;
import main.messages.*;

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

        World world = new World(10);
        ClientManager clientManager = new ClientManager("localhost", 9090, world);

        LoginHandler<LoginMessage> hLogin = new LoginHandler<>();
        LogoutHandler<LogoutMessage> hLogout = new LogoutHandler<>();
        MODelHandler<MODelMessage> hMODeletion = new MODelHandler<>(world);
        MOMovHandler<MOMovMessage> hMOMovement = new MOMovHandler<>(world);
        MONewHandler<MONewMessage> hMONew = new MONewHandler<>(world);


        Thread tLogin = new Thread(hLogin);
        Thread tLogout = new Thread(hLogout);
        Thread tDeletion = new Thread(hMODeletion);
        Thread tMovement = new Thread(hMOMovement);
        Thread tNew = new Thread(hMONew);

        tLogin.start();
        tLogout.start();
        tDeletion.start();
        tMovement.start();
        tNew.start();

        clientManager.registerMessageHandler(hLogin);
        clientManager.registerMessageHandler(hLogout);
        clientManager.registerMessageHandler(hMODeletion);
        clientManager.registerMessageHandler(hMOMovement);
        clientManager.registerMessageHandler(hMONew);

        world.addKeyListener(clientManager);
        //clientManager.addKeyListener(clientManager);
        world.setFocusable(true);


        add(world);

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
