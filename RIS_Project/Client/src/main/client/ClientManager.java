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

public class ClientManager implements KeyListener, Manager {


    Map<MessageType, NetworkMessageHandler> listeners;
    boolean isAlive;
    boolean loggedIn;

    private Socket socket;
    private String address;
    private int port;

    private LinkedList<Message> messages;
    private ObjectOutputStream oos = null;
    private ObjectInputStream ois = null;

    private ConnectionCookie cc;
    private World world;

    private Image img;

    public ClientManager(String address, int port, World world)
    {
        this.world = world;

        this.listeners = new HashMap<>();
        this.isAlive = true;
        this.loggedIn = false;

        this.address = address;
        this.port = port;

        // FIFO
        this.messages = new LinkedList<>();

        init();
    }

    public void init()
    {
        ImageIcon ii = new ImageIcon("Client/src/main/game/resources/player1.png");
        img = ii.getImage().getScaledInstance(100, 80, 0);

    }

    /*
    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        doDrawing(g);

        Toolkit.getDefaultToolkit().sync();
    }


    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.drawOval(10, 10, 10, 10);


        Toolkit.getDefaultToolkit().sync();
        //ImageIcon ii = new ImageIcon(Toolkit.getDefaultToolkit().getImage((ResourcesManager.class.getResource("src/main/game/resources/player1.png"))));

        // check path where java is looking
        //System.out.println(System.getProperty("user.dir"));


        g2d.drawImage(img,20,20, this);


        g2d.drawOval(100, 100, 10, 10);
    }
    */

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

        // wait for logged in message
        while(!loggedIn)
        {
            Message message = readFromOIS();

            if(message.getType() == MessageType.LOGIN)
                if(((LoginMessage)message).isLoggedIn()) {
                    this.loggedIn = true;
                    this.cc = new ConnectionCookie(message.getUserID(), ((LoginMessage) message).getUserName());
                    System.out.println("Logged in with name: "+cc.getUserName());
                }
        }


        while(isAlive && cc.isLoggedIn()) {

            // TODO print out ois ?? and update gui

            // TODO check if game started

            Message message = readFromOIS();
            listeners.get(message.getType()).addMessage(message);

            System.out.println("mmsg type: "+message.getType());
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
        // TODO check if registered
        System.out.println("key listener present");

        if(loggedIn) {
            System.out.println("key pressed" + e.paramString());

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

            //System.out.println("new m: "+m.toString());
            //messages.add(m);

            // TODO get handler and write messg
            writeToOOS(message);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        // TODO check if registered

        messages.add(new KeyEventMessage(KeyEventType.KEY_RELEASED, e.getKeyCode(), cc.getOwnUserID()));
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
}
