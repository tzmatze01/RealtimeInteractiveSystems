package main.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {



    @Override
    public void run() {

        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(9090);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(true)
        {
            System.out.println("Waiting for mssgs...");

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

                System.out.println("got: "+ois.readInt());
                //Message message = ((Message)ois.readObject());

                //NetworkMessageHandler nmh = listeners.get(message.getType());
                //nmh.addMessage(message);
                //nmh.run();
            } catch (IOException e) {
                e.printStackTrace();
            }


            // unterscheiden was für messages
            // richtigen Hanler aus pool suchen
            // handler thread starten
            // ---> ??Queue vorlesung
        }
    }
}
