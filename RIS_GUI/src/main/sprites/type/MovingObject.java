package main.sprites.type;

import javax.swing.*;
import java.awt.*;
import java.io.ObjectOutputStream;

public abstract class MovingObject {

    private ObjectType type;
    private Image image;

    private int w;
    private int h;

    // moving object has two y Pos, which are randomly generated, to simulate a non straight moving object
    protected double xPos;
    protected double yPos;

    private int energy;

    private boolean toDelete;
    protected int gamePoints;

    //private int yPosStart;
    //private int yPosEnd;

    //private double velocity; // errechnet sich aus w & h des objects

    //private double m;


    public MovingObject(ObjectType type, String imgFileName, int imageWidth, int imageHeight, int xPos, int yPos, int energy, int gamePoints)
    {
        this.type = type;

        this.w = imageWidth;
        this.h = imageHeight;

        // objects always start on right side of screen
        this.xPos = xPos;
        this.yPos = yPos;

        this.energy = energy;

        this.toDelete = false;

        this.gamePoints = gamePoints;

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
        this.energy -= amount;
    }

    public int getEnergy() {
        return energy;
    }

    public ObjectType getType() {
        return type;
    }

    public int getGamePoints() {
        return gamePoints;
    }

    public boolean isToDelete() {
        return toDelete;
    }

    public void setToDelete(boolean toDelete) {
        this.toDelete = toDelete;
    }

    // for collision detection
    public Rectangle getRectangleBounds() {

        // TODO http://zetcode.com/tutorials/javagamestutorial/collision/

        // TODO with intersects https://docs.oracle.com/javase/7/docs/api/java/awt/Polygon.html

        Rectangle rect = new Rectangle( 1,1,1,1);
        return new Rectangle((int) xPos - getWidth() / 2, (int) yPos - getHeight() / 2, w, h);
    }

    public Polygon getPolygonBounds() {

        return null;
    }

}
