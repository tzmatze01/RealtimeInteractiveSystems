package main.network;

import main.handler.NetworkMessageHandler;
import main.manager.Manager;

import java.io.IOException;
import java.net.Socket;

public class ClientManager extends Manager {


    private Socket socket;
    boolean isAlive;

    public ClientManager(String address, int port) {
        try {
            this.socket = new Socket(address, port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.isAlive = true;
    }

    @Override
    public void register(NetworkMessageHandler nmh) {

    }

    @Override
    public void run() {

        while isAlive
    }
}
