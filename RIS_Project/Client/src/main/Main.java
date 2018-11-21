package main;

import main.client.ClientManager;
import main.messages.LoginMessage;
import main.messages.MovementMessage;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    public static void main(String [] args) throws IOException {

        ClientManager clientManager = new ClientManager("localhost", 9090);

        Thread clientThread = new Thread(clientManager);
        clientThread.start();
    }
}
