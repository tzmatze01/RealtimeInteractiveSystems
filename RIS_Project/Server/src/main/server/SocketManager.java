package main.server;

import main.game.sprites.type.ObjectType;
import main.handler.NetworkMessageHandler;
import main.manager.Manager;
import main.messages.LoginMessage;
import main.messages.Message;
import main.messages.MovementMessage;
import main.messages.type.MessageType;
import main.network.ConnectionCookie;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class SocketManager implements Manager {

    protected Socket socket;
    protected Map<MessageType, NetworkMessageHandler> listeners;

    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    private boolean isAlive;
    //private boolean loggedIn;

    private ConnectionCookie cc;
    private String userName;

    public SocketManager(Socket socket, ConnectionCookie cc) {

        this.socket = socket;
        this.listeners = new HashMap<>();
        //this.loggedIn = false;

        this.cc = cc;
    }

    @Override
    public void run()
    {
        try
        {
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        // expect login message from user
        Message message = readFromOIS();
        if(message.getType() == MessageType.LOGIN) {
            listeners.get(message.getType()).addMessage(message);
            this.userName = ((LoginMessage)message).getUserName();
        }
        else {
            System.out.println("expected login message, got: " + message.getType());
            return;
        }

        // wait till user is authenticated
        System.out.println("authenticating new user...");
        while(!cc.isUserLoggedIn(userName)) { }

        System.out.println("user: "+userName+" is logged in!");
        writeToOOS(new LoginMessage(userName, true, cc.getUserID(userName)));

        while(cc.isUserLoggedIn(userName))
        {
            System.out.println("sm in while loop");

            try
            {
                message = (Message)ois.readObject();
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

    public void sendGameUpdates()
    {
        writeToOOS(new MovementMessage(1, 2, ObjectType.METEORITE, cc.getUserID(userName)));
    }

    private Message readFromOIS()
    {
        Message message = null;

        try {
            message = ((Message)ois.readObject());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return message;
    }

    private void writeToOOS(Message message)
    {
        try {
            oos.writeObject(message);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public boolean getAlive() {
        return isAlive;
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

}
