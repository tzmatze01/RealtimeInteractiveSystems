package main.server;

import main.handler.NetworkMessageHandler;
import main.messages.Message;
import main.messages.type.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SocketHandler implements Runnable {

    protected Socket socket;
    protected Map<MessageType, NetworkMessageHandler> listeners;


    private boolean isAlive;

    public SocketHandler(Socket socket, Map<MessageType, NetworkMessageHandler> listeners) {
        this.socket = socket;
        this.listeners = listeners;

        this.isAlive = true;

    }

    public void run()
    {

        ObjectInputStream ois = null;

        //ObjectInputStream ois = null;
        try
        {
            ois = new ObjectInputStream(socket.getInputStream());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


        System.out.println("created new inputstream: "+socket.toString());

        while(isAlive)
        {
            try
            {
                Message message = ((Message)ois.readObject());


                NetworkMessageHandler nmh = listeners.get(message.getType());
                nmh.addMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            // TODO get response from nmh and return
        }
    }


    public boolean getAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }
}
