package main.messages;

import main.game.sprites.type.ObjectType;
import main.messages.type.KeyEventType;
import main.messages.type.MessageType;

import java.awt.event.KeyEvent;
import java.io.Serializable;

public class MovementMessage extends Message {

    private int xPos;
    private int yPos;

    private ObjectType objectType;

    public MovementMessage(int xPos, int yPos, ObjectType objectType)
    {
        super(MessageType.MOVEMENT);

        this.xPos = xPos;
        this.yPos = yPos;
        this.objectType = objectType;
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public void setObjectType(ObjectType objectType) {
        this.objectType = objectType;
    }
}
