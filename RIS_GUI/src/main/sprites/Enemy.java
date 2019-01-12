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


    public void move() {

        // TODO set min dist to player

        double deltaX = Math.abs(playerPos.x - getX());
        double deltaY = Math.abs(playerPos.y - getY());

        double hyp = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

        //double angle = Math.abs(Math.tan(deltaX / deltaY));

        //double angle = Math.sin(deltaY / hyp);
        double angle = Math.toDegrees(Math.asin(deltaY / hyp));

        //System.out.println("angle to before: "+Math.toDegrees(angle));
        // get random player pos and move in this direction
        //this.xPos -= 1;

        //ddx = Math.abs(Math.cos(Math.toRadians(rotation)));
        //ddy = Math.abs(Math.sin(Math.toRadians(rotation)));

        //double xMovement = ddx * dMovement;
        //double yMovement = ddy * dMovement;

        // check which quadrant - pos player to enemy




        if(playerPos.y <= getY() && playerPos.x >= getX())
        {
            angle = Math.abs(angle - 90);

            ddx = Math.abs(Math.sin(Math.toRadians(angle)));
            ddy = Math.abs(Math.cos(Math.toRadians(angle)));

            xPos += ddx * velocity;
            yPos -= ddy * velocity;

            //System.out.println("Q1: "+xPos+ " : "+yPos);
        }
        else if(playerPos.y >= getY() && playerPos.x >= getX())
        {
            angle += 90;

            ddx = Math.abs(Math.sin(Math.toRadians(angle)));
            ddy = Math.abs(Math.cos(Math.toRadians(angle)));

            xPos += ddx * velocity;
            yPos += ddy * velocity;

            //System.out.println("Q2: "+xPos+ " : "+yPos);
        }
        else if(playerPos.y > getY() && playerPos.x < getX())
        {
            angle = Math.abs(angle - 90) + 180;

            ddx = Math.abs(Math.sin(Math.toRadians(angle)));
            ddy = Math.abs(Math.cos(Math.toRadians(angle)));

            xPos -= ddx * velocity;
            yPos += ddy * velocity;

            //System.out.println("Q3: "+xPos+ " : "+yPos);
        }
        else if(playerPos.y < getY() && playerPos.x < getX())
        {
            angle += 270;

            ddx = Math.abs(Math.sin(Math.toRadians(angle)));
            ddy = Math.abs(Math.cos(Math.toRadians(angle)));

            xPos -= ddx * velocity;
            yPos -= ddy * velocity;

            //System.out.println("Q4: "+xPos+ " : "+yPos);
        }


        //System.out.println("velocity: "+velocity);
        //System.out.println("ddx: "+ddx*velocity+ " ddy: "+ddy*velocity);
//        this.rotation = (int) angle - 180;

        this.rotation = (int)angle;

        //System.out.println("angle to player: "+angle);

        if(System.currentTimeMillis() >= nextShootTime)
            shoot();
    }

    private void shoot()
    {
        // TODO shoot faster when player is closer
        this.nextShootTime = System.currentTimeMillis() + shootingDurationInMS;


        double dx = 0;
        double dy = 0;

        int x = 0;
        int y = 0;

        if(rotation >= 0 && rotation < 90)
        {
            dx += ddx;
            dy += ddy;

            x = (int) xPos+(getWidth() / 2);
            y = (int) yPos;
        }
        else if(rotation >= 90 && rotation < 180)
        {
            dx -= ddx;
            dy += ddy;

            x = (int) xPos;
            y = (int) yPos+(getWidth() / 2);
        }
        else if(rotation >= 180 && rotation < 270)
        {
            dx -= ddx;
            dy -= ddy;

            x =  (int) xPos - (getWidth() / 2);
            y =  (int) yPos;
        }
        else if(rotation >= 270 && rotation < 360)
        {
            dx += ddx;
            dy -= ddy;

            x =  (int) xPos;
            y = (int) yPos - (getWidth() / 2);
        }


        System.out.println("enemy "+getEnemyID()+" shoots to "+dx+" : "+dy);


        projectiles.add(new Beam(ObjectType.ENEMY_BEAM, getEnemyID(),"beam", BEAM_WIDTH, BEAM_HEIGHT,  getX(), getY(), dx, dy, 5));


        //projectiles.add(new Beam(ObjectType.PLAYER_BEAM, playerID,"beam", BEAM_WIDTH, BEAM_HEIGHT,  x, y, dx, dy, 5));
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

    public int getRotation() {
        return rotation;
    }

    // rectangle for detection of meteorites
    public Rectangle scanNeighborhood()
    {
        return new Rectangle((int) xPos - getWidth(), (int) yPos - getHeight(), getWidth() + (getWidth() / 2), getHeight() + (getHeight() / 2));
    }
}
