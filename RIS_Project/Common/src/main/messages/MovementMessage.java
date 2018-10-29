package main.messages;

import main.messages.type.MessageType;

public class MovementMessage extends Message {

    public MovementMessage() {
        super(MessageType.MOVEMENT);
    }
}
