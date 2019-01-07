package main.sprites.type;

import javax.swing.*;
import java.awt.*;

public abstract class MovingObject {

    private Image image;

    private int w;
    private int h;

    // moving object has two y Pos, which are randomly generated, to simulate a non straight moving object
    protected double xPos;
    protected double yPos;

    private int energy;

    //private int yPosStart;
    //private int yPosEnd;

    //private double velocity; // errechnet sich aus w & h des objects

    //private double m;


    public MovingObject(String imgFileName, int imageWidth, int imageHeight, int xPos, int yPos, int energy)
    {
        this.w = imageWidth;
        this.h = imageHeight;

        // objects always start on right side of screen
        this.xPos = xPos;
        this.yPos = yPos;

        //this.m = m;
        //this.velocity = velocity;

        //this.yPosStart = yPosStart;
        //this.yPosEnd = yPosEnd;

        this.energy = energy;

        loadImage(imgFileName);
    }

    private void loadImage(String imgFileName) {

        ImageIcon ii = new ImageIcon("src/main/resources/img/"+imgFileName);

        image = ii.getImage();

        image = image.getScaledInstance(w,h, 0);
    }

    public void move() { }


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

    public void addEnergy(int amount)
    {
        this.energy += amount;
    }

    public void reduceEnergy(int amount)
    {
        System.out.println("energy: "+ energy+ " - " + amount);
        this.energy -= amount;
    }

    public int getEnergy() {
        return energy;
    }

    // for collision detection
    public Rectangle getBounds() {
        return new Rectangle((int) xPos, (int) yPos, w, h);
    }

}
