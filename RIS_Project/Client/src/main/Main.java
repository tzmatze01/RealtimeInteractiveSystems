package main;

import main.messages.LoginMessage;
import main.messages.MovementMessage;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    public static void main(String [] args) throws IOException {


        Socket s = new Socket("127.0.0.1", 9090);
        String name = "asda";

        boolean isAlive = true;
        PrintWriter pw = new PrintWriter(s.getOutputStream());

        ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());

        //s.getInputStream();

        while(isAlive) {

            System.out.println("Login (1)\nMovement Message (2)");

            Scanner sc = new Scanner(System.in);
            int input = sc.nextInt();


            switch (input)
            {
                case 1:
                    LoginMessage loginMssg = new LoginMessage("Hans", "Peter");
                    //pw.print(loginMssg);
                    //oos.write(1);

                    oos.writeObject(loginMssg);
                    break;
                case 2:
                    MovementMessage movementMssg = new MovementMessage(5,4);
                    //pw.print(movementMssg);
                    //oos.write(2 );

                    oos.writeObject(movementMssg);
                    break;
            }

            oos.flush();
            //pw.flush();
        }
        pw.close();



    }
}
