package main.sprites;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import main.sprites.type.MovingObject;

public class Beam extends MovingObject {

    private double dx;
    private double dy;

    private boolean hitObject;

    //
    //    public Meteorite(String imgFileName, int imageWidth, int imageHeight, int xPos, int yPosStart, int yPosEnd, double m, double velocity, int energy)
    public Beam(String imgFileName, int imageWidth, int imageHeight, int xPos, int yPos, double dx, double dy)
    {
        super(imgFileName, imageWidth, imageHeight, xPos, yPos, 10);

        this.dx = dx;
        this.dy = dy;

        this.hitObject = false;

    }

    public boolean isHitObject() {
        return hitObject;
    }

    public void setHitObject(boolean hitObject) {
        this.hitObject = hitObject;
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


}
