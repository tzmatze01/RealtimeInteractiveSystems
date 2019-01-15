package main.messages;

import main.game.sprites.type.ObjectType;
import main.messages.type.Message;
import main.messages.type.MessageType;

public class MOMovMessage extends Message {

    private int xPos;
    private int yPos;
    private double rotation;

    private ObjectType objectType;
    private int objectID;


    // Movement Messages are only sent for players, enemies - to reduce network traffic
    // v2 also for collectables, meteorites
    public MOMovMessage(int xPos, int yPos, double rotation, ObjectType objectType, int objectID)
    {
        // gets onl send by socketHandler, no userID  needed
        super(MessageType.MOV_MOVING_OBJECT, 0);

        this.xPos = xPos;
        this.yPos = yPos;
        this.rotation = rotation;
        this.objectType = objectType;
        this.objectID = objectID;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public double getRotation() {
        return rotation;
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public int getObjectID() {
        return objectID;
    }
}
