package main.sprites;

import main.sprites.type.MovingObject;
import main.sprites.type.ObjectType;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Enemy extends MovingObject {


    private int enemyID;

    private double velocity;
    private int rotation;
    private int focusPlayer;

    private Point playerPos;

    private List<Beam> projectiles;
    private static int BEAM_WIDTH = 20;
    private static int BEAM_HEIGHT = 8;

    // TODO list of vector with weights for movement direction ?
    private long shootingDurationInMS;
    private long nextShootTime;

    private double ddx = 0;
    private double ddy = 0;

    private static int PLAYER_MIN_DIST = 150;


    public Enemy(int enemyID, String imgFileName, int imageWidth, int imageHeight, int xPos, int yPos, int energy, int gamePoints, double velocity, int focusPlayer, int shootingDurationInMS) {
        super(ObjectType.ENEMY, imgFileName, imageWidth, imageHeight, xPos, yPos, energy, gamePoints);

        this.enemyID = enemyID;

        this.velocity = velocity;
        this.focusPlayer = focusPlayer;

        this.playerPos = new Point(0, 0);

        this.rotation = 0;
        this.shootingDurationInMS = shootingDurationInMS;
        this.nextShootTime = System.currentTimeMillis() + shootingDurationInMS;

        this.projectiles = new ArrayList<>();
    }

    private int calcQuadrantAngleCorrection(int quadrant, double angle)
    {

        // correction because angle is only 0-90 degrees and in each quadrant degrees change direction 0°->90° 90°->0°
        if(quadrant == 1)
            return (int)angle;
        else if(quadrant == 2)
            return 90 + (int)(90-angle);
        else if(quadrant == 3)
            return 180 + (int)angle;
        else if(quadrant == 4)
            return 270 + (int)(90-angle);

        return 0;
    }

    public void move() {

        // TODO set min dist to player


        double deltaX = Math.abs(playerPos.x - getX());
        double deltaY = Math.abs(playerPos.y - getY());

        double hyp = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
        double angle = Math.toDegrees(Math.asin(deltaY / hyp));

        if(playerPos.y <= getY() && playerPos.x >= getX())
        {
            this.rotation = calcQuadrantAngleCorrection(2, angle);

            if(PLAYER_MIN_DIST <= hyp) {
                angle = Math.abs(angle - 90);

                ddx = Math.abs(Math.sin(Math.toRadians(angle)));
                ddy = Math.abs(Math.cos(Math.toRadians(angle)));

                xPos += ddx * velocity;
                yPos -= ddy * velocity;
            }
            //System.out.println("Q1: "+xPos+ " : "+yPos);
        }
        else if(playerPos.y >= getY() && playerPos.x >= getX())
        {
            this.rotation = calcQuadrantAngleCorrection(3, angle);
            angle += 90;

            if(PLAYER_MIN_DIST <= hyp) {
                ddx = Math.abs(Math.sin(Math.toRadians(angle)));
                ddy = Math.abs(Math.cos(Math.toRadians(angle)));

                xPos += ddx * velocity;
                yPos += ddy * velocity;
            }

            //System.out.println("Q2: "+xPos+ " : "+yPos);
        }
        else if(playerPos.y > getY() && playerPos.x < getX())
        {
            this.rotation = calcQuadrantAngleCorrection(4, angle);
            angle = Math.abs(angle - 90) + 180;

            if(PLAYER_MIN_DIST <= hyp) {
                ddx = Math.abs(Math.sin(Math.toRadians(angle)));
                ddy = Math.abs(Math.cos(Math.toRadians(angle)));

                xPos -= ddx * velocity;
                yPos += ddy * velocity;
            }

            //System.out.println("Q3: "+xPos+ " : "+yPos);
        }
        else if(playerPos.y < getY() && playerPos.x < getX())
        {
            this.rotation = calcQuadrantAngleCorrection(1, angle);
            angle += 270;

            if(PLAYER_MIN_DIST <= hyp) {
                ddx = Math.abs(Math.sin(Math.toRadians(angle)));
                ddy = Math.abs(Math.cos(Math.toRadians(angle)));

                xPos -= ddx * velocity;
                yPos -= ddy * velocity;
            }

            //System.out.println("Q4: "+xPos+ " : "+yPos);
        }

        if(System.currentTimeMillis() >= nextShootTime)
            shoot();

        for(Beam b : projectiles)
            b.move();
    }

    private void shoot()
    {
        // TODO shoot faster when player is closer
        this.nextShootTime = System.currentTimeMillis() + shootingDurationInMS;

        double dx = 0;
        double dy = 0;

        if(rotation >= 0 && rotation < 90)
        {
            dx -= ddx;
            dy -= ddy;
        }
        else if(rotation >= 90 && rotation < 180)
        {
            dx += ddx;
            dy += ddy;
        }
        else if(rotation >= 180 && rotation < 270)
        {
            dx += ddx;
            dy += ddy;
        }
        else if(rotation >= 270 && rotation < 360)
        {
            dx -= ddx;
            dy += ddy;
        }

        projectiles.add(new Beam(ObjectType.ENEMY_BEAM, getEnemyID(),"beam", BEAM_WIDTH, BEAM_HEIGHT,  getX(), getY(), dx, dy, 5));
    }

    public int getFocusPlayer() {
        return focusPlayer;
    }

    public void setFocusPlayerPos(int xPos, int yPos) {
        this.playerPos.x = xPos;
        this.playerPos.y = yPos;
    }

    // TODO myb points are enough ?
    public void setNeighborhoodMO(ArrayList<MovingObject> nmo)
    {

    }

    public int getEnemyID() {
        return enemyID;
    }

    public List<Beam> getProjectiles()
    {
        return this.projectiles;
    }

    public double getRotation() {
        return Math.toRadians(this.rotation);
    }

    // rectangle for detection of meteorites
    public Rectangle scanNeighborhood()
    {
        return new Rectangle((int) xPos - getWidth(), (int) yPos - getHeight(), getWidth() + (getWidth() / 2), getHeight() + (getHeight() / 2));
    }
}
