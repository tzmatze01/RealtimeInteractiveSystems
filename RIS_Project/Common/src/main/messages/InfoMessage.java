package main.messages;

import main.game.sprites.type.ObjectType;
import main.messages.type.Message;
import main.messages.type.MessageType;

public class InfoMessage extends Message {

    private int gamePoints;
    private int damage;

    private boolean gameOver;
    private boolean winGame;

    private int level;

    // message for damage or points
    public InfoMessage(int userID, int gamePoints, int damage) {
        super(MessageType.INFO_MESSAGE, userID);

        this.gamePoints = gamePoints;
        this.damage = damage;

        this.gameOver = false;
        this.winGame = false;
        this.level = 0;
    }

    // message if player won or lost game
    public InfoMessage(int userID, boolean gameOver, boolean winGame)
    {
        super(MessageType.INFO_MESSAGE, userID);

        this.gamePoints = 0;
        this.damage = 0;

        this.gameOver = gameOver;
        this.winGame = winGame;

        this.level = 0;
    }

    // message for new level
    public InfoMessage(int level)
    {
        super(MessageType.INFO_MESSAGE, 0);

        this.gamePoints = 0;
        this.damage = 0;

        this.gameOver = false;
        this.winGame = false;

        this.level = level;
    }

    public boolean isDamageMessage()
    {
        return damage > 0;
    }

    public boolean isGamePointsMessage()
    {
        return gamePoints > 0;
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

    public boolean isWinGame() {
        return winGame;
    }

    public int getLevel() {
        return level;
    }
}
