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

    private int velocity;

    public Player(int velocity) {

        this.rotation = 0;
        this.oldRotation = 0;

        this.velocity = velocity;

        loadImage();
    }

    private void loadImage() {

        ImageIcon ii = new ImageIcon("src/main/resources/player.png");
        image = ii.getImage();

        image = image.getScaledInstance(80,40, 0);

        w = image.getWidth(null);
        h = image.getHeight(null);
    }

    public void move() {

        //System.out.println("rotation:"+rotation);

        double ddx = Math.abs(Math.cos(rotation) * dMovement);
        double ddy = Math.abs(Math.sin(rotation) * dMovement);


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


        // TODO geht nur mit geraden dRotation
        if(dRotation < 0 && rotation == 0)
        {
            rotation = 360;
        }

        if(dRotation > 0 && rotation == 360)
        {
            rotation = 0;
        }



        rotation += dRotation;


       // System.out.println("ddx: "+ddx+" ddy: "+ddy);

        // TODO update winkel in quadrants

        // TODO oben links ist 0,0 check degrees

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
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                dMovement = velocity;
                //dRotation = -2;
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                dMovement = -velocity;
                //dRotation = 2;
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                dRotation = -2;
                //dMovement = -2;
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT :
                dRotation = 2;
                //dMovement = 2;
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