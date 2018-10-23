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


        System.out.println("zahl eingeben: ");

        Socket s = new Socket("127.0.0.1", 9090);

        Scanner sc = new Scanner(System.in);
        int i = sc.nextInt();

        PrintWriter pw = new PrintWriter(s.getOutputStream());

        pw.print("Die oigebene zohl isch: " + i);

        pw.close();



    }
}
