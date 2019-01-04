package main.game;

import main.sprites.Beam;
import main.sprites.MovingObject;
import main.sprites.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class World extends JPanel implements KeyListener, ActionListener {

    private int screenWidth;
    private int screenHeight;

    // width should be alrger than height, to compute the
    private static  int METEORITE_MIN_WIDTH = 50;
    private static int METORITE_MAX_WIDTH = 250;

    private static int METEORITE_MIN_HEIGHT = 50;
    private static int METORITE_MAX_HEIGHT = 150;

    private static int PLAYER_VELOCITY = 3;

    private MovingObject mo;
    private Player player;

    private Timer timer;
    private final int DELAY = 10;


    /*
    TODO

    level length: Spiel wird side scroller, bei dem objekte eingesammelt und abgeschosen werden können
    objekte: stern, meteoriten, gegner?
    level: definiert wieviele objekte generiert werden
     */

    private int level;
    private List<MovingObject> movingObjects;

    public World(int screenWidth, int screenHeight)
    {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        this.level = 1;
        this.movingObjects = new LinkedList<MovingObject>();

        initBoard();
    }

    private void initBoard() {
        setBackground(Color.black);

        player = new Player(PLAYER_VELOCITY);

        timer = new Timer(DELAY, this);
        timer.start();
        timer.start();
    }


    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        doDrawing(g);

        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void actionPerformed(ActionEvent e) { step(); }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) { player.keyPressed(e); }

    @Override
    public void keyReleased(KeyEvent e) { player.keyReleased(e); }

    private void step() {

        player.move();

        for(MovingObject mo : movingObjects)
        {
            mo.move();
        }

        generateObjects();

        collisionDetection();

        // check if moving objects, projectiles and out of screen and 'delete' them
        updateProjectiles();
        updateMovingObjects();

        repaint();
    }

    private void updateProjectiles()
    {
        List<Beam> delBeams = new LinkedList<>();

        // recognize which beams are out of screen
        for(Beam b : player.getProjectiles())
        {
            if(b.getX() < -(b.getWidth() / 2) || b.getX() > (screenWidth + (b.getWidth() / 2)) ||
                b.getY() < -(b.getHeight() / 2) || b.getY() > (screenHeight + (b.getHeight() / 2)))
            {
                delBeams.add(b);
            }

        }

        // delete beams
        for(Beam b : delBeams)
        {
            player.getProjectiles().remove(b);
        }
    }

    private void updateMovingObjects()
    {
        List<MovingObject> delMovingObjects = new LinkedList<>();

        /// recognize moving objects which are out of screen
        for(MovingObject mo : movingObjects)
        {
            if(mo.getX() < -(mo.getWidth() / 2) || mo.getX() > (screenWidth + (mo.getWidth() / 2)) ||
                mo.getY() < -(mo.getHeight() / 2) || mo.getY() > (screenHeight + (mo.getHeight() / 2)))
            {
                delMovingObjects.add(mo);
            }
        }

        // delete moving objects
        for(MovingObject mo : delMovingObjects)
        {
            movingObjects.remove(mo);
        }
    }

    private void collisionDetection()
    {
        // TODO collision projectiles and movingObjects

        for(MovingObject mo : movingObjects)
        {
            int moXStart = mo.getX() - (mo.getWidth() / 2);
            int moXEnd = mo.getX() + (mo.getWidth() / 2);

            int moYStart = mo.getY() - (mo.getHeight() / 2);
            int moYEnd = mo.getY() + (mo.getHeight() / 2);

            if(player.getX() >= moXStart && player.getX() < moXEnd && player.getY() >= moYStart && player.getY() < moYEnd)
                System.out.println("collision");
        }
    }

    private void generateObjects() {

        if(movingObjects.isEmpty()) {

            movingObjects.add(generateMeteorite());
            movingObjects.add(generateMeteorite());
        }
    }

    private MovingObject generateMeteorite()
    {
        int meteoriteNumber = ThreadLocalRandom.current().nextInt(1, 4);

        int imgWidth =  ThreadLocalRandom.current().nextInt(METEORITE_MIN_WIDTH, METORITE_MAX_WIDTH);
        int imgHeight = ThreadLocalRandom.current().nextInt(METEORITE_MIN_HEIGHT, METORITE_MAX_HEIGHT);

        int yStart = ThreadLocalRandom.current().nextInt(0, screenHeight);
        int yEnd = ThreadLocalRandom.current().nextInt(0, screenHeight);

        double m = (double) Math.abs(yEnd - yStart) / screenHeight;

        // moving from high 'y' to low 'y' -> gradient must be negative
        if(yEnd > yStart)
            m = -m;

        double velocity = 1;

        //System.out.println("generated meteorite with\nvelocity: "+velocity+"\nstarting at y: "+yStart+"\nending at y: "+yEnd+"\nwith dY: "+dY+"\n");

        //  public MovingObject(String imgFileName, int imageWidth, int imageHeight, int xPos, int yPosStart, int yPosEnd, double m, double velocity)
        return new MovingObject("meteorite"+meteoriteNumber+".png", imgWidth, imgHeight, screenWidth, yStart, yEnd, m, velocity);
    }

    private void doDrawing(Graphics g) {

        // TODO two player movement https://stackoverflow.com/questions/26828438/how-to-correctly-rotate-my-players-java
        //System.out.println("doDrawing");

        Graphics2D g2d = (Graphics2D) g.create();

        // MOVING PLAYER
        double imgW = player.getX() - (player.getWidth() / 2);
        double imgH = player.getY() - (player.getHeight() / 2);

        double transX = -(player.getWidth() / 2);
        double transY = -(player.getHeight() / 2);
        
        g2d.translate(transX, transY);
        g2d.rotate(player.getRotation(), imgW, imgH);

        g2d.drawImage(player.getImage(), player.getX(), player.getY(), this);

        g2d.dispose();

        // MOVE PROJECTILES PLAYER
        g2d = (Graphics2D) g.create();

        for(Beam b : player.getProjectiles())
        {
            g2d.drawImage(b.getImage(), b.getX(), b.getY(), this);
        }

        // MOVE OBJECTS
        g2d = (Graphics2D) g.create();

        for(MovingObject mo : movingObjects)
        {
            int midX = mo.getX() - (mo.getWidth() / 2);
            int midY = mo.getY() - (mo.getHeight() / 2);

            g2d.drawImage(mo.getImage(), midX, midY, this);
        }



        // For testing player and objects
        g2d = (Graphics2D) g.create();
        g2d.setColor(Color.GREEN);
        g2d.drawOval(player.getX(), player.getY(), 10,10);

        g2d.dispose();
        g2d = (Graphics2D) g.create();
        g2d.setColor(Color.RED);

        for(MovingObject mo : movingObjects)
        {
            g2d.drawOval(mo.getX(), mo.getY(), 10, 10);
        }
        g2d.dispose();

        // ----------------------------------------




    }
}
