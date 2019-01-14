package main.game;


import main.game.sprites.*;
import main.game.sprites.type.ObjectType;
import main.game.sprites.type.MovingObject;
import main.messages.MODelMessage;
import main.messages.MOMovMessage;
import main.messages.MONewMessage;
import main.messages.type.Message;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class World extends JPanel implements ActionListener {


    private Timer timer;
    private static int DELAY = 10; // TODO get from cc

    // visible and moving objects
    private Map<Integer, Player> players;
    private Map<Integer, Enemy> enemies;
    private List<MovingObject> movingObjects;
    private List<MovingObject> allMovingObjects;



    public World(int delay) {

        this.DELAY = delay;

        this.movingObjects = new LinkedList<>();
        this.players = new HashMap<>();
        this.enemies = new HashMap<>();


        initBoard();
    }



    private void initBoard() {
        setBackground(Color.black);

        //players.put(1, new Player(1,"player1", 80, 40, 20,20,100, PLAYER_VELOCITY));

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



    public void setPlayerPos(int playerID, int xPos, int yPos)
    {
        players.get(playerID).setxPos(xPos);
        players.get(playerID).setyPos(yPos);
    }


    public void addPlayer(Player player) {
        players.put(player.getId(), player);
    }

    public void removePlayer(int playerID)
    {
        players.remove(playerID);
    }



    public void step() {

        allMovingObjects = new ArrayList<>();

        for (Player player : players.values()) {
            player.move();
            allMovingObjects.addAll(player.getProjectiles());
            allMovingObjects.add(player);

            //addMessageAll(new MOMovMessage(player.getX(), player.getY(), player.getRotation(), ObjectType.PLAYER, player.getId()));
        }

        for (MovingObject mo : movingObjects) {
            mo.move();
            allMovingObjects.add(mo);
        }

        /*
        for (Enemy enemy : enemies.values()) {
            int playerID = enemy.getFocusPlayer();
            enemy.setFocusPlayerPos(players.get(playerID).getX(), players.get(playerID).getY());

            enemy.move();

            allMovingObjects.addAll(enemy.getProjectiles());
            allMovingObjects.add(enemy);

            //addMessageAll(new MOMovMessage(enemy.getX(), enemy.getY(), enemy.getRotation(), ObjectType.ENEMY, enemy.getId()));
        }
        */

        //generateGameObjects();

        // for sockethandlers
        //updateMovingObjectsPostions(allMovingObjects);

        //collisionDetection(allMovingObjects);

        //updateMovingObjects(allMovingObjects);

        repaint();
    }

 /*
    // check if moving objects are out of screen and delete them
    private void updateMovingObjects(List<MovingObject> allMovingObjects) {
        List<MovingObject> delMovingObjects = new LinkedList<>();

        /// recognize moving objects which are out of screen
        for (MovingObject mo : allMovingObjects) {
            if (mo.getX() < -(mo.getWidth() / 2) || mo.getX() >= (screenWidth + (mo.getWidth() / 2)) ||
                    mo.getY() < -(mo.getHeight() / 2) || mo.getY() >= (screenHeight + (mo.getHeight() / 2))) {
                delMovingObjects.add(mo);


            } else if (mo.getEnergy() <= 0)
                delMovingObjects.add(mo);
            else if (mo.isToDelete())
                delMovingObjects.add(mo);
        }

        // delete moving objects
        for (MovingObject mo : delMovingObjects) {

            if (mo.getType() == ObjectType.PLAYER_BEAM) {
                int playerID = ((Beam) mo).getPlayerID();
                players.get(playerID).getProjectiles().remove(mo);
            } else if (mo.getType() == ObjectType.ENEMY_BEAM) {
                int enemyID = ((Beam) mo).getPlayerID();
                enemies.get(enemyID).getProjectiles().remove(mo);
            } else if (mo.getType() == ObjectType.METEORITE || mo.getType() == ObjectType.COLLECTABLE)
                movingObjects.remove(mo);
            else if (mo.getType() == ObjectType.ENEMY) {
                int enemyID = ((Enemy) mo).getId();
                enemies.remove(enemyID);
            } else if (mo.getType() == ObjectType.PLAYER) {
                int playerID = ((Player) mo).getId();
                Player tmpPlayer = players.get(playerID);
                players.remove(playerID);


                // add message to all players for del enemy beam with beam damage
                addMessageAll(new MODelMessage(ObjectType.PLAYER, playerID));
            }

            // TODO player dies? - display tomb
            // TODO enemy & their beams
        }
    }


    private void collisionDetection(List<MovingObject> allMovingObjects) {
        List<MovingObject> moCollisionCheck = new ArrayList<>(allMovingObjects);

        for (MovingObject mo : allMovingObjects) {

            moCollisionCheck.remove(mo); // do not check collision with current mo

            for (MovingObject collMO : moCollisionCheck) {

                if (collMO.getType() != mo.getType()) {

                    if (collMO.getRectangleBounds().intersects(mo.getRectangleBounds())) {
                        switch (mo.getType()) {
                            case PLAYER_BEAM:
                                if (collMO.getType() == ObjectType.METEORITE || collMO.getType() == ObjectType.ENEMY) {
                                    //System.out.println("hit outer hitbox");

                                    if (mo.detectCollision(collMO.getHitboxPoints())) {
                                        //System.out.println("hit inner hitbox");

                                        collMO.reduceEnergy(mo.getEnergy());
                                        mo.setToDelete(true);

                                        if (collMO.getEnergy() <= 0) {
                                            int playerID = ((Beam) mo).getPlayerID();
                                            players.get(playerID).addGamePoints(collMO.getGamePoints());

                                            collMO.setToDelete(true);

                                            // when enemy or meteorite dies, send DEL message with points to player

                                            // add message for del collMO with points
                                            addMessageAll(new MODelMessage(collMO.getType(), collMO.getId(), 0, collMO.getGamePoints()));
                                        }

                                        // add message for del beam without any effect
                                        addMessageAll(new MODelMessage(ObjectType.PLAYER_BEAM, mo.getId(), ((Beam) mo).getPlayerID()));
                                    }
                                }
                                break;
                            case PLAYER:
                                if (collMO.getType() == ObjectType.COLLECTABLE) {

                                    collMO.setToDelete(true);

                                    int playerID = ((Player) mo).getId();
                                    players.get(playerID).addGamePoints(collMO.getGamePoints());

                                    // add message to corresponding player for del collectable with points
                                    addMessagePlayer(playerID, new MODelMessage(ObjectType.ENEMY_BEAM, collMO.getId(), ((Beam) collMO).getPlayerID(), 0, collMO.getGamePoints()));
                                }
                                if (collMO.getType() == ObjectType.METEORITE) {

                                    if (mo.detectCollision(collMO.getHitboxPoints())) {
                                        int playerEnergy = mo.getEnergy();
                                        int meteoriteEnergy = collMO.getEnergy();

                                        mo.reduceEnergy(meteoriteEnergy);
                                        collMO.reduceEnergy(playerEnergy);

                                        if (collMO.getEnergy() <= 0)
                                            addMessageAll(new MODelMessage(ObjectType.METEORITE, collMO.getId()));
                                    }
                                }
                                if (collMO.getType() == ObjectType.ENEMY_BEAM) {

                                    if (mo.detectCollision(collMO.getHitboxPoints())) {
                                        collMO.setToDelete(true);
                                        mo.reduceEnergy(collMO.getEnergy());

                                        // add message to corresponding player for del enemy beam with beam damage
                                        addMessagePlayer(mo.getId(), new MODelMessage(ObjectType.ENEMY_BEAM, collMO.getId(), ((Beam) collMO).getPlayerID(), collMO.getEnergy(), 0));
                                    }
                                }
                                break;


                        }
                    }
                }
            }
        }
    }


    */


    /* TODO fill methods

    public void addMeteorite(Meteorite meteorite) {

    }

    public void removeEmteorite(int meteoriteID) {

    }

    public void addCollectable(Collectable collectable) {
    }

    public void removeCollectable(int collectableID) {

    }

    private void addEnemy(Enemy enemy) {

    }

    public void removeEnemy(int enemyID)
    {

    }
    */



    private void doDrawing(Graphics g) {

        // TODO two player movement https://stackoverflow.com/questions/26828438/how-to-correctly-rotate-my-players-java
        //System.out.println("doDrawing");

        Graphics2D g2d = null;

        for(Player player : players.values()) {

            /* DRAW PROJECTILES PLAYER
            g2d = (Graphics2D) g.create();

            for (Beam b : player.getProjectiles()) {

                int beamImgW = b.getX() - (b.getWidth() / 2);
                int beamImgH = b.getY() - (b.getHeight() / 2);

                g2d.drawImage(b.getImage(), beamImgW, beamImgH, this);
            }
            */

            g2d = (Graphics2D) g.create();

            // DRAW PLAYER
            int imgW = player.getX() - (player.getWidth() / 2);
            int imgH = player.getY() - (player.getHeight() / 2);

            AffineTransform backup = g2d.getTransform(); // TODO w/ g2d.dispose() ? https://gamedev.stackexchange.com/questions/62196/rotate-image-around-a-specified-origin
            AffineTransform trans = new AffineTransform();

            trans.rotate(player.getRotation(), player.getX(), player.getY());
            g2d.transform(trans);

            g2d.drawImage(player.getImage(), imgW, imgH, this);

            g2d.setColor(Color.WHITE);
            g2d.draw(player.getRectangleBounds());

            g2d.drawOval(20, 20, 5,5);
        }

        /*
        // MOVE ENEMIES
        for(Enemy enemy : enemies.values())
        {
            g2d = (Graphics2D) g.create();

            // DRAW PROJECTILES ENEMY
            for (Beam b : enemy.getProjectiles()) {


                int beamImgW = b.getX() - (b.getWidth() / 2);
                int beamImgH = b.getY() - (b.getHeight() / 2);

                g2d.drawImage(b.getImage(), beamImgW, beamImgH, this);
            }

            g2d = (Graphics2D) g.create();

            // DRAW PLAYER
            int imgW = enemy.getX() - (enemy.getWidth() / 2);
            int imgH = enemy.getY() - (enemy.getHeight() / 2);

            AffineTransform backup = g2d.getTransform();
            AffineTransform trans = new AffineTransform();

            trans.rotate(enemy.getRotation(), enemy.getX(), enemy.getY());
            g2d.transform(trans);

            g2d.drawImage(enemy.getImage(), imgW, imgH, this);
        }

        // MOVE OBJECTS
        g2d = (Graphics2D) g.create();
        for(MovingObject mo : movingObjects)
        {
            int midX = mo.getX() - (mo.getWidth() / 2);
            int midY = mo.getY() - (mo.getHeight() / 2);

            g2d.drawImage(mo.getImage(), midX, midY, this);
        }
        */

        //drawHitboxes(g);
    }


    /*
    private void drawHitboxes(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.GREEN);

        for(Player player : players.values())
        {
            g2d.rotate(player.getRotation(), player.getX(), player.getY());
            //g2d.draw(player.getRectangleBounds());

            for(Point p : player.getHitboxPoints())
                g2d.drawOval(p.x, p.y, 1,1);

            g2d = (Graphics2D) g.create();
            g2d.setColor(Color.GREEN);

            for(Beam b : player.getProjectiles()) {
                g2d.draw(b.getRectangleBounds());
                g2d.drawOval(b.getX(), b.getY(), 1, 1);
            }

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
        g2d = (Graphics2D) g.create();
        g2d.setColor(Color.BLUE);

        for(Enemy enemy : enemies.values())
        {
            //g2d.draw(enemy.getRectangleBounds());
            //g2d.draw(enemy.scanNeighborhood());

            for(Point p : enemy.getHitboxPoints())
                g2d.drawOval(p.x, p.y, 1, 1);
        }
    }
    */

}
