package main.game;

import main.game.sprites.*;
import main.game.sprites.type.ObjectType;
import main.game.sprites.type.MovingObject;
import main.messages.InfoMessage;
import main.messages.MODelMessage;
import main.messages.MOMovMessage;
import main.messages.MONewMessage;
import main.messages.type.Message;

import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ThreadLocalRandom;


public class World { // implements KeyListener, ActionListener {

    // width should be alrger than height, to compute the
    private static  int METEORITE_MIN_WIDTH = 50;
    private static int METORITE_MAX_WIDTH = 250;

    private static int METEORITE_MIN_HEIGHT = 50;
    private static int METORITE_MAX_HEIGHT = 150;

    private static int PLAYER_VELOCITY = 4;
    private static int PLAYER_ENERGY = 100;

    private static int ENEMY_ENERGY = 100;
    private static int ENEMY_POINTS = 300;
    private static int ENEMY_SHOOTING_DURATION = 2000; // in MS
    private static double ENEMY_VELOCITY = 2.5;

    private static double COLLECTABLE_VELOCITY = 1.5;
    private static int COLLECTABLE_POINTS = 500;

    private int screenWidth;
    private int screenHeight;




    // visible and moving objects
    private ConcurrentMap<Integer, Player> players;
    private ConcurrentMap<Integer, Enemy> enemies;
    private List<MovingObject> movingObjects;

    private List<MovingObject> allMovingObjects;

    // for ID creation of objects
    private int moCounter;
    private int enemyCounter;

    private int level;
    private int wave;
    private int[][] gamePlan;

    // message queues for registered players
    private ConcurrentMap<Integer, LinkedList<Message>> messageQueues;

    public World(int screenWidth, int screenHeight, int[][] gamePlan)
    {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        this.movingObjects = new LinkedList<>();


        this.players = new ConcurrentHashMap<>();
        this.enemies = new ConcurrentHashMap<>();

        this.moCounter = 0;
        this.enemyCounter = 0;

        this.gamePlan = gamePlan;
        this.level = 0;
        this.wave = 1;

        this.messageQueues = new ConcurrentHashMap<>();
        //initBoard();
    }

    public List<Message> getMessagesPlayer(int playerID)
    {
        //System.out.println("mq size "+messageQueues.size());
        //List<Message> newMessages = (List<Message>) messageQueues.get(playerID).clone();

        List<Message> newMessages = (List<Message>) messageQueues.get(playerID).clone();
        messageQueues.get(playerID).clear();

        return newMessages;
    }

    /*
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
    */

    //@Override
    //public void actionPerformed(ActionEvent e) { step(); }

    //@Override
    public void keyTyped(KeyEvent e) { }

    //@Override
    public void keyPressed(int playerID, int keyCode) {

        // TODO keyEvent messages are still sent, but player is already deleted
        if(players.get(playerID) != null)
            players.get(playerID).keyPressed(keyCode);

        //for(Player player : players.values())
        //    player.keyPressed(keyCode);
    }

    // @Override
    public void keyReleased(int playerID, int keyCode) {

        if(players.get(playerID) != null)
            players.get(playerID).keyReleased(keyCode);

        //for(Player player : players.values())
        //    player.keyReleased(keyCode);
    }

    public void addPlayer(int playerID)
    {

        players.put(playerID, new Player(playerID,"player", 80, 40, 20,20, PLAYER_ENERGY, PLAYER_VELOCITY));

        // add message queue for new player
        messageQueues.put(playerID, new LinkedList<>());

        // send messages of existing players to new player
        for(Player player : players.values())
            if(player.getId() != playerID)
                addMessagePlayer(playerID, new MONewMessage(ObjectType.PLAYER, player.getId(), 20, 20, 100, 80, 40, "player"+player.getId()));

        // TODO this might break if userID is generated differently --> LoginHandler
        addMessageAll(new MONewMessage(ObjectType.PLAYER, playerID, 20,20, 100, 80, 40, "player"+playerID));


    }

    private void addMessageAll(Message message)
    {
        //if(message.getType() != MessageType.MOV_MOVING_OBJECT)
        //    System.out.println("Queue both: "+message.getType());

        for(List<Message> messageQueue : messageQueues.values())
            messageQueue.add(message);
    }

