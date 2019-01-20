package main.game;


import main.game.sprites.*;
import main.game.sprites.type.MovingObject;
import main.network.ConnectionCookie;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import javax.swing.JLabel;
import java.sql.Connection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;



public class World extends JPanel implements ActionListener {


    private Timer timer;
    private static int DELAY = 10; // TODO get from cc

    // visible and moving objects
    private ConcurrentMap<Integer, Player> players;
    private ConcurrentMap<Integer, Enemy> enemies;
    private ConcurrentMap<Integer, MovingObject> movingObjects;

    private Image background;

    private int screenwidth;
    private int screenheight;

    private JLabel labelHealth;
    private JLabel labelPoints;

    private int level;
    private int playerID;
    private boolean gameStart;

    private ConnectionCookie cc;

    public World(int delay, int screenwidth, int screenheight) {

        this.DELAY = delay;
        this.screenwidth = screenwidth;
        this.screenheight = screenheight;

        this.players = new ConcurrentHashMap<>();
        this.enemies = new ConcurrentHashMap<>();
        this.movingObjects = new ConcurrentHashMap<>();

        this.gameStart = false;
        this.level = 0;
        this.playerID = 0;

        initBoard();
    }

    private void initBoard() {
        setBackground(Color.black);

        ImageIcon ii = new ImageIcon("Client/src/main/game/resources/space.jpeg");
        this.background = ii.getImage().getScaledInstance(screenwidth, screenheight, 0);

        timer = new Timer(DELAY, this);
        timer.start();
        timer.start();
    }


    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        if (gameStart && players.size() > 0) {
            doDrawing(g);
        }
        // TODO not working
        //g.drawImage(background, 0, 0, null);

