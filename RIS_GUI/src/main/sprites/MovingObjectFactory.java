package main.sprites;

import main.sprites.type.MovingObject;
import main.sprites.type.ObjectType;

import java.util.concurrent.ThreadLocalRandom;

/*
public class MovingObjectFactory {

    // TODO need to be changed when screensize in MAIN is changed
    private static int screenWidth = 1200;
    private static int screenHeight = 800;

    // width should be alrger than height, to compute the
    private static  int METEORITE_MIN_WIDTH = 50;
    private static int METORITE_MAX_WIDTH = 250;

    private static int METEORITE_MIN_HEIGHT = 50;
    private static int METORITE_MAX_HEIGHT = 150;

    private static int BEAM_WIDTH = 20;
    private static int BEAM_HEIGHT = 8;

    private static int PLAYER_VELOCITY = 4;

    private static int ENEMY_ENERGY = 100;
    private static int ENEMY_POINTS = 300;
    private static int ENEMY_SHOOTING_DURATION = 2000; // in MS
    private static double ENEMY_VELOCITY = 1.5;

    private static double COLLECTABLE_VELOCITY = 1.5;
    private static int COLLECTABLE_POINTS = 500;

    public static MovingObject getMovingObject(ObjectType type)
    {
        // TODO via object type parametes set id of player and beams etc.
        switch (type){

            case METEORITE:
                int meteoriteNumber = ThreadLocalRandom.current().nextInt(1, 4);

                int imgWidth =  ThreadLocalRandom.current().nextInt(METEORITE_MIN_WIDTH, METORITE_MAX_WIDTH);
                int imgHeight = ThreadLocalRandom.current().nextInt(METEORITE_MIN_HEIGHT, METORITE_MAX_HEIGHT);

                int yStart = ThreadLocalRandom.current().nextInt(0, screenHeight);
                int yEnd = ThreadLocalRandom.current().nextInt(0, screenHeight);

                double m = (double) Math.abs(yEnd - yStart) / screenHeight;

                // moving from high 'y' to low 'y' -> gradient must be negative
                if(yEnd > yStart)
                    m = -m;

                double velocity = 1; // TODO calc velocity due size

                // TODO gamepoints
                new Meteorite("meteorite"+1, imgWidth, imgHeight, screenWidth + (imgWidth / 2), yStart, yEnd, m, velocity, 100, 10);
                break;
            case PLAYER_BEAM:

                return new Beam(ObjectType.PLAYER_BEAM, 0,"beam", 60, 20,  100, 150,   6, 1, 5);
                break;

            case ENEMY_BEAM:
                return new Beam(ObjectType.ENEMY_BEAM, 0,"beam.png", BEAM_WIDTH, BEAM_HEIGHT,  200, 20, 2, 0, 5);
                break;
            case COLLECTABLE:
                int collectableNumber = ThreadLocalRandom.current().nextInt(1, 3);

                yStart = ThreadLocalRandom.current().nextInt(0, screenHeight);
                yEnd = ThreadLocalRandom.current().nextInt(0, screenHeight);

                m = (double) Math.abs(yEnd - yStart) / screenHeight;

                // moving from high 'y' to low 'y' -> gradient must be negative
                if(yEnd > yStart)
                    m = -m;

                velocity = 3; // TODO calc velocity due size

                int imgSize = 30;

                new Collectable("artifact"+collectableNumber, imgSize, imgSize, screenWidth + (imgSize / 2), yStart, yEnd, m, COLLECTABLE_VELOCITY, 10, COLLECTABLE_POINTS);
                break;
            case PLAYER:
                return new Player(1,"player1", 80, 40, 20,20,100, PLAYER_VELOCITY);
                break;
            case ENEMY:
                new Enemy(0,"enemy", 80,80, screenWidth , 6, ENEMY_ENERGY, ENEMY_POINTS, ENEMY_VELOCITY, 1, ENEMY_SHOOTING_DURATION);
                break;
        }
        return null;
    }

}
*/