package main;

import main.server.Client;
import main.server.Server;

public class Main {

    public static void main(String [] args)
    {

        Thread t = new Thread(new Server());
        t.start();

        Thread t2 = new Thread(new Client());
        t2.start();
    }
}
