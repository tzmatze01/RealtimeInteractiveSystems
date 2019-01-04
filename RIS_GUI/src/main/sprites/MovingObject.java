package main.sprites;

import javax.swing.*;
import java.awt.*;

public class MovingObject {

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

    public MovingObject(String imgFileName, int imageWidth, int imageHeight, int xPos, int yPosStart, int yPosEnd, double m, double velocity)
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


        loadImage();

        // TODO rotate meterite
    }

    private void loadImage() {

        ImageIcon ii = new ImageIcon("src/main/resources/"+imgFileName);

        image = ii.getImage();

        image = image.getScaledInstance(w,h, 0);
    }

    public void move()
    {
        // move with size / accelartion
        this.xPos -= velocity;
        this.yPos = (m * this.xPos) + yPosEnd;


        //System.out.println("x: "+xPos+ " y: "+yPos+ "m: "+m+" velo: "+velocity);
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

    // for collision detection
    public Rectangle getBounds() {
        return new Rectangle((int) xPos, (int) yPos, w, h);
    }

}
