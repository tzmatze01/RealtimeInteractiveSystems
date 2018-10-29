package main.threads;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class HandlerThread implements Runnable {


    private Socket clientSocket;
    private ObjectInputStream ois;

    public HandlerThread(Socket socket) {
        this.clientSocket = socket;
        try {
            this.ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

    }
}
