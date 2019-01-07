package main.sprites;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import main.sprites.type.MovingObject;
import main.sprites.type.ObjectType;

public class Beam extends MovingObject {

    private int playerID;

    private double dx;
    private double dy;


    //
    //    public Meteorite(String imgFileName, int imageWidth, int imageHeight, int xPos, int yPosStart, int yPosEnd, double m, double velocity, int energy)
    public Beam(int playerID, String imgFileName, int imageWidth, int imageHeight, int xPos, int yPos, double dx, double dy)
    {
        super(ObjectType.PLAYER_BEAM, imgFileName, imageWidth, imageHeight, xPos, yPos, 10);

        this.playerID = playerID;

        this.dx = dx;
        this.dy = dy;

    }


    public void move()
    {
        // TODO

        //
        //    public Meteorite(String imgFileName, int imageWidth, int imageHeight, int xPos, int yPosStart, int yPosEnd, double m, double velocity, int energy)

        // move with size / accelartion
        //this.xPos -= velocity;
        //this.yPos = (m * this.xPos) + yPosEnd;

        this.xPos += dx;
    }


    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }
}
