package main.game.sprites;

import main.game.sprites.type.ObjectType;
import main.game.sprites.type.MovingObject;

public class Collectable extends MovingObject {

    private int yPosEnd;
    private double velocity; // errechnet sich aus w & h des objects
    private double m;


    public Collectable(int id, String imgFileName, int imageWidth, int imageHeight, int xPos, int yPos, int yPosEnd, double m, double velocity, int energy, int gamePoints) {


        super(id, ObjectType.COLLECTABLE, imgFileName, imageWidth, imageHeight, xPos, yPos, energy, gamePoints);

        this.yPosEnd = yPosEnd;
        this.velocity = velocity;
        this.m = m;
    }


    public void move()
    {
        // move with size / accelartion
        this.xPos -= velocity;
        this.yPos = (m * this.xPos) + yPosEnd;
    }
}
