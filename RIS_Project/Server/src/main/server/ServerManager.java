package main.server;

import main.handler.NetworkMessageHandler;
import main.manager.Manager;
import main.messages.LoginMessage;
import main.messages.Message;
import main.messages.type.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

// netzwerk listener

public class ServerManager extends Manager {

    private ServerSocket serverSocket;
    private Socket socket;
    private int port;

    public ServerManager(int port) {
        this.port = port;
        this.listeners = new HashMap<>();
    }

    @Override
    public void run() {

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }


        ObjectInputStream ois = null;


        while(isAlive)
        {
            System.out.print("Waiting for mssgs...");


            try {
                socket = serverSocket.accept();
            }
            catch (IOException e){
                e.printStackTrace();
            }


            // read the incoming messages
            //ObjectInputStream ois = null;
            try
            {
                ois = new ObjectInputStream(socket.getInputStream());
                Message message = ((Message)ois.readObject());

                NetworkMessageHandler nmh = listeners.get(message.getType());
                nmh.addMessage(message);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            catch (ClassNotFoundException e)
            {
                e.printStackTrace();
            }
        }
    }
}
