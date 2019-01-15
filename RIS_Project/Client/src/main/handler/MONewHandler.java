package main.handler;

import main.game.World;
import main.game.sprites.*;
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

        System.out.println("got new: "+m.getObjectType().toString()+" id: "+m.getObjectID());

        switch (m.getObjectType())
        {
            case METEORITE:
                world.addMeteorite(new Meteorite(m.getObjectID(), m.getFilename(), m.getImgWidth(), m.getImgHeight(), m.getxPos(), m.getyPos(), m.getyPosEnd(), m.getM(), m.getVelocity(), m.getEnergy()));
                break;
            case ENEMY_BEAM:
                world.addEnemyBeam(m.getShooterID(), new Beam(m.getObjectID(), m.getObjectType(), m.getShooterID(), m.getFilename(), m.getImgWidth(), m.getImgHeight(),  m.getxPos(), m.getyPos(), m.getDx(), m.getDy(), m.getVelocity()));
                break;
            case PLAYER_BEAM:
                world.addPlayerBeam(m.getShooterID(), new Beam(m.getObjectID(), m.getObjectType(), m.getShooterID(), m.getFilename(), m.getImgWidth(), m.getImgHeight(),  m.getxPos(), m.getyPos(), m.getDx(), m.getDy(), m.getVelocity()));
                break;
            case ENEMY:
                world.addEnemy(new Enemy(m.getObjectID(), m.getFilename(), m.getImgWidth(), m.getImgHeight(), m.getxPos(), m.getyPos(), m.getEnergy()));
                break;
            case COLLECTABLE:
                world.addCollectable(new Collectable(m.getObjectID(), m.getFilename(), m.getImgWidth(), m.getImgHeight(), m.getxPos(), m.getyPos(), m.getyPosEnd(), m.getM(), m.getVelocity(), m.getEnergy()));
                break;
            case PLAYER:
                world.addPlayer(new Player(m.getObjectID(), m.getFilename(), m.getImgWidth(), m.getImgHeight(), m.getxPos(), m.getyPos(), m.getEnergy()));
                break;
        }

    }

    @Override
    public MessageType getHandledMessageType() {
        return MessageType.NEW_MOVING_OBJECT;
    }
}
