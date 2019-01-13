package main.server;

import main.handler.NetworkMessageHandler;
import main.handler.SocketHandler;
import main.manager.Manager;
import main.messages.type.MessageType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

// netzwerk listener

public class ServerManager implements Manager, ActionListener {

    Map<MessageType, NetworkMessageHandler> listeners;
    boolean isAlive;

    private ServerSocket serverSocket;
    private Socket socket;
    private int port;

    private Timer timer;
    private final int DELAY = 10;

    public ServerManager(int port) {

        this.listeners = new HashMap<>();
        this.isAlive = true;

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


        //ObjectInputStream ois = null;


        System.out.print("Waiting for mssgs...");

        while(isAlive)
        {
            try {
                socket = serverSocket.accept();
            }
            catch (IOException e){
                e.printStackTrace();
            }

            Thread socketThread = new Thread(new SocketHandler(socket, listeners));
            socketThread.start();

            /*
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
            */
        }
    }

    @Override
    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    @Override
    public boolean getAlive() {
        return this.isAlive;
    }

    @Override
    public void registerMessageHandler(NetworkMessageHandler nmh) {
        if(!listeners.containsKey(nmh.getHandledMessageType()))
        {
            listeners.put(nmh.getHandledMessageType(), nmh);
        }
        else
        {
            // TODO Error messages handler already exists
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO step
    }
}
