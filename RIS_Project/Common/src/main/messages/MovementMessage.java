package main.messages;

import main.messages.type.MessageType;

public class MovementMessage implements Message {

    private int x;
    private int y;

    public MovementMessage(int x, int y)
    {
        //super(MessageType.MOVEMENT);

        this.x = x;
        this.y = y;

    }

    public int getX()
    {
        return this.x;
    }

    public int getY()
    {
        return this.y;
    }


    @Override
    public MessageType getType() {

        return MessageType.MOVEMENT;
    }
}
