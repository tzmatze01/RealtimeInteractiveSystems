package main.messages;

import main.messages.type.MessageType;

public class NewMovObjMessage extends Message {

    private int objectID;
    private int xPos;
    private int yPos;

    public NewMovObjMessage(MessageType type)
    {
        super(MessageType.NEW_MOVING_OBJECT);
    }
}
