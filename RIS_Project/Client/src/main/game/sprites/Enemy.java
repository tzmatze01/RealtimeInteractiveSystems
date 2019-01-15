package main.game.sprites;


import main.game.sprites.type.MovingObject;
import main.game.sprites.type.ObjectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Enemy extends MovingObject {


    private double rotation;

    private ConcurrentMap<Integer, Beam> projectiles;

    public Enemy(int id, String imgFileName, int imageWidth, int imageHeight, int xPos, int yPos, int energy) {
        super(id, ObjectType.ENEMY, imgFileName, imageWidth, imageHeight, xPos, yPos, energy);


        this.projectiles = new ConcurrentHashMap<>();
        this.rotation = 0;
    }



    public void move()
    {
        for(Beam b : projectiles.values())
            b.move();
    }

    public void addBeam(Beam beam)
    {
        //System.out.println("add enemy "+beam.getPlayerID()+" beam "+beam.getId());

        projectiles.put(beam.getId(), beam);

        //System.out.print("projectiles size: "+projectiles.size());
    }

    public void removeBeam(int beamID)
    {
        projectiles.remove(beamID);
    }

    public double getRotation() {
        return Math.toRadians(rotation);
    }

    public void setRotation(double radians) {
        this.rotation = Math.toDegrees(radians);
    }

    public List<Beam> getProjectiles()
    {
        return new ArrayList<Beam>(projectiles.values());
    }

}