        Toolkit.getDefaultToolkit().sync();

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(gameStart && players.size() > 0)
            step();
    }



    public void step() {

        // pos beams of player are not sent over network
        for (Player player : players.values()) {
            player.move();
        }

        /*
        for (MovingObject mo : movingObjects.values()) {
            mo.move();
        }
        */

        // pos beams of player are not sent over network
        for (Enemy enemy : enemies.values()) {

            enemy.move();
        }

        repaint();
    }



    public void setPlayerPos(int playerID, int xPos, int yPos, double rotation)
    {

        // needs if check, because some movement message arrive earlier than creation messages
        if(players.containsKey(playerID)) {

            //System.out.println("set pos playeriD "+playerID);
            players.get(playerID).setXPos(xPos);
            players.get(playerID).setYPos(yPos);
            players.get(playerID).setdRotation(rotation);
        }
    }


    public void addPlayer(Player player) {

        // 1st player is own ID
        //if(playerID == 1000)
        //    this.playerID = player.getId();

        players.put(player.getId(), player);
    }

    public void removePlayer(int playerID)
    {
        players.remove(playerID);
    }

    public void addMeteorite(Meteorite meteorite) {
        movingObjects.put(meteorite.getId(), meteorite);
    }

    public void removeMeteorite(int meteoriteID) {
        movingObjects.remove(meteoriteID);
    }


    public void addCollectable(Collectable collectable) {
        movingObjects.put(collectable.getId(), collectable);
    }

    public void removeCollectable(int collectableID) {
        movingObjects.remove(collectableID);
    }

    public void addEnemy(Enemy enemy) {
        enemies.put(enemy.getId(), enemy);
    }

    public void removeEnemy(int enemyID)
    {
        enemies.remove(enemyID);
    }

    public void addEnemyBeam(int enemyID, Beam beam)
    {
        //System.out.println("ADD Enemy "+enemyID+" Beam "+beam.getId());

        enemies.get(enemyID).addBeam(beam);
    }

    public void removeEnemyBeam(int enemyID, int beamID)
    {
        /*
        System.out.print("enemy beam keys: ");
        for(Beam b : enemies.get(enemyID).getProjectiles())
            System.out.print(b.getId()+ " ");
        */

        //System.out.println("DEL Enemy "+enemyID+" Beam "+beamID);

        enemies.get(enemyID).removeBeam(beamID);
    }

    public void addPlayerBeam(int playerID, Beam beam)
    {

        players.get(playerID).addBeam(beam);
    }

    public void removePlayerBeam(int playerID, int beamID)
    {
        /*
        System.out.print("player beam keys: ");
        for(Beam b : players.get(playerID).getProjectiles())
            System.out.print(b.getId()+ " ");
        */

        players.get(playerID).removeBeam(beamID);
    }


    public void setEnemyPos(int enemyID, int xPos, int yPos, double rotation)
    {
        // needs if check, because some movement message arrive earlier than creation messages
        if(enemies.containsKey(enemyID)) {
            enemies.get(enemyID).setXPos(xPos);
            enemies.get(enemyID).setYPos(yPos);
            enemies.get(enemyID).setRotation(rotation);
        }
    }

    public void setMOPos(int moID, int xPos, int yPos)
    {
        if(movingObjects.containsKey(moID)) {
            movingObjects.get(moID).setXPos(xPos);
            movingObjects.get(moID).setYPos(yPos);
        }
    }
    public void addPlayerPoints(int playerID, int points)
    {
        if(players.containsKey(playerID))
            players.get(playerID).addGamePoints(points);
    }

    public void addPlayerDamage(int playerID, int damage)
    {
        if(players.containsKey(playerID))
            players.get(playerID).reduceEnergy(damage);
    }

    public void setLevel(int level)
    {
        this.level = level;

       // System.out.println("New level: "+ level);
    }

    private void doDrawing(Graphics g) {

        // TODO two player movement https://stackoverflow.com/questions/26828438/how-to-correctly-rotate-my-players-java
       // System.out.println("doDrawing");

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.drawImage(background, 0   , 0, this);

        FontRenderContext frc = g2d.getFontRenderContext();
        Font font1 = new Font("Courier", Font.BOLD, 24);

        String str1 = new String("HEALTH "+players.get(playerID).getEnergy());
        TextLayout tl = new TextLayout(str1, font1, frc);
        g2d.setColor(Color.green);
        tl.draw(g2d, 10, 20);


        // TODO only show health 0

        //frc = g2d.getFontRenderContext();
        String str2 = new String("POINTS "+players.get(playerID).getGamePoints());
        TextLayout tl2 = new TextLayout(str2, font1, frc);
        tl2.draw(g2d, 630, 20);

        String str3 = new String("LEVEL "+level);
        TextLayout tl3 = new TextLayout(str3, font1, frc);
        tl3.draw(g2d, 350, 20);

        for(Player player : players.values()) {

            // DRAW PROJECTILES PLAYER
            g2d = (Graphics2D) g.create();

            for (Beam b : player.getProjectiles()) {

                int beamImgW = b.getX() - (b.getWidth() / 2);
                int beamImgH = b.getY() - (b.getHeight() / 2);

                g2d.drawImage(b.getImage(), beamImgW, beamImgH, this);
            }


            g2d = (Graphics2D) g.create();

            // DRAW PLAYER
            int imgW = player.getX() - (player.getWidth() / 2);
            int imgH = player.getY() - (player.getHeight() / 2);

            AffineTransform backup = g2d.getTransform(); // TODO w/ g2d.dispose() ? https://gamedev.stackexchange.com/questions/62196/rotate-image-around-a-specified-origin
            AffineTransform trans = new AffineTransform();

            //System.out.println("player pos: "+player.getRotation()+" "+player.getX()+" "+player.getY());

            trans.rotate(player.getRotation(), player.getX(), player.getY());
            g2d.transform(trans);

            g2d.drawImage(player.getImage(), imgW, imgH, this);

            //g2d.setColor(Color.WHITE);
            //g2d.draw(player.getRectangleBounds());
            //g2d.drawOval(20, 20, 5,5);
        }


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
        for(MovingObject mo : movingObjects.values())
        {
            int midX = mo.getX() - (mo.getWidth() / 2);
            int midY = mo.getY() - (mo.getHeight() / 2);


            //System.out.println(""+mo.getType().toString()+" "+mo.getId()+" x: "+mo.getX()+ " y: "+mo.getY());

            g2d.drawImage(mo.getImage(), midX, midY, this);
        }


        //drawHitboxes(g);
    }



    private void drawHitboxes(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.GREEN);

        for(Player player : players.values())
        {
            g2d.rotate(player.getRotation(), player.getX(), player.getY());
            //g2d.draw(player.getRectangleBounds());

            //for(Point p : player.getHitboxPoints())
            //    g2d.drawOval(p.x, p.y, 1,1);

            //g2d = (Graphics2D) g.create();
            //g2d.setColor(Color.GREEN);


            //for(Beam b : player.getProjectiles()) {
            //    g2d.draw(b.getRectangleBounds());
            //    g2d.drawOval(b.getX(), b.getY(), 1, 1);
            //}

        }

        g2d.dispose();
        g2d = (Graphics2D) g.create();
        g2d.setColor(Color.RED);

        for(MovingObject mo : movingObjects.values())
        {
            g2d.draw(mo.getRectangleBounds());

            //for(Point p : mo.getHitboxPoints())
            //    g2d.drawOval(p.x, p.y, 1,1);
        }


        g2d.dispose();
        g2d = (Graphics2D) g.create();
        g2d.setColor(Color.BLUE);

        for(Enemy enemy : enemies.values())
        {
            g2d.draw(enemy.getRectangleBounds());
            //g2d.draw(enemy.scanNeighborhood());

            //for(Point p : enemy.getHitboxPoints())
            //    g2d.drawOval(p.x, p.y, 1, 1);
        }
    }

    public void setGameStart(boolean gameStart) {
        this.gameStart = gameStart;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        System.out.println("init playeriD "+playerID);
        this.playerID = playerID;
    }
}
