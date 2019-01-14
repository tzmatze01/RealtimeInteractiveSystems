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

    private double rotation;
    private int oldRotation;

    private int velocity;

    private List<Beam> projectiles;
    private static int BEAM_WIDTH = 20;
    private static int BEAM_HEIGHT = 8;

    private MediaPlayer mp;

    private int beamCounter;

    public Player(int playerID, String imgFileName, int imageWidth, int imageHeight, int xPos, int yPos, int energy) {
        super(playerID, ObjectType.PLAYER, imgFileName, imageWidth, imageHeight, xPos, yPos, energy);

        //this.playerID = playerID;

        this.rotation = 0;
        this.oldRotation = 0;

        this.velocity = velocity;

        this.projectiles = new ArrayList<>();

    }

    private void loadSound(String filename) {

        Media sound = new Media(new File("src/main/resources/sound/"+filename).toURI().toString());
        this.mp = new MediaPlayer(sound);
    }

    public void move() {

        // TODO only move beams in loop

        for(Beam b : projectiles)
        {
            b.move();
        }
    }

    public void addBeam(Beam beam)
    {
        projectiles.add(beam);
    }

    private void shoot()
    { }

    public void setdRotation(double radians)
    {
        this.rotation = radians;
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

}