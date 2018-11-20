package main.handler;

import main.messages.Message;
import main.messages.MovementMessage;
import main.messages.type.MessageType;

public class MovementHandler<T extends Message> extends NetworkMessageHandler<T> {


    public MovementHandler() {

    }

    @Override
    public void handleMessage(T message) {

        System.out.println("Movement Handler: "+message.getType());
    }

    @Override
    public MessageType getHandledMessageType() {
        return MessageType.MOVEMENT;
    }


    //typ der verabeitenden nachricht
    // diese nehmen nachrichten an und versenden sie an NetMsgHandler
}
