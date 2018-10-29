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

    // hier werden sockets angenommen

    // hier sind threads drin
    private Map<MessageType, NetworkMessageHandler> listeners;

    private int port;
    private boolean isAlive;

    private ServerSocket serverSocket;
    private Socket socket;


    public ServerManager(int port) {
        this.port = port;
        this.isAlive = true;
        this.listeners = new HashMap<>();
    }

    @Override
    public void run() {

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(isAlive)
        {
            try {
                socket = serverSocket.accept();
            }
            catch (IOException e){
                e.printStackTrace();
            }

            // read the incoming messages
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(socket.getInputStream());
                Message message = ((Message)ois.readObject());

                NetworkMessageHandler nmh = listeners.get(message.getType());
                nmh.handle(message);
                nmh.run();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


            // unterscheiden was für messages
            // richtigen Hanler aus pool suchen
            // handler thread starten
            // ---> ??Queue vorlesung
        }
    }

    public void register(NetworkMessageHandler nmh)
    {
        if(!listeners.containsKey(nmh.getHandledMessageType())) {
            listeners.put(nmh.getHandledMessageType(), nmh);
        }
        else
        {
            // TODO Error messages
        }
    }
}
