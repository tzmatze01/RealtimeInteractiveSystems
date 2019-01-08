package main.sprites;

import main.sprites.type.MovingObject;
import main.sprites.type.ObjectType;

import java.awt.*;
import java.util.List;

public class Enemy extends MovingObject {

    private int velocity;
    private int focusPlayer;

    private Point playerPos;

    private List<Beam> projectiles;
    private static int BEAM_WIDTH = 20;
    private static int BEAM_HEIGHT = 8;

    public Enemy(String imgFileName, int imageWidth, int imageHeight, int xPos, int yPos, int energy, int gamePoints, int velocity, int focusPlayer) {
        super(ObjectType.ENEMY, imgFileName, imageWidth, imageHeight, xPos, yPos, energy, gamePoints);

        this.velocity = velocity;
        this.focusPlayer = focusPlayer;
    }


    public void move() {


        // get random player pos and move in this direction
    }

    private void shoot()
    {
        double dx = -1;
        double dy = 0;


        //projectiles.add(new Beam(0, ObjectType.ENEMY_BEAM,"beam.png", BEAM_WIDTH, BEAM_HEIGHT,  getX(), getY(), dx, dy, 5));

        //this.mp.play();
    }

    public int getFocusPlayer() {
        return focusPlayer;
    }

    public void setFocusPlayerPos(int xPos, int yPos) {
        this.playerPos.x = xPos;
        this.playerPos.y = yPos;
    }
}
