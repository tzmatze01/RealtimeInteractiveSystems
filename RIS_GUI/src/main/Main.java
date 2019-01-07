package main;

import main.game.World;
import main.shapes.*;
import main.shapes.Image;

import java.awt.*;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.util.Scanner;

public class Main extends JFrame { //extends JPanel {

    private static int screenWidth = 1200;
    private static int screenHeight = 800;

    public Main() {

        initUI();
    }

    private void initUI() {

        // gameplan consist of lenght -> num level, rows (N) -> meteorites, collectables, enemies
        int[][] gamePlan = {{10,0,0}, {15,3,0}, {20,5,1}, {25,5,3}};

        World world = new World(screenWidth, screenHeight, gamePlan);
        world.setFocusable(true);
        world.addKeyListener(world);

        add(world);

        setSize(screenWidth, screenHeight);

        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setVisible(true);

    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            Main ex = new Main();
            ex.setVisible(true);
        });
    }

    /*
    private List<Object> shapes = new ArrayList<>();
    private Random random = new Random();


    public Main(int i, String shape) {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(400, 400));

        switch (shape) {
            case "Circles":
                for (int j = 0; j < i; j++) {
                    addCircle(390, 390);
                }
                break;
            case "Stars":
                for (int j = 0; j < i; j++) {
                    addStar(380, 380);
                }
                break;
            case "Both":
                int mid = i / 2;
                for (int j = 0; j < mid; j++) {
                    addCircle(390, 390);
                }
                for (int j = mid; j < i; j++) {
                    addStar(380, 380);
                }
                break;
        }
    }


    // this overrideed methoded draws the Graphics2D Objects in shapes
    // g is pane itself?
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Object s : shapes) {
            if (s instanceof Circle) {
                ((Circle) s).draw(g);
            } else if (s instanceof Star) {
                ((Star) s).draw(g);
            } else if (s instanceof Image) {
                ((Image) s).draw(g);
            }

        }
    }

    public void addCircle(int maxX, int maxY) {
        shapes.add(new Circle(random.nextInt(maxX), random.nextInt(maxY)));
        repaint();
    }

    public void addStar(int maxX, int maxY) {
        shapes.add(new Star(random.nextInt(maxX), random.nextInt(maxY)));
        repaint();
    }

    public void addImage(int x, int y, String path)
    {
        shapes.add(new Image(x, y, path));
        repaint();
    }

    public static void addKeyListener(JFrame frame)
    {

    }

    public static void main(String[] argv) {

        /*

        TUTORIAL

        http://zetcode.com/tutorials/javagamestutorial/movingsprites/

        //
        String shapeAmount = JOptionPane.showInputDialog(null,
                "How many shapes?", "Random Shapes...", JOptionPane.PLAIN_MESSAGE);
        int amount = Integer.parseInt(shapeAmount);

        String shapes[] = {"Stars", "Circles", "Both"};
        String shape = (String) JOptionPane.showInputDialog(null,
                "Pick the shape you want", "Random Shapes...",
                JOptionPane.PLAIN_MESSAGE, null, shapes, shapes[0]);

        JFrame frame = new JFrame();

        Main main = new Main(amount, shape);
        main.addImage(50, 50, "src/main/resources/player.png");


        frame.add(main);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        long eventMask = AWTEvent.KEY_EVENT_MASK;

        frame.getToolkit().addAWTEventListener(new AWTEventListener() {


            @Override
            public void eventDispatched(AWTEvent event) {

                System.out.println(event.paramString());
                System.out.println(event.getID());

                /*
                EVENT IDs

                KEY_TYPED : 400 - Button halten?
                KEY_PRESSED : 401 - Button erstmalig gedrückt
                KEY_RELEASED : 402 - Button losgelassen
                 //

                //Event.Key
            }


        }, eventMask);

        //addKeyListener();


    }
    */

}
