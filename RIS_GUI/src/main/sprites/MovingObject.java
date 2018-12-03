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

    private double dX;
    private double dY;


    public MovingObject(String imgFileName, int imageWidth, int imageHeight, int xPos, int yPos, double dX, double dY, double velocity)
    {
        this.imgFileName = imgFileName;

        this.w = imageWidth;
        this.h = imageHeight;

        // objects always start on right side of screen
        this.xPos = xPos;
        this.yPos = yPos;

        this.velocity = velocity;

        this.yPosStart = yPosStart;
        this.yPosEnd = yPosEnd;

        this.dX = dX;
        this.dY = dY;

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
        this.xPos += dX * velocity;
        this.yPos += dY * velocity;

        //System.out.println("x: "+xPos+ " y: "+yPos+ "dX: "+dX);
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
}
