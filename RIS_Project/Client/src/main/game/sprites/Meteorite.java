package main.game.sprites;

import main.game.sprites.type.MovingObject;
import main.game.sprites.type.ObjectType;

public class Meteorite extends MovingObject {


    private int yPosStart;
    private int yPosEnd;

    private double velocity; // errechnet sich aus w & h des objects

    private double m;

    public Meteorite(int id, String imgFileName, int imageWidth, int imageHeight, int xPos, int yPosStart, int yPosEnd, double m, double velocity, int energy) {
        super(id, ObjectType.METEORITE, imgFileName, imageWidth, imageHeight, xPos, yPosStart, energy);

        this.yPosStart = yPosStart;
        this.yPosEnd = yPosEnd;
        this.m = m;
        this.velocity = velocity;
    }

    public void move()
    {
        // move with size / accelartion
        this.xPos -= velocity;
        this.yPos = (m * this.xPos) + yPosEnd;
    }
}
