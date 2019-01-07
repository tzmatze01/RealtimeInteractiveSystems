package main.sprites;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import main.sprites.type.MovingObject;
import main.sprites.type.ObjectType;


public class Player extends MovingObject {

    private int playerID;

    private int dMovement;
    private int dRotation;

    private double ddx;
    private double ddy;

    private int rotation;
    private int oldRotation;

    private int velocity;

    private List<Beam> projectiles;
    private static int BEAM_WIDTH = 20;
    private static int BEAM_HEIGHT = 8;

    private MediaPlayer mp;


    public Player(int playerID, String imgFileName, int imageWidth, int imageHeight, int xPos, int yPos, int energy, int velocity) {
        super(ObjectType.PLAYER, imgFileName, imageWidth, imageHeight, xPos, yPos, energy, 0);

        this.playerID = playerID;

        //this.w = imageWidth;
        //this.h = imageHeight;

        this.rotation = 0;
        this.oldRotation = 0;

        this.velocity = velocity;

        this.projectiles = new ArrayList<>();

        //loadSound(filename);
    }

    private void loadSound(String filename) {

        Media sound = new Media(new File("src/main/resources/sound/"+filename).toURI().toString());
        this.mp = new MediaPlayer(sound);
    }

    public void move() {

        ddx = Math.abs(Math.cos(rotation) * dMovement);
        ddy = Math.abs(Math.sin(rotation) * dMovement);


        if(rotation >= 0 && rotation < 90)
        {
            xPos += ddx;
            yPos += ddy;
        }
        if(rotation >= 90 && rotation < 180)
        {
            xPos -= ddx;
            yPos += ddy;
        }
        if(rotation >= 180 && rotation < 270)
        {
            xPos -= ddx;
            yPos -= ddy;
        }
        if(rotation >= 270 && rotation < 360)
        {
            xPos += ddx;
            yPos -= ddy;
        }


        if(dRotation < 0 && rotation == 0)
        {
            rotation = 360;
        }

        if(dRotation > 0 && rotation == 360)
        {
            rotation = 0;
        }

        rotation += dRotation;


        // TODO update winkel in quadrants


        for(Beam b : projectiles)
        {
            b.move();
        }
    }

    private void shoot()
    {
        // TODO calc dx and dy for missle

        double dx = 5;
        double dy = 1;

        int x = (int) xPos+(getWidth() / 2);
        int y = (int) yPos;

        projectiles.add(new Beam(playerID,"beam.png", BEAM_WIDTH, BEAM_HEIGHT,  x, y, dx, dy));

        //this.mp.play();
    }

    public double getRotation()
    {
        return Math.toRadians(this.rotation);
    }

    public List<Beam> getProjectiles()
    {
        return this.projectiles;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void reduceGamePoints(int amount)
    {
        gamePoints -= amount;
    }

    public void addGamePoints(int amount) {
        gamePoints += amount;

        System.out.println("players points: "+gamePoints); 
    }

    public void keyPressed(KeyEvent e) {

        int keyCode = e.getKeyCode();

        switch( keyCode ) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                dMovement = velocity;
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                dMovement = -velocity;
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                dRotation = -2;
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT :
                dRotation = 2;
                break;
            case KeyEvent.VK_SPACE:
                shoot();
                break;

        }
    }

    public void keyReleased(KeyEvent e) {

        int keyCode = e.getKeyCode();

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