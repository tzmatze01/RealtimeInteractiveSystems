package main.game;

import main.sprites.MovingObject;
import main.sprites.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class World extends JPanel implements KeyListener {


    private MovingObject mo;
    private Player player;

    private Timer timer;
    private final int DELAY = 10;

    private KeyLis listener;

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

    private class KeyLis extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    System.out.println("VK_LEFT pressed");
                    break;
                case KeyEvent.VK_RIGHT:
                    System.out.println("VK_RIGHT pressed");
                    break;
            }
        }
    }

    private void initBoard() {
        setBackground(Color.black);

        player = new Player();

        //timer = new Timer(DELAY, this);
        //timer.start();
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);

        Toolkit.getDefaultToolkit().sync();
    }


    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        /*
        move the sprite and repaint the part of the board that has changed. We use a small optimisation technique
        that repaints only the small area of the window that actually changed.
         */

        g2d.drawImage(player.getImage(), player.getX(),
                player.getY(), this);
    }

    /*
    @Override
    public void actionPerformed(ActionEvent e) {

        step();
    }

    private void step() {

        player.move();

        repaint(player.getX()-1, player.getY()-1,
                player.getWidth()+2, player.getHeight()+2);
    }
    */

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("world key typed");


    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("world key pressed");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("world key released");
    }
      /*
    public class CustomKeyListener extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            System.out.println("world key pressed");
            player.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println("world key pressed");
            player.keyPressed(e);
        }
    }
      */
}
