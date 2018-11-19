package main.handler;

import main.messages.Message;
import main.messages.MovementMessage;

public class MovementHandler<T extends Message> extends NetworkMessageHandler<T> {


    @Override
    public void handleMessage(T message) {

        System.out.println("Movement Handler: "+message.getType());
    }


    //typ der verabeitenden nachricht
    // diese nehmen nachrichten an und versenden sie an NetMsgHandler
}
