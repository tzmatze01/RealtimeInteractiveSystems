package main.messages;

import main.game.sprites.type.ObjectType;
import main.messages.type.Message;
import main.messages.type.MessageType;


public class MODelMessage extends Message {

    private ObjectType objectType;
    private int objectID;

    // if meteorite, beam, enemy or collectable -> enmies get damage and/or points
    private int shooterID;
    private int addedPlayerDamage;
    private int addedPlayerPoints;

    // constructor for player, collectable, enemy, meteorite out of screen
    public MODelMessage(ObjectType objectType, int objectID) {
        super(MessageType.DEL_MOVING_OBJECT, 0);

        this.objectType = objectType;
        this.objectID = objectID;

        this.shooterID = 0;
        this.addedPlayerDamage = 0;
        this.addedPlayerPoints = 0;
    }

    // constructor for player - & enemy beam out of screen
    public MODelMessage(ObjectType objectType, int objectID, int shooterID) {
        super(MessageType.DEL_MOVING_OBJECT, 0);

        this.objectType = objectType;
        this.objectID = objectID;

        this.shooterID = shooterID;
        this.addedPlayerDamage = 0;
        this.addedPlayerPoints = 0;
    }

    // constructor for meteorites, collectables, enemies with effect
    public MODelMessage(ObjectType objectType, int objectID, int addedPlayerDamage, int addedPlayerPoints) {
        super(MessageType.DEL_MOVING_OBJECT, 0);

        this.objectType = objectType;
        this.objectID = objectID;
        this.shooterID = 0;
        this.addedPlayerDamage = addedPlayerDamage;
        this.addedPlayerPoints = addedPlayerPoints;
    }

    // constructor for beams with effect
    public MODelMessage(ObjectType objectType, int objectID, int shooterID, int addedPlayerDamage, int addedPlayerPoints) {
        super(MessageType.DEL_MOVING_OBJECT, 0);

        this.objectType = objectType;
        this.objectID = objectID;
        this.shooterID = shooterID;
        this.addedPlayerDamage = addedPlayerDamage;
        this.addedPlayerPoints = addedPlayerPoints;
    }

    public boolean madeDamage()
    {
        return addedPlayerDamage > 0;
    }

    public boolean madePoints()
    {
        return addedPlayerPoints > 0;
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public int getObjectID() {
        return objectID;
    }

    public int getShooterID() {
        return shooterID;
    }

    public int getAddedPlayerDamage() {
        return addedPlayerDamage;
    }

    public int getAddedPlayerPoints() {
        return addedPlayerPoints;
    }
}
