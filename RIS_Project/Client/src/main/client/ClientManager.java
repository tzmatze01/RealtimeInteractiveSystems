package main.client;

import main.handler.NetworkMessageHandler;
import main.manager.Manager;
import main.messages.LoginMessage;
import main.messages.MovementMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientManager extends Manager {

    private Socket socket;
    private String address;
    private int port;

    public ClientManager(String address, int port)
    {
        this.address = address;
        this.port = port;
    }

    @Override
    public void run() {

        ObjectOutputStream oos = null;

        try {
            socket = new Socket(address, port);
            oos = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(isAlive) {

            System.out.println("Login (1)\nMovement Message (2)");

            Scanner sc = new Scanner(System.in);
            int input = sc.nextInt();

            try{

                switch (input)
                {
                    case 1:
                        LoginMessage loginMssg = new LoginMessage("Hans", "Peter");

                        oos.writeObject(loginMssg);
                        break;
                    case 2:
                        MovementMessage movementMssg = new MovementMessage(5,4);
                        oos.writeObject(movementMssg);
                        break;
                }
                oos.flush();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }


}