    private void addMessagePlayer(int playerID, Message message)
    {
        this.messageQueues.get(playerID).add(message);
    }

    public int countRegisteredPlayers()
    {
        return this.players.size();
    }

    public void step() {

        allMovingObjects = new ArrayList<>();

        for(Player player : players.values())
        {
            player.move();
            allMovingObjects.addAll(player.getProjectiles());
            allMovingObjects.add(player);

            addMessageAll(new MOMovMessage(player.getX(), player.getY(), player.getRotation(), ObjectType.PLAYER, player.getId()));

            for(Beam b : player.getNewBeams()) {
                addMessageAll(new MONewMessage(ObjectType.PLAYER_BEAM, b.getId(), player.getId(), b.getX(), b.getY(), b.getDx(), b.getDy(), b.getVelocity(), b.getEnergy(), b.getWidth(), b.getHeight(), "beam"));
                }
        }

        for(MovingObject mo : movingObjects)
        {
            mo.move();
            allMovingObjects.add(mo);

            //System.out.println(""+mo.getType().toString()+" "+mo.getId()+" x: "+mo.getX()+ " y: "+mo.getY());
            addMessageAll(new MOMovMessage(mo.getX(), mo.getY(), 0, mo.getType(), mo.getId()));
        }

        for(Enemy enemy : enemies.values())
        {
            int playerID = enemy.getFocusPlayer();
            enemy.setFocusPlayerPos(players.get(playerID).getX(), players.get(playerID).getY());

            enemy.move();

            allMovingObjects.addAll(enemy.getProjectiles());
            allMovingObjects.add(enemy);

            addMessageAll(new MOMovMessage(enemy.getX(), enemy.getY(), enemy.getRotation(), ObjectType.ENEMY, enemy.getId()));

            for(Beam b : enemy.getNewBeams()) {
                addMessageAll(new MONewMessage(ObjectType.ENEMY_BEAM, b.getId(), enemy.getId(), b.getX(), b.getY(), b.getDx(), b.getDy(), b.getVelocity(), b.getEnergy(), b.getWidth(), b.getHeight(), "beam"));
                //System.out.println("ADD Enemy "+enemy.getId()+" Beam "+b.getId());
            }
        }

        generateGameObjects();

        collisionDetection(allMovingObjects);

        updateMovingObjects(allMovingObjects);

        //repaint();
    }


    // check if moving objects are out of screen and delete them
    private void updateMovingObjects(List<MovingObject> allMovingObjects)
    {
        List<MovingObject> delMovingObjects = new LinkedList<>();

        /// recognize moving objects which are out of screen
        for(MovingObject mo : allMovingObjects)
        {
            if(mo.getX() < -(mo.getWidth() / 2) || mo.getX() >= (screenWidth + (mo.getWidth() / 2)) ||
                    mo.getY() < -(mo.getHeight() / 2) || mo.getY() >= (screenHeight + (mo.getHeight() / 2))) {
                delMovingObjects.add(mo);
            }
            else if(mo.getEnergy() <= 0) {
                delMovingObjects.add(mo);
            }
            else if(mo.isToDelete())
                delMovingObjects.add(mo);
        }

        // delete moving objects
        for(MovingObject mo : delMovingObjects)
        {
            //addMessageAll(new MODelMessage(mo.getType(), mo.getId()));

            if(mo.getType() == ObjectType.PLAYER_BEAM)
            {
                int playerID = ((Beam)mo).getPlayerID();
                players.get(playerID).getProjectiles().remove(mo);


                addMessageAll(new MODelMessage(ObjectType.PLAYER_BEAM, mo.getId(), playerID));
            }
            else if(mo.getType() == ObjectType.ENEMY_BEAM)
            {
                int enemyID = ((Beam)mo).getPlayerID();
                enemies.get(enemyID).getProjectiles().remove(mo);

                //System.out.println("DEL Enemy "+enemyID+" Beam "+mo.getId());
                addMessageAll(new MODelMessage(ObjectType.ENEMY_BEAM, mo.getId(), enemyID));
            }
            else if(mo.getType() == ObjectType.METEORITE || mo.getType() == ObjectType.COLLECTABLE) {
                movingObjects.remove(mo);


                addMessageAll(new MODelMessage(mo.getType(), mo.getId(), 0));
            }
            else if(mo.getType() == ObjectType.ENEMY)
            {
                int enemyID = ((Enemy)mo).getId();
                enemies.remove(enemyID);

                addMessageAll(new MODelMessage(ObjectType.ENEMY, mo.getId(), 0));
            }
            else if(mo.getType() == ObjectType.PLAYER)
            {
                //Player tmpPlayer = players.get(playerID);
                players.remove(mo.getId());

                System.out.println("player "+mo.getId()+" died!");

                // add message to queue for corresponding listener to alter connection cookie
                addMessagePlayer(mo.getId(), new MODelMessage(ObjectType.PLAYER, -1, -1));

                // add message to all players that player died
                //addMessageAll(new MODelMessage(ObjectType.PLAYER, mo.getId()));
            }

            // TODO player dies? - display tomb
            // TODO enemy & their beams
        }
    }

