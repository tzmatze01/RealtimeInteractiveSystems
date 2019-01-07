package main.sprites;

import main.sprites.type.MovingObject;

import javax.swing.*;
import java.awt.*;

public class Meteorite extends MovingObject {

    /*
    private String imgFileName;
    private Image image;

    private int w;
    private int h;

    // moving object has two y Pos, which are randomly generated, to simulate a non straight moving object
    private double xPos;
    private double yPos;

    private int yPosStart;
    private int yPosEnd;

    private double velocity; // errechnet sich aus w & h des objects

    private double m;
    private int energy;
    */
    private int yPosStart;
    private int yPosEnd;

    private double velocity; // errechnet sich aus w & h des objects

    private double m;

    public Meteorite(String imgFileName, int imageWidth, int imageHeight, int xPos, int yPosStart, int yPosEnd, double m, double velocity, int energy) {
        super(imgFileName, imageWidth, imageHeight, xPos, yPosStart, energy);

        this.yPosStart = yPosStart;
        this.yPosEnd = yPosEnd;
        this.m = m;
        this.velocity = velocity;
    }

    /*
    public Meteorite(String imgFileName, int imageWidth, int imageHeight, int xPos, int yPosStart, int yPosEnd, double m, double velocity)
    {
        this.imgFileName = imgFileName;

        this.w = imageWidth;
        this.h = imageHeight;

        // objects always start on right side of screen
        this.xPos = xPos;
        this.yPos = yPosStart;

        this.m = m;
        this.velocity = velocity;

        this.yPosStart = yPosStart;
        this.yPosEnd = yPosEnd;

        this.energy = 100;

        loadImage();

        // TODO rotate meterite
    }


    private void loadImage() {

        ImageIcon ii = new ImageIcon("src/main/resources/img/"+imgFileName);

        image = ii.getImage();

        image = image.getScaledInstance(w,h, 0);
    }

    public void move()
    {
        // move with size / accelartion
        this.xPos -= velocity;
        this.yPos = (m * this.xPos) + yPosEnd;
    }

    public int getX()
    {
        return (int) this.xPos;
    }

    public int getY()
    {
        return (int) this.yPos;
    }

    public int getWidth()
    {
        return this.w;
    }

    public int getHeight()
    {
        return this.h;
    }

    public Image getImage()
    {
        return this.image;
    }

    public void reduceEnergy(int amount)
    {
        this.energy -= amount;
    }

    public int getEnergy() {
        return energy;
    }

    // for collision detection
    public Rectangle getBounds() {
        return new Rectangle((int) xPos, (int) yPos, w, h);
    }
     */
    public void move()
    {
        // move with size / accelartion
        this.xPos -= velocity;
        this.yPos = (m * this.xPos) + yPosEnd;
    }
}
