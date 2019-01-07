package main.game;

import main.sprites.Beam;
import main.sprites.Meteorite;
import main.sprites.type.MovingObject;
import main.sprites.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
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
    //private Player player;

    private Timer timer;
    private final int DELAY = 10;


    private int level;
    private int[][] gamePlan;
    private int maxLevel;

    private List<Player> players;
    /*
    TODO

    level length: Spiel wird side scroller, bei dem objekte eingesammelt und abgeschosen werden können
    objekte: stern, meteoriten, gegner?
    level: definiert wieviele objekte generiert werden
     */

    private List<MovingObject> movingObjects;

    public World(int screenWidth, int screenHeight, int[][] gamePlan)
    {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        this.level = 1;
        this.movingObjects = new LinkedList<>();

        this.gamePlan = gamePlan;
        this.level = 1;
        this.maxLevel = gamePlan.length;

        this.players = new ArrayList<>();


        initBoard();
    }

    private void initBoard() {
        setBackground(Color.black);

        players.add(new Player("player.png", 80, 40, PLAYER_VELOCITY));

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
    public void keyPressed(KeyEvent e) {

        // TODO check which player

        for(Player player : players)
            player.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {

        // TODO check which player

        for(Player player : players)
            player.keyReleased(e);
    }

    private void step() {

        for(Player player : players)
            player.move();

        for(MovingObject mo : movingObjects)
            mo.move();

        generateObjects();

        collisionDetection();

        // check if moving objects, projectiles and out of screen and 'delete' them
        //updateProjectiles();
        updateMovingObjects();



        repaint();
    }

    /*
    private void updateMovingObjects()
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
            else if(b.isHitObject())
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
    */

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
            else if(mo.getEnergy() <= 0)
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

        // TODO add hitbox of player to calculation

        //int playerCornerX = player.getX() + (player.getWidth() / 2);
        //int playerCornerY = player.getY() + (player.getHeight() / 2);

        //System.out.println("player center : " + player.getX() + " / " + player.getY());
        //System.out.println("player corner : " + playerCornerX + " / " + playerCornerY);

        for(MovingObject mo : movingObjects)
        {
            int moXStart = mo.getX() - (mo.getWidth() / 2);
            int moXEnd = mo.getX() + (mo.getWidth() / 2);

            int moYStart = mo.getY() - (mo.getHeight() / 2);
            int moYEnd = mo.getY() + (mo.getHeight() / 2);

            for(Player player : players)
            {
                // check collision with player
                if (player.getX() >= moXStart && player.getX() < moXEnd && player.getY() >= moYStart && player.getY() < moYEnd) {
                    System.out.println("collision");

                }

                // check collision with any beam
                for (Beam b : player.getProjectiles())
                {
                    if (b.getX() >= moXStart && b.getX() < moXEnd && b.getY() >= moYStart && b.getY() < moYEnd)
                    {
                        b.setHitObject(true);
                        mo.reduceEnergy(b.getEnergy());
                    }
                }
            }
        }
    }

    private void generateObjects() {

        // TODO generate every X seconds ?

        // TODO generate with level number - enemies, collectables, meteorites

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

        return new Meteorite("meteorite"+meteoriteNumber+".png", imgWidth, imgHeight, screenWidth, yStart, yEnd, m, velocity, 100);
        //return new Meteorite("debris.png", imgWidth, imgHeight, screenWidth, yStart, yEnd, m, velocity, 100);
    }

    private void doDrawing(Graphics g) {

        // TODO two player movement https://stackoverflow.com/questions/26828438/how-to-correctly-rotate-my-players-java
        //System.out.println("doDrawing");

        Graphics2D g2d = null;

        for(Player player : players) {
            g2d = (Graphics2D) g.create();

            // MOVING PLAYER
            int imgW = player.getX() - (player.getWidth() / 2);
            int imgH = player.getY() - (player.getHeight() / 2);

            AffineTransform backup = g2d.getTransform(); // TODO w/ g2d.dispose() ? https://gamedev.stackexchange.com/questions/62196/rotate-image-around-a-specified-origin
            AffineTransform trans = new AffineTransform();

            trans.rotate(player.getRotation(), player.getX(), player.getY());
            g2d.transform(trans);

            g2d.drawImage(player.getImage(), imgW, imgH, this);

            g2d.dispose();

            // MOVE PROJECTILES PLAYER
            g2d = (Graphics2D) g.create();

            for (Beam b : player.getProjectiles()) {
                g2d.drawImage(b.getImage(), b.getX(), b.getY(), this);
            }
        }
        // MOVE OBJECTS
        g2d = (Graphics2D) g.create();

        for(MovingObject mo : movingObjects)
        {
            int midX = mo.getX() - (mo.getWidth() / 2);
            int midY = mo.getY() - (mo.getHeight() / 2);

            g2d.drawImage(mo.getImage(), midX, midY, this);
        }

        drawHitboxes(g);
    }

    private void drawHitboxes(Graphics g)
    {
        Graphics2D g2d = null;

        for(Player player : players) {

            g2d = (Graphics2D) g.create();

            // For testing player and objects
            g2d.setColor(Color.GREEN);
            g2d.drawOval(player.getX(), player.getY(), 10, 10);

            AffineTransform backup = g2d.getTransform();
            AffineTransform trans = new AffineTransform();

            trans.rotate(player.getRotation(), player.getX(), player.getY());
            g2d.transform(trans);

            int pX1 = player.getX() - (player.getWidth() / 2);
            int pY1 = player.getY() - (player.getHeight() / 2);

            int pX2 = player.getX() + (player.getWidth() / 2);
            int pY2 = player.getY() + (player.getHeight() / 2);

            g2d.drawLine(pX1, pY1, pX1, pY2);
            g2d.drawLine(pX2, pY1, pX2, pY2);

            g2d.drawLine(pX1, pY1, pX2, pY1);
            g2d.drawLine(pX1, pY2, pX2, pY2);

            //Point p = new Point(pX2, pY2);
            //g2d.draw(p);

            g2d.drawOval(pX2, pY2, 5, 5);

            g2d.dispose();
        }

        g2d = (Graphics2D) g.create();
        g2d.setColor(Color.RED);

        for(MovingObject mo : movingObjects)
        {
            g2d.drawOval(mo.getX(), mo.getY(), 10, 10);

            int moX1 =  mo.getX() - (mo.getWidth() / 2);
            int moY1 = mo.getY() - (mo.getHeight() / 2);

            int moX2 =  mo.getX() + (mo.getWidth() / 2);
            int moY2 = mo.getY() + (mo.getHeight() / 2);

            g2d.drawLine(moX1, moY1, moX1, moY2);
            g2d.drawLine(moX2, moY1, moX2, moY2);

            g2d.drawLine(moX1, moY1, moX2, moY1);
            g2d.drawLine(moX1, moY2, moX2, moY2);
        }
        g2d.dispose();

        // ----------------------------------------

    }
}
