package main.client;

import main.game.World;
import main.handler.NetworkMessageHandler;
import main.manager.Manager;
import main.messages.*;
import main.messages.type.KeyEventType;
import main.messages.type.Message;
import main.messages.type.MessageType;
import main.network.ConnectionCookie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;

public class ClientManager extends Manager implements KeyListener {



    boolean loggedIn;

    private Socket socket;
    private String address;
    private int port;

    private LinkedList<Message> messages;
    private ObjectOutputStream oos = null;
    private ObjectInputStream ois = null;

    private ConnectionCookie cc;

    /*/

    TODO

    Connection cookie von aussen kreieren und INfohandler #  Clientmanager geben
     */

    public ClientManager(String address, int port, ConnectionCookie cc)
    {
        super();
        this.cc = cc;


        this.loggedIn = false;

        this.address = address;
        this.port = port;

        // FIFO
        this.messages = new LinkedList<>();
    }

    @Override
    public void run() {

        try {
            socket = new Socket(address, port);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }


        // login user
        System.out.println("Login Name:\n");
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();

        writeToOOS(new LoginMessage(input, ""));

        /*
        // wait for logged in message
        while(!loggedIn)
        {
            Message message = readFromOIS();

            if(message.getType() == MessageType.LOGIN)
                if(((LoginMessage)message).isLoggedIn()) {
                    this.loggedIn = true;

                    this.cc.setUserPlaying(message.getUserID(), true);

                    System.out.println("Logged in with name: "+cc.getUserName());
                }
        }
        */

        while(isAlive) {


            Message message = readFromOIS();

            //if(message.getType() != MessageType.MOV_MOVING_OBJECT)
            //    System.out.println("mmsg type: "+message.getType());

            listeners.get(message.getType()).addMessage(message);

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

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {

        //System.out.println(" before key pressed" );


        if(cc.isLoggedIn()) {
            //System.out.println("key pressed" + e.paramString());

            Message message = null;

            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_W:
                case KeyEvent.VK_A:
                case KeyEvent.VK_S:
                case KeyEvent.VK_D:
                case KeyEvent.VK_SPACE:
                    message = new KeyEventMessage(KeyEventType.KEY_PRESSED, e.getKeyCode(), cc.getOwnUserID());
                    break;
                case KeyEvent.VK_ESCAPE:
                    message = new LogoutMessage(cc.getOwnUserID());
                    break;
                case KeyEvent.VK_L:
                    message = new LoginMessage("test", "asdads");
                    break;
            }

            writeToOOS(message);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        if(cc.isLoggedIn()) {

            Message message = null;

            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_W:
                case KeyEvent.VK_A:
                case KeyEvent.VK_S:
                case KeyEvent.VK_D:
                case KeyEvent.VK_SPACE:
                    message = new KeyEventMessage(KeyEventType.KEY_RELEASED, e.getKeyCode(), cc.getOwnUserID());
                    break;
            }

            writeToOOS(message);
        }
    }
}
