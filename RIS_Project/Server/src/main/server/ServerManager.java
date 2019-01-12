package main.server;

import main.manager.Manager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

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
}
