package main.handler;

import main.messages.Message;
import main.messages.MovementMessage;
import main.messages.type.MessageType;

import java.awt.event.KeyEvent;

public class MovementHandler<T extends Message> extends NetworkMessageHandler<T> {


    public MovementHandler() {

    }

    @Override
    public void handleMessage(T message) {

    }

    @Override
    public MessageType getHandledMessageType() {
        return MessageType.MOVEMENT;
    }

}
