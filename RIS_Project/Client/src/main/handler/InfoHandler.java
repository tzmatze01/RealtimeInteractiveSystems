package main.handler;

import main.game.World;
import main.messages.InfoMessage;
import main.messages.type.Message;
import main.messages.type.MessageType;

public class InfoHandler<T extends Message> extends NetworkMessageHandler<T> {


    private World world;

    public InfoHandler(World world) {
        this.world = world;
    }

    @Override
    protected void handleMessage(T message) {

        InfoMessage m = (InfoMessage)message;

        switch (m.getObjectType())
        {
            case PLAYER:
                if(m.isDamageMessage())
                    world.addPlayerDamage(m.getUserID(), m.getDamage());
                else if(m.isGamePointsMessage())
                    world.addPlayerPoints(m.getUserID(), m.getGamePoints());
                break;
            case OTHER:

                break;
        }
    }

    @Override
    public MessageType getHandledMessageType() {
        return null;
    }
}
