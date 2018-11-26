package main.sprites;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Player {

    private int dMovement;
    private int dRotation;

    private int dx;
    private int dy;

    // player pos on screen
    private int x = 20;
    private int y = 10;

    // image sizes
    private int w;
    private int h;

    private Image image;

    private int rotation;
    private int oldRotation;


    public Player() {

        this.rotation = 0;
        this.oldRotation = 0;

        loadImage();
    }

    private void loadImage() {

        ImageIcon ii = new ImageIcon("src/main/resources/player2.png");
        image = ii.getImage();

        image = image.getScaledInstance(80,40, 0);

        w = image.getWidth(null);
        h = image.getHeight(null);
    }

    public void move() {

        System.out.println("rotation:"+rotation);


        // TODO geht nur mit geraden dRotation
        if(dRotation < 0 && rotation == 0)
        {
            rotation = 360;
        }

        if(dRotation > 0 && rotation == 360)
        {
            rotation = 0;
        }

        if(rotation != oldRotation)
        {

        }

        rotation += dRotation;

        double ddx = Math.abs(Math.cos(rotation) * dMovement);
        double ddy = Math.abs(Math.sin(rotation) * dMovement);

        System.out.println("ddx: "+ddx+" ddy: "+ddy);

        // TODO oben links ist 0,0 check degrees
        if(rotation >= 0 && rotation < 90)
        {
            x += ddx;
            y += ddy;
        }
        if(rotation >= 90 && rotation < 180)
        {
            x -= ddx;
            y += ddy;
        }
        if(rotation >= 180 && rotation < 270)
        {
            x -= ddx;
            y -= ddy;
        }
        if(rotation >= 270 && rotation < 360)
        {
            x += ddx;
            y -= ddy; 
        }
    }

    public double getRotation()
    {
        return Math.toRadians(this.rotation);
    }

    public int getX() {

        return x;
    }

    public int getY() {

        return y;
    }

    public int getWidth() {

        return w;
    }

    public int getHeight() {

        return h;
    }

    public Image getImage() {

        return image;
    }

    public void keyPressed(KeyEvent e) {

        int keyCode = e.getKeyCode();

        switch( keyCode ) {
            case KeyEvent.VK_UP:
                dMovement = 2;
                //dRotation = -2;
                break;
            case KeyEvent.VK_DOWN:
                dMovement = -2;
                //dRotation = 2;
                break;
            case KeyEvent.VK_LEFT:
                dRotation = -2;
                //dMovement = -2;
                break;
            case KeyEvent.VK_RIGHT :
                dRotation = 2;
                //dMovement = 2;
                break;
        }
    }

    public void keyReleased(KeyEvent e) {

        int keyCode = e.getKeyCode();

        switch( keyCode ) {
            case KeyEvent.VK_UP:
                dMovement = 0;
                break;
            case KeyEvent.VK_DOWN:
                dMovement = 0;
                break;
            case KeyEvent.VK_LEFT:
                dRotation = 0;
                break;
            case KeyEvent.VK_RIGHT :
                dRotation = 0;
                break;
        }
    }
}