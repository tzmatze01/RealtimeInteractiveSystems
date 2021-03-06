package main.handler;

import main.game.World;
import main.messages.MOMovMessage;
import main.messages.type.Message;
import main.messages.type.MessageType;

public class MOMovHandler<T extends Message> extends NetworkMessageHandler<T> {

    private World world;

    public MOMovHandler(World world) {
        this.world = world;
    }

    @Override
    protected void handleMessage(T message) {

        MOMovMessage m = (MOMovMessage)message;

        switch (m.getObjectType())
        {
            case PLAYER:
                world.setPlayerPos(m.getObjectID(), m.getxPos(), m.getyPos(), m.getRotation());
                break;
            case ENEMY:
                world.setEnemyPos(m.getObjectID(), m.getxPos(), m.getyPos(), m.getRotation());
        }
    }

    @Override
    public MessageType getHandledMessageType() {
        return MessageType.MOV_MOVING_OBJECT;
    }
}
