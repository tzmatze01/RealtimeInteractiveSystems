package main.messages;

import main.messages.type.MessageType;

public class NewMovObjMessage extends Message {

    private int objectID;
    private int xPos;
    private int yPos;

    private int imgWidth;
    private int imgHeight;

    public NewMovObjMessage()
    {
        super(MessageType.NEW_MOVING_OBJECT, 0);
    }
}