    private void collisionDetection(List<MovingObject> allMovingObjects)
    {
        List<MovingObject> moCollisionCheck = new ArrayList<>(allMovingObjects);

        for(MovingObject mo : allMovingObjects) {

            moCollisionCheck.remove(mo); // do not check collision with current mo

            for (MovingObject collMO : moCollisionCheck) {

                if(collMO.getType() != mo.getType())
                {

                    if(collMO.getRectangleBounds().intersects(mo.getRectangleBounds()))
                    {
                        //System.out.println(""+mo.getType()+" hit "+collMO.getType());

                        switch (mo.getType()) {

                            case ENEMY:
                                if(collMO.getType() == ObjectType.METEORITE)
                                {

                                    System.out.println("enemy collides with meteorite.");

                                    int enemyEnergy = mo.getEnergy();
                                    int meteoriteEnergy = collMO.getEnergy();

                                    mo.reduceEnergy(meteoriteEnergy);
                                    collMO.reduceEnergy(enemyEnergy);
                                }
                                break;
                            case PLAYER_BEAM:
                                if(collMO.getType() == ObjectType.METEORITE || collMO.getType() == ObjectType.ENEMY)
                                {
                                    //System.out.println("hit outer hitbox");
                                    //System.out.println("Player beam hit "+collMO.getType());

                                    if(mo.detectCollision(collMO.getHitboxPoints()))
                                    {
                                        //System.out.println("hit inner hitbox");

                                        collMO.reduceEnergy(mo.getEnergy());
                                        mo.setToDelete(true);

                                        if(collMO.getEnergy() <= 0)
                                        {
                                            int playerID = ((Beam)mo).getPlayerID();
                                            players.get(playerID).addGamePoints(collMO.getGamePoints());

                                            collMO.setToDelete(true);

                                            addMessagePlayer(playerID, new InfoMessage(playerID, collMO.getGamePoints(), 0));
                                        }

                                    }
                                }
                                break;
                            case PLAYER:
                                if (collMO.getType() == ObjectType.COLLECTABLE) {

                                    collMO.setToDelete(true);

                                    int playerID = ((Player)mo).getId();
                                    players.get(playerID).addGamePoints(collMO.getGamePoints());


                                    addMessagePlayer(playerID, new InfoMessage(playerID, collMO.getGamePoints(), 0));
                                }
                                if (collMO.getType() == ObjectType.METEORITE) {

                                    if(mo.detectCollision(collMO.getHitboxPoints()))
                                    {
                                        int playerEnergy = mo.getEnergy();
                                        int meteoriteEnergy = collMO.getEnergy();

                                        mo.reduceEnergy(meteoriteEnergy);
                                        collMO.reduceEnergy(playerEnergy);


                                        addMessagePlayer(mo.getId(), new InfoMessage(mo.getId(), 0, meteoriteEnergy));
                                        //if(collMO.getEnergy() <= 0)
                                        //    addMessageAll(new MODelMessage(ObjectType.METEORITE, collMO.getId()));
                                    }
                                }
                                if (collMO.getType() == ObjectType.ENEMY_BEAM) {

                                    if(mo.detectCollision(collMO.getHitboxPoints()))
                                    {
                                        collMO.setToDelete(true);
                                        mo.reduceEnergy(collMO.getEnergy());


                                        addMessagePlayer(mo.getId(), new InfoMessage(mo.getId(), 0, collMO.getEnergy()));
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

        // there are N wave for level N

        if(movingObjects.isEmpty()) {

            if(wave == 0 && level < gamePlan.length)
            {
                ++level;
                wave = level;

                addMessageAll(new InfoMessage(level));
            }
            else if(wave > 0 && level < gamePlan.length)
            {

                for(int i = 0; i < gamePlan[level][0]; ++i)
                {
                    movingObjects.add(generateMeteorite());
                }

                for(int i = 0; i < gamePlan[level][1]; ++i)
                {
                    movingObjects.add(generateCollectable());
                }

                for(int i = 0; i < gamePlan[level][2]; ++i)
                {
                    Enemy enemy = generateEnemy();
                    enemies.put(enemy.getId(), enemy);
                }

                --wave;
            }
            else if(level >= gamePlan.length)
            {
                addMessageAll(new InfoMessage(0, true, true));
            }

        }

    }

    private MovingObject generateMeteorite()
    {
        ++moCounter;

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

        int gamePoints = (int) Math.sqrt((double)(imgWidth * imgHeight));

        addMessageAll(new MONewMessage(ObjectType.METEORITE, moCounter, screenWidth + (imgWidth / 2), yStart, yEnd, m, velocity, 100, imgWidth, imgHeight, "meteorite"+meteoriteNumber));


        //System.out.println("created meteorite: "+moCounter);
        return new Meteorite(moCounter,"meteorite"+meteoriteNumber, imgWidth, imgHeight, screenWidth + (imgWidth / 2), yStart, yEnd, m, velocity, 100, gamePoints);
    }

    private Collectable generateCollectable()
    {
        ++moCounter;

        int collectableNumber = ThreadLocalRandom.current().nextInt(1, 3);

        int yStart = ThreadLocalRandom.current().nextInt(0, screenHeight);
        int yEnd = ThreadLocalRandom.current().nextInt(0, screenHeight);

        double m = (double) Math.abs(yEnd - yStart) / screenHeight;

        // moving from high 'y' to low 'y' -> gradient must be negative
        if(yEnd > yStart)
            m = -m;

        double velocity = 3; // TODO calc velocity due size

        int imgSize = 30;

        addMessageAll(new MONewMessage(ObjectType.COLLECTABLE, moCounter, screenWidth + (imgSize / 2), yStart, yEnd, m, COLLECTABLE_VELOCITY, 10, imgSize, imgSize, "artifact"+collectableNumber));

        //System.out.println("created collectable: "+moCounter);
        return new Collectable(moCounter,"artifact"+collectableNumber, imgSize, imgSize, screenWidth + (imgSize / 2), yStart, yEnd, m, COLLECTABLE_VELOCITY, 10, COLLECTABLE_POINTS);
    }

    private Enemy generateEnemy()
    {
        ++enemyCounter;

        int focusPlayer = ThreadLocalRandom.current().nextInt(1, players.size()+1);
        int yStart = ThreadLocalRandom.current().nextInt(0, screenHeight);

        System.out.println("keys: "+players.keySet().toString()+" focus: "+focusPlayer);

        int imgSize = 60;

        addMessageAll(new MONewMessage(ObjectType.ENEMY, enemyCounter, screenWidth + (imgSize / 2), yStart, ENEMY_ENERGY, imgSize, imgSize, "enemy"));

        // System.out.println("created enemy: "+enemyCounter);
        return new Enemy(enemyCounter,"enemy", imgSize,imgSize, screenWidth + (imgSize / 2), yStart, ENEMY_ENERGY, ENEMY_POINTS, ENEMY_VELOCITY, focusPlayer, ENEMY_SHOOTING_DURATION);
    }


    /*
    private void doDrawing(Graphics g) {

        // TODO two player movement https://stackoverflow.com/questions/26828438/how-to-correctly-rotate-my-players-java
        //System.out.println("doDrawing");

        Graphics2D g2d = null;

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

            trans.rotate(player.getRotation(), player.getX(), player.getY());
            g2d.transform(trans);

            g2d.drawImage(player.getImage(), imgW, imgH, this);

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
        for(MovingObject mo : movingObjects)
        {
            int midX = mo.getX() - (mo.getWidth() / 2);
            int midY = mo.getY() - (mo.getHeight() / 2);

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
