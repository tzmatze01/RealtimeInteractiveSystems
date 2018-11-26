package main.game;

import main.sprites.MovingObject;
import main.sprites.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class World extends JPanel implements KeyListener, ActionListener {


    private MovingObject mo;
    private Player player;

    private Timer timer;
    private final int DELAY = 10;


    public World()
    {
        initBoard();
        //grabFocus();


        /*
        add(new JButton("Foo")); // something to draw off focus
        listener = new KeyLis();
        this.setFocusable(true);
        this.requestFocus();
        this.addKeyListener(listener);
        */
    }

    private void initBoard() {
        setBackground(Color.black);

        player = new Player();

        timer = new Timer(DELAY, this);
        timer.start();
        timer.start();
    }


    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        doDrawing(g);

        Toolkit.getDefaultToolkit().sync();
    }


    private void doDrawing(Graphics g) {

        System.out.println("doDrawing");

        Graphics2D g2d = (Graphics2D) g;

        /*
        move the sprite and repaint the part of the board that has changed. We use a small optimisation technique
        that repaints only the small area of the window that actually changed.
        */

        double imgW = player.getX() + (player.getWidth() / 2);
        double imgH = player.getY() + (player.getHeight() / 2);

        g2d.rotate(player.getRotation(), imgW, imgH);

        g2d.drawImage(player.getImage(), player.getX(),
                player.getY(), this);


    }


    @Override
    public void actionPerformed(ActionEvent e) {

        step();
    }

    private void step() {

        player.move();

        //repaint(player.getX()-1, player.getY()-1,player.getWidth()+2, player.getHeight()+2);

        repaint();
    }


    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {

        player.keyPressed(e);
        /*
        int keyCode = e.getKeyCode();
        switch( keyCode ) {
            case KeyEvent.VK_UP:
                System.out.println("up");
                break;
            case KeyEvent.VK_DOWN:
                System.out.println("down");
                break;
            case KeyEvent.VK_LEFT:
                System.out.println("left");
                break;
            case KeyEvent.VK_RIGHT :
                System.out.println("right");
                break;
        }
        */
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //System.out.println("world key released");
        player.keyReleased(e);
    }
}
