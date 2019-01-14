package main.game.sprites;

import main.game.sprites.type.MovingObject;
import main.game.sprites.type.ObjectType;

public class Beam extends MovingObject {

    private int playerID;

    private double dx;
    private double dy;

    private double velocity;


    public Beam(int id, ObjectType type, int playerID, String imgFileName, int imageWidth, int imageHeight, int xPos, int yPos, double dx, double dy, double velocity)
    {
        super(id, type, imgFileName, imageWidth, imageHeight, xPos, yPos, 10);

        this.playerID = playerID;

        this.dx = dx;
        this.dy = dy;

        this.velocity = velocity;
    }


    public void move()
    {
        // TODO

        //
        //    public Meteorite(String imgFileName, int imageWidth, int imageHeight, int xPos, int yPosStart, int yPosEnd, double m, double velocity, int energy)

        // move with size / accelartion
        //this.xPos -= velocity;
        //this.yPos = (m * this.xPos) + yPosEnd;

        this.xPos += dx * velocity;
        this.yPos += dy * velocity;
    }


    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }
}
