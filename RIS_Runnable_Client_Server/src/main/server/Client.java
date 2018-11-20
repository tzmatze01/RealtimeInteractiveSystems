package main.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {


    @Override
    public void run() {

        Socket s = null;
        try {
            s = new Socket("127.0.0.1", 9090);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String name = "asda:";

        boolean isAlive = true;
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(s.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(s.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            oos.writeInt(2 );
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
