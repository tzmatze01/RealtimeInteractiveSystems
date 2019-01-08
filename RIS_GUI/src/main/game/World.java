package main.game;

import main.sprites.*;
import main.sprites.type.MovingObject;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static main.sprites.type.ObjectType.*;

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

    private Map<Integer, Player> players;
    private List<MovingObject> allMovingObjects;

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

        this.players = new HashMap<>();


        initBoard();
    }

    private void initBoard() {
        setBackground(Color.black);

        players.put(1, new Player(1,"player1", 80, 40, 20,20,100, PLAYER_VELOCITY));

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

        for(Player player : players.values())
            player.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {

        // TODO check which player

        for(Player player : players.values())
            player.keyReleased(e);
    }

    private void step() {

        allMovingObjects = new ArrayList<>();

        for(Player player : players.values())
        {
            player.move();
            allMovingObjects.addAll(player.getProjectiles());
            allMovingObjects.add(player);
        }

        for(MovingObject mo : movingObjects)
        {
            mo.move();

            // update player pos in enemy
            if(mo.getType() == ENEMY)
            {
                int playerID = ((Enemy)mo).getFocusPlayer();
                Player tmpPlayer = players.get(playerID);
                ((Enemy)mo).setFocusPlayerPos(tmpPlayer.getX(), tmpPlayer.getY());
            }

            allMovingObjects.add(mo);
        }

        generateGameObjects();

        collisionDetection(allMovingObjects);

        updateMovingObjects(allMovingObjects);

        repaint();
    }


    // check if moving objects are out of screen and delete them
    private void updateMovingObjects(List<MovingObject> allMovingObjects)
    {
        List<MovingObject> delMovingObjects = new LinkedList<>();

        /// recognize moving objects which are out of screen
        for(MovingObject mo : allMovingObjects)
        {
            if(mo.getX() < -(mo.getWidth() / 2) || mo.getX() >= (screenWidth + (mo.getWidth() / 2)) ||
                mo.getY() < -(mo.getHeight() / 2) || mo.getY() >= (screenHeight + (mo.getHeight() / 2)))
                delMovingObjects.add(mo);
            if(mo.getEnergy() <= 0)
                delMovingObjects.add(mo);
            if(mo.isToDelete())
                delMovingObjects.add(mo);
        }

        // delete moving objects
        for(MovingObject mo : delMovingObjects)
        {
            if(mo.getType() == PLAYER_BEAM)
            {
                int playerID = ((Beam)mo).getPlayerID();
                players.get(playerID).getProjectiles().remove(mo);
            }
            if(mo.getType() == METEORITE || mo.getType() == COLLECTABLE)
                movingObjects.remove(mo);

            // TODO player dies? - display tomb
            // TODO enemy & their beams
        }
    }

    private boolean simpleCollisionDetection(MovingObject mo1, MovingObject mo2)
    {
        // MO which is more on the left -> xPos + mo.getWidth / 2
        // vice versa for MO on right and yPos

        boolean collidedX = false;

        if(mo1.getX() < mo2.getX())
        {
            if((mo1.getX() + (mo1.getWidth() / 2)) > (mo2.getX() - (mo2.getWidth() / 2)))
            {
                collidedX = true;
            }

        }
        else
        {
            if((mo2.getX() + (mo2.getWidth() / 2)) > (mo1.getX() - (mo1.getWidth() / 2)))
            {
                collidedX = true;
            }
        }

        if(collidedX) {
            if (mo1.getY() < mo2.getY()) {
                if ((mo1.getY() + (mo1.getHeight() / 2)) > (mo2.getY() - (mo2.getHeight() / 2)))
                    return true;
            } else {
                if ((mo2.getY() + (mo2.getHeight() / 2)) > (mo1.getY() - (mo1.getHeight() / 2)))
                    return true;
            }
        }

        return false;
    }

    private void collisionDetection(List<MovingObject> allMovingObjects)
    {
        List<MovingObject> moCollisionCheck = new ArrayList<>(allMovingObjects);
        //List<MovingObject> moCollision = new ArrayList<>(); TODO myb for performance after each 'allMovingObjects' iteration

        for(MovingObject mo : allMovingObjects) {

            moCollisionCheck.remove(mo); // do not check collision with current mo

            for (MovingObject collMO : moCollisionCheck) {

                if(collMO.getType() != mo.getType())
                {

                    if (simpleCollisionDetection(collMO, mo))
                    {
                        // TODO Playerbeam collides with player

                        switch (mo.getType()) {
                            case PLAYER_BEAM:
                                if(collMO.getType() == METEORITE || collMO.getType() == ENEMY)
                                {
                                    System.out.println("hit outer hitbox");


                                    if(mo.detectCollision(collMO.getHitboxPoints()))
                                    {
                                        System.out.println("hit inner hitbox");

                                        collMO.reduceEnergy(mo.getEnergy());
                                        mo.setToDelete(true);

                                        if(collMO.getEnergy() <= 0)
                                        {
                                            int playerID = ((Beam)mo).getPlayerID();
                                            players.get(playerID).addGamePoints(collMO.getGamePoints());
                                        }
                                    }
                                }
                                break;
                            case PLAYER:
                                if (collMO.getType() == COLLECTABLE) {
                                    collMO.setToDelete(true);

                                    int playerID = ((Player)mo).getPlayerID();
                                    players.get(playerID).addGamePoints(collMO.getGamePoints());
                                }
                                if (collMO.getType() == METEORITE) {

                                    if(mo.detectCollision(collMO.getHitboxPoints()))
                                    {
                                        // TODO 
                                    }
                                }
                                if (collMO.getType() == ENEMY_BEAM) {

                                    if(mo.detectCollision(collMO.getHitboxPoints()))
                                    {
                                        collMO.setToDelete(true);
                                    }
                                }

                                mo.reduceEnergy(collMO.getEnergy());
                                break;
                            case ENEMY:
                                if (collMO.getType() == PLAYER_BEAM) {

                                    mo.reduceEnergy(collMO.getEnergy());

                                    if(mo.getEnergy() <= 0)
                                    {
                                        int playerID = ((Beam) collMO).getPlayerID();
                                        players.get(playerID).addGamePoints(collMO.getGamePoints());
                                    }
                                }
                                break;

                        }
                    }
                }
            }
        }
    }

    private void generateGameObjects() {

        // TODO generate every X seconds ?

        // TODO generate with level number - enemies, collectables, meteorites

        if(movingObjects.isEmpty()) {

            movingObjects.add(generateMeteorite());
            movingObjects.add(generateMeteorite());
            movingObjects.add(generateCollectable());
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

        double velocity = 1; // TODO calc velocity due size

        // TODO gamepoints

        return new Meteorite("meteorite"+meteoriteNumber, imgWidth, imgHeight, screenWidth + (imgWidth / 2), yStart, yEnd, m, velocity, 100, 10);
    }

    private Collectable generateCollectable() {

        int collectableNumber = ThreadLocalRandom.current().nextInt(1, 3);

        int yStart = ThreadLocalRandom.current().nextInt(0, screenHeight);
        int yEnd = ThreadLocalRandom.current().nextInt(0, screenHeight);

        double m = (double) Math.abs(yEnd - yStart) / screenHeight;

        // moving from high 'y' to low 'y' -> gradient must be negative
        if(yEnd > yStart)
            m = -m;

        double velocity = 3; // TODO calc velocity due size

        int imgSize = 30;

        return new Collectable("artifact"+collectableNumber, imgSize, imgSize, screenWidth + (imgSize / 2), yStart, yEnd, m, velocity, 10, 500);
    }

    private Enemy generateEnemy() {

        int focusPlayer = ThreadLocalRandom.current().nextInt(1, players.size()+1);
        int yStart = ThreadLocalRandom.current().nextInt(0, screenHeight);

        int imgSize = 60;

        return new Enemy("enemy", imgSize,imgSize, screenWidth + (imgSize / 2), yStart, 100, 300, 5, focusPlayer);
    }


    private void doDrawing(Graphics g) {

        // TODO two player movement https://stackoverflow.com/questions/26828438/how-to-correctly-rotate-my-players-java
        //System.out.println("doDrawing");

        Graphics2D g2d = null;

        for(Player player : players.values()) {
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
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.GREEN);

        for(Player player : players.values())
        {
            g2d.rotate(player.getRotation(), player.getX(), player.getY());
            g2d.draw(player.getRectangleBounds());


        }

        g2d.dispose();
        g2d = (Graphics2D) g.create();
        g2d.setColor(Color.RED);

        for(MovingObject mo : movingObjects)
        {
            g2d.draw(mo.getRectangleBounds());

            for(Point p : mo.getHitboxPoints())
                g2d.drawOval(p.x, p.y, 1,1);
        }

        g2d.dispose();
    }
}
