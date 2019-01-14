package main.messages;

import main.game.sprites.type.ObjectType;
import main.messages.type.Message;
import main.messages.type.MessageType;

public class MONewMessage extends Message {

    private ObjectType type;
    private int objectID;

    private int xPos;
    private int yPos;

    private int yPosEnd;
    private double m;
    private double velocity;
    private int energy;

    private int imgWidth;
    private int imgHeight;

    private boolean isStaticMO;
    /* TODO myb moving objects by thmselves to reduce network traffic


    p   ublic Beam(int id, ObjectType type, int playerID, String imgFileName, int imageWidth, int imageHeight, int xPos, int yPos, double dx, double dy, double velocity)
public Collectable(int id, String imgFileName, int imageWidth, int imageHeight, int xPos, int yPos, int yPosEnd, double m, double velocity, int energy, int gamePoints) {
  public Meteorite(int id, String imgFileName, int imageWidth, int imageHeight, int xPos, int yPos, int yPosEnd, double m, double velocity, int energy, int gamePoints)
                       -                 -               -              -            -        -         -

     */

    // for player & enemy
    public MONewMessage(ObjectType type, int objectID, int xPos, int yPos, int energy, int imgWidth, int imgHeight)
    {
        super(MessageType.NEW_MOVING_OBJECT, 0);

        this.type = type;
        this.objectID = objectID;
        this.xPos = xPos;
        this.yPos = yPos;
        this.imgWidth = imgWidth;
        this.imgHeight = imgHeight;
        this.energy = energy;
        this.yPosEnd = 0;
        this.m = 0;
        this.velocity = 0;

        this.isStaticMO = false;
    }

    // for all others
    public MONewMessage(ObjectType type, int objectID, int xPos, int yPos, int yPosEnd, double m, double velocity, int energy, int imgWidth, int imgHeight)
    {
        super(MessageType.NEW_MOVING_OBJECT, 0);

        this.type = type;
        this.objectID = objectID;
        this.xPos = xPos;
        this.yPos = yPos;
        this.yPosEnd = yPosEnd;
        this.m = m;
        this.velocity = velocity;
        this.imgWidth = imgWidth;
        this.imgHeight = imgHeight;
        this.energy = energy;

        this.isStaticMO = true;
    }

    public boolean isStaticMO() {
        return isStaticMO;
    }

    public int getObjectID() {
        return objectID;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public int getImgWidth() {
        return imgWidth;
    }

    public int getImgHeight() {
        return imgHeight;
    }

    public int getyPosEnd() {
        return yPosEnd;
    }

    public double getM() {
        return m;
    }

    public double getVelocity() {
        return velocity;
    }

    public int getEnergy() {
        return energy;
    }

    public ObjectType getMOType()
    {
        return this.type;
    }
}
