package main.client;

import main.handler.NetworkMessageHandler;
import main.manager.Manager;
import main.messages.LoginMessage;
import main.messages.Message;
import main.messages.MovementMessage;
import main.messages.type.KeyEventType;
import main.messages.type.MessageType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;

public class ClientManager extends JPanel implements KeyListener, Manager {


    Map<MessageType, NetworkMessageHandler> listeners;
    boolean isAlive;

    private Socket socket;
    private String address;
    private int port;


    private LinkedList<Message> messages;
    private ObjectOutputStream oos = null;


    public ClientManager(String address, int port)
    {
        this.listeners = new HashMap<>();
        this.isAlive = true;

        this.address = address;
        this.port = port;

        // FIFO
        this.messages = new LinkedList<>();
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        doDrawing(g);

        Toolkit.getDefaultToolkit().sync();
    }


    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.drawOval(10, 10, 10, 10);
    }

    @Override
    public void run() {

        //ObjectOutputStream oos = null;

        try {
            socket = new Socket(address, port);
            oos = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //System.out.println("Login (1)\nMovement Message (2)");



        while(isAlive) {

            // TODO print out ois ?? and update gui

            // TODO check if game started

            if(!messages.isEmpty())
            {
                try {
                    oos.writeObject(messages.removeFirst());
                    System.out.println("send message");

                    oos.flush();
                    // TODO get correct handler and give message to it
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO check if registered

        //System.out.println("key pressed"+e.paramString());
        Message message = null;

        switch (e.getKeyCode())
        {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_W:
            case KeyEvent.VK_A:
            case KeyEvent.VK_S:
            case KeyEvent.VK_D:
            case KeyEvent.VK_SPACE:
                message = new MovementMessage(KeyEventType.KEY_PRESSED, e.getKeyCode());
                break;
        }

        //System.out.println("new m: "+m.toString());
        //messages.add(m);

        // TODO get handler and write messg
        try {
            oos.writeObject(message);
            oos.flush();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO check if registered

        messages.add(new MovementMessage(KeyEventType.KEY_RELEASED, e.getKeyCode()));

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
