package main.messages;

import main.game.sprites.type.ObjectType;
import main.messages.type.Message;
import main.messages.type.MessageType;

public class InfoMessage extends Message {

    private ObjectType objectType;
    private int gamePoints;
    private int damage;

    private boolean gameOver;

    public InfoMessage(int userID, ObjectType type, int gamePoints, int damage, boolean gameOver) {
        super(MessageType.INFO_MESSAGE, userID);

        this.objectType = type;
        this.gamePoints = gamePoints;
        this.damage = damage;
        this.gameOver = gameOver;
    }

    public boolean isDamageMessage()
    {
        return damage > 0;
    }

    public boolean isGamePointsMessage()
    {
        return gamePoints > 0;
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public int getGamePoints() {
        return gamePoints;
    }

    public int getDamage() {
        return damage;
    }

    public boolean isGameOver() {
        return gameOver;
    }
}
