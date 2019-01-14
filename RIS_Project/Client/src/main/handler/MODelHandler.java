package main.handler;

import main.game.World;
import main.messages.type.Message;
import main.messages.type.MessageType;

public class MODelHandler<T extends Message> extends NetworkMessageHandler<T> {


    private World world;

    public MODelHandler(World world) {
        this.world = world;
    }

    @Override
    protected void handleMessage(T message) {

    }

    @Override
    public MessageType getHandledMessageType() {
        return MessageType.DEL_MOVING_OBJECT;
    }
}
