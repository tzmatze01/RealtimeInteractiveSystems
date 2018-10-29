package main;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    public static void main(String [] args) throws IOException {


        Socket s = new Socket("127.0.0.1", 9090);
        String name = "asda:";
        boolean isAlive = true;
        PrintWriter pw = new PrintWriter(s.getOutputStream());

        while(isAlive) {

            System.out.println("text eingeben: ");


            Scanner sc = new Scanner(System.in);
            String str = sc.nextLine();


            pw.print(name + "" + str);
            pw.flush();
        }
        pw.close();



    }
}
