package main.handler;

import main.game.World;
import main.game.sprites.Player;
import main.game.sprites.type.ObjectType;
import main.messages.MONewMessage;
import main.messages.type.Message;
import main.messages.type.MessageType;

public class MONewHandler<T extends Message> extends NetworkMessageHandler<T> {


    private World world;

    public MONewHandler(World world) {
        this.world = world;
    }

    @Override
    protected void handleMessage(T message) {

        MONewMessage m = (MONewMessage)message;

        if(m.getMOType() == ObjectType.PLAYER)
            world.addPlayer(new Player(m.getObjectID(), "player", m.getImgWidth(), m.getImgHeight(), m.getxPos(), m.getyPos(), m.getEnergy()));
    }

    @Override
    public MessageType getHandledMessageType() {
        return MessageType.NEW_MOVING_OBJECT;
    }
}
