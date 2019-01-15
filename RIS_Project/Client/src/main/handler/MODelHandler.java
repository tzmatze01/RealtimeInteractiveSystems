package main.handler;

import main.game.World;
import main.game.sprites.Meteorite;
import main.game.sprites.Player;
import main.messages.MODelMessage;
import main.messages.MONewMessage;
import main.messages.type.Message;
import main.messages.type.MessageType;

public class MODelHandler<T extends Message> extends NetworkMessageHandler<T> {


    private World world;

    public MODelHandler(World world) {
        this.world = world;
    }

    @Override
    protected void handleMessage(T message) {
        MODelMessage m = (MODelMessage) message;

        //System.out.println("Delete object "+m.getObjectType().toString()+" with id "+m.getObjectID());

        switch (m.getObjectType())
        {
            case METEORITE:
                world.removeMeteorite(m.getObjectID());
                break;
            case ENEMY_BEAM:
                world.removeEnemyBeam(m.getOwnerID(), m.getObjectID());

                // TODO possibly faulty
                //if(m.madeDamage())
                //    world.addPlayerDamage(m.getShooterID(), m.getAddedPlayerDamage());

                break;
            case PLAYER_BEAM:
                world.removePlayerBeam(m.getOwnerID(), m.getObjectID());

                if(m.madePoints())
                    world.addPlayerPoints(m.getShooterID(), m.getAddedPlayerPoints());
                break;
            case ENEMY:
                world.removeEnemy(m.getObjectID());
                break;
            case COLLECTABLE:
                world.removeCollectable(m.getObjectID());

                if(m.madePoints())
                    world.addPlayerPoints(m.getShooterID(), m.getAddedPlayerPoints());
                break;
            case PLAYER:
                world.removePlayer(m.getObjectID());
                break;
        }
    }

    @Override
    public MessageType getHandledMessageType() {
        return MessageType.DEL_MOVING_OBJECT;
    }
}
