package main.sprites;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Player {

    private int dx;
    private int dy;
    private int x = 40;
    private int y = 60;
    private int w;
    private int h;
    private Image image;

    public Player() {

        loadImage();
    }

    private void loadImage() {

        ImageIcon ii = new ImageIcon("src/main/resources/player2.png");
        image = ii.getImage();

        image = image.getScaledInstance(50,80, 0);

        w = image.getWidth(null);
        h = image.getHeight(null);
    }

    public void move() {

        System.out.println("move player dx: "+x+" dy:"+y);
        x += dx;
        y += dy;
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
                dy = -2;
                break;
            case KeyEvent.VK_DOWN:
                dy = 2;
                break;
            case KeyEvent.VK_LEFT:
                dx = -2;
                break;
            case KeyEvent.VK_RIGHT :
                dx = 2;
                break;
        }
    }

    public void keyReleased(KeyEvent e) {

        int keyCode = e.getKeyCode();

        switch( keyCode ) {
            case KeyEvent.VK_UP:
                dy = 0;
                break;
            case KeyEvent.VK_DOWN:
                dy = 0;
                break;
            case KeyEvent.VK_LEFT:
                dx = 0;
                break;
            case KeyEvent.VK_RIGHT :
                dx = 0;
                break;
        }
    }
}