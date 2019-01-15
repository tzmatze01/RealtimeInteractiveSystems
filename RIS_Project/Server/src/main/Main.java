package main;

import main.server.Server;

public class Main {


    public static void main(String [] args) {


        Server manager = new Server(9090);

        Thread mainThread = new Thread(manager);

        mainThread.start();
    }
}
