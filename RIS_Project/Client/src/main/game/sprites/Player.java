package main.game.sprites;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import main.game.sprites.type.ObjectType;
import main.game.sprites.type.MovingObject;


public class Player extends MovingObject {

    private double rotation;
    private int velocity;

    private Map<Integer, Beam> projectiles;

    private MediaPlayer mp;


    public Player(int playerID, String imgFileName, int imageWidth, int imageHeight, int xPos, int yPos, int energy) {
        super(playerID, ObjectType.PLAYER, imgFileName, imageWidth, imageHeight, xPos, yPos, energy);

        //this.playerID = playerID;

        this.rotation = 0;

        this.projectiles = new HashMap<>();
    }

    private void loadSound(String filename) {

        Media sound = new Media(new File("src/main/resources/sound/"+filename).toURI().toString());
        this.mp = new MediaPlayer(sound);
    }

    public void move() {

        // TODO add projectiles to self when spacebar, don't need to send back, only to other player

        for(Beam b : projectiles.values())
        {
            b.move();
        }
    }

    public void addBeam(Beam beam)
    {
        projectiles.put(beam.getId(), beam);
    }

    public void removeBeam(int beamID)
    {
        projectiles.remove(beamID);
    }
    public void setdRotation(double radians)
    {
        this.rotation = Math.toDegrees(radians);
    }
    public double getRotation()
    {
        return Math.toRadians(this.rotation);
    }

    public List<Beam> getProjectiles()
    {
        return new ArrayList<>(projectiles.values());
    }

    public void reduceGamePoints(int amount)
    {
        gamePoints -= amount;
    }

    public void addGamePoints(int amount) {
        gamePoints += amount;

        System.out.println("players points: "+gamePoints);
    }

}