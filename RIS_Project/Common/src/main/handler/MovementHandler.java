package main.handler;

import main.messages.Message;
import main.messages.MovementMessage;

public class MovementHandler<T extends Message> extends NetworkMessageHandler<T> {

    @Override
    public void handle(T message) {


        run();
    }

    @Override
    public void run() {

    }

    //typ der verabeitenden nachricht
    // diese nehmen nachrichten an und versenden sie an NetMsgHandler
}
