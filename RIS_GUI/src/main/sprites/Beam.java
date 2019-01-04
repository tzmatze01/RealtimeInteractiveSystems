package main.sprites;

import javax.swing.*;
import java.awt.*;

public class Beam {

    // player pos on screen
    private double x;
    private double y;

    private double dx;
    private double dy;

    // image sizes
    private int w;
    private int h;

    private Image image;


    public Beam(int w, int h, int xPos, int yPos, double dx, double dy, String imgName)
    {
        this.x = xPos;
        this.y = yPos;

        this.w = w;
        this.h = h;

        this.dx = dx;
        this.dy = dy;

        loadImage(imgName);
    }


    private void loadImage(String imgName) {

        ImageIcon ii = new ImageIcon("src/main/resources/"+imgName);
        image = ii.getImage();

        image = image.getScaledInstance(w,h, 0);

        w = image.getWidth(null);
        h = image.getHeight(null);
    }


    public Image getImage() {

        return image;
    }

    public void move()
    {
        x += dx;
        //y += dy;
    }

    public int getX()
    {
        return (int) this.x;
    }

    public int getY()
    {
        return (int) this.y;
    }

    public int getHeight()
    {
        return this.h;
    }

    public int getWidth()
    {
        return this.w;
    }

    // for collision detection
    public Rectangle getBounds() {
        return new Rectangle( getX(), getY(), w, h);
    }

}
