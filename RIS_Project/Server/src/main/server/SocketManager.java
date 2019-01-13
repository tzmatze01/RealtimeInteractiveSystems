package main.server;

import main.handler.LoginHandler;
import main.handler.NetworkMessageHandler;
import main.handler.cookie.ConnectionCookie;
import main.manager.Manager;
import main.messages.LoginMessage;
import main.messages.Message;
import main.messages.type.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SocketManager implements Manager {

    protected Socket socket;
    protected Map<MessageType, NetworkMessageHandler> listeners;

    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    private boolean isAlive;

    private boolean loggedIn;

    private ConnectionCookie cc;

    public SocketManager(Socket socket, ConnectionCookie cc) {

        this.socket = socket;
        this.listeners = new HashMap<>();
        this.loggedIn = loggedIn;

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

        // wait for login message
        //LoginHandler<LoginMessage> tmpLoginHandler = (LoginHandler<LoginMessage>)listeners.get(MessageType.LOGIN);
        /*
        while(!cc.isLoggedIn())
        {
            System.out.println("idle...");
            Message message = readFromOIS();

            if(message.getType() == MessageType.LOGIN) {
                //System.out.println("dpre one!"+cc.isLoggedIn());
                listeners.get(message.getType()).addMessage(message);
                //System.out.println("done!"+cc.isLoggedIn());
            }
            else
                System.out.println("expected login message, got: "+message.getType());
        }
        */

        // TODO while loop before waits for next message

        Message mmessage = readFromOIS();
        if(mmessage.getType() == MessageType.LOGIN) {
            //System.out.println("dpre one!"+cc.isLoggedIn());
            listeners.get(mmessage.getType()).addMessage(mmessage);
            //System.out.println("done!"+cc.isLoggedIn());
        }
        else
            System.out.println("expected login message, got: "+mmessage.getType());


        //System.out.println("wait reply to user." );
        // send successful login message back to user
        writeToOOS(new LoginMessage("deiMudder", true));

        System.out.println("aent reply to user." );

        while(isAlive && loggedIn)
        {
            try
            {
                Message message = ((Message)ois.readObject());

                NetworkMessageHandler nmh = listeners.get(message.getType());
                System.out.println("msg type: "+message.getType());
                System.out.println("msg type: "+nmh.getHandledMessageType());
                nmh.addMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            // TODO get response from nmh and return
        }
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

    public void setAlive(boolean alive) {
        isAlive = alive;
    }
}
