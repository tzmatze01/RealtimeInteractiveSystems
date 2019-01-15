package main.game.sprites;

import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import main.game.sprites.type.ObjectType;
import main.game.sprites.type.MovingObject;


public class Player extends MovingObject {

    //private int playerID;

    private int dMovement;
    private int dRotation;

    private double ddx;
    private double ddy;

    private int rotation;
    private int oldRotation;

    private int velocity;

    private List<Beam> projectiles;
    private List<Beam> projMessage;

    private static int BEAM_WIDTH = 20;
    private static int BEAM_HEIGHT = 8;

    private MediaPlayer mp;

    private int beamCounter;

    public Player(int playerID, String imgFileName, int imageWidth, int imageHeight, int xPos, int yPos, int energy, int velocity) {
        super(playerID, ObjectType.PLAYER, imgFileName, imageWidth, imageHeight, xPos, yPos, energy, 0);

        //this.playerID = playerID;

        this.rotation = 0;
        this.oldRotation = 0;

        this.velocity = velocity;

        this.projectiles = new ArrayList<>();
        this.projMessage = new ArrayList<>();

        this.beamCounter = 0;
        //loadSound(filename);
    }

    private void loadSound(String filename) {

        Media sound = new Media(new File("src/main/resources/sound/"+filename).toURI().toString());
        this.mp = new MediaPlayer(sound);
    }

    public List<Beam> getNewBeams()
    {
        List<Beam> tmpBeams = new ArrayList<>(projMessage);
        projMessage.clear();

        return tmpBeams;
    }

    public void move() {

        ddx = Math.abs(Math.cos(Math.toRadians(rotation)));
        ddy = Math.abs(Math.sin(Math.toRadians(rotation)));

        double xMovement = ddx * dMovement;
        double yMovement = ddy * dMovement;

        if(rotation >= 0 && rotation < 90)
        {
            xPos += xMovement;
            yPos += yMovement;
        }
        else if(rotation >= 90 && rotation < 180)
        {
            xPos -= xMovement;
            yPos += yMovement;
        }
        else if(rotation >= 180 && rotation < 270)
        {
            xPos -= xMovement;
            yPos -= yMovement;
        }
        else if(rotation >= 270 && rotation < 360)
        {
            xPos += xMovement;
            yPos -= yMovement;
        }


        if(dRotation < 0 && rotation == 0)
        {
            rotation = 360;
        }
        else if(dRotation > 0 && rotation == 360)
        {
            rotation = 0;
        }

        rotation += dRotation;


        for(Beam b : projectiles)
        {
            b.move();
        }
    }

    private void shoot()
    {
        beamCounter++;

        double dx = 0;
        double dy = 0;

        if(rotation >= 0 && rotation < 90)
        {
            dx += ddx;
            dy += ddy;
        }
        else if(rotation >= 90 && rotation < 180)
        {
            dx -= ddx;
            dy += ddy;
        }
        else if(rotation >= 180 && rotation < 270)
        {
            dx -= ddx;
            dy -= ddy;
        }
        else if(rotation >= 270 && rotation < 360)
        {
            dx += ddx;
            dy -= ddy;
        }

        Beam beam = new Beam(beamCounter, ObjectType.PLAYER_BEAM, getId(),"beam", BEAM_WIDTH, BEAM_HEIGHT,  getX(), getY(), dx, dy, 6);
        projectiles.add(beam);
        projMessage.add(beam);
    }

    public double getRotation()
    {
        return Math.toRadians(this.rotation);
    }

    public List<Beam> getProjectiles()
    {
        return this.projectiles;
    }

    /*
    public int getPlayerID() {
        return playerID;
    }
    */

    public void reduceGamePoints(int amount)
    {
        gamePoints -= amount;
    }

    public void addGamePoints(int amount) {
        gamePoints += amount;

        System.out.println("players points: "+gamePoints);
    }

    public void keyPressed(int keyCode) {

        //int keyCode = e.getKeyCode();

        //System.out.println("got key: "+keyCode);

        switch( keyCode ) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                //System.out.println("player up.");
                dMovement = velocity;
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                //System.out.println("player down.");
                dMovement = -velocity;
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                //System.out.println("player left.");
                dRotation = -2;
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT :
                //System.out.println("player right.");
                dRotation = 2;
                break;
            case KeyEvent.VK_SPACE:
                //System.out.println("player shoot.");
                shoot();
                break;

        }

        //System.out.println();
    }

    public void keyReleased(int keyCode) {

        //int keyCode = e.getKeyCode();

        switch( keyCode ) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_S:
            case KeyEvent.VK_UP:
            case KeyEvent.VK_DOWN:
                dMovement = 0;
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_D:
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT :
                dRotation = 0;
                break;
        }
    }
}