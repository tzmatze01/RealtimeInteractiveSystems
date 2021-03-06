package main.server;

import main.game.sprites.type.ObjectType;
import main.handler.NetworkMessageHandler;
import main.manager.Manager;
import main.messages.LoginMessage;
import main.messages.MOMovMessage;
import main.messages.type.Message;
import main.messages.type.MessageType;
import main.network.ConnectionCookie;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
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

    private boolean gameStart;

    public SocketManager(Socket socket, ConnectionCookie cc) {

        this.socket = socket;
        this.listeners = new HashMap<>();
        //this.loggedIn = false;

        this.gameStart = false;

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
            //System.out.println("sm in while loop");

            try
            {
                message = (Message)ois.readObject();

                if(!listeners.containsKey(message.getType()))
                    System.out.println("found no listner for: "+message.getType());

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

    public void sendGameUpdates(List<Message> messages)
    {
        /*
        if(!gameStart)
        {
            // TODO send game init messages

            // TODO cc set userplaying?

            gameStart = true;
        }
        else if(gameStart)
        {

            // 1. check created obj
            // 2. check (both) player pos & health
            // 3. check obj, enemies movement

            writeToOOS(new MOMovMessage(1, 2, ObjectType.METEORITE, cc.getUserID(userName)));

            // TODO check player health -> set cc is Playing

        }
        */

        for(Message m : messages)
            writeToOOS(m);
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

    public String getUserName() {
        return userName;
    }
}
