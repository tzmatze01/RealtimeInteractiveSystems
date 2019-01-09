package main.sprites;

import main.sprites.type.MovingObject;
import main.sprites.type.ObjectType;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Enemy extends MovingObject {


    private int enemyID;

    private int velocity;
    private int focusPlayer;

    private Point playerPos;

    private List<Beam> projectiles;
    private static int BEAM_WIDTH = 20;
    private static int BEAM_HEIGHT = 8;

    // TODO list of vector with weights for movement direction ?


    public Enemy(int enemyID, String imgFileName, int imageWidth, int imageHeight, int xPos, int yPos, int energy, int gamePoints, int velocity, int focusPlayer) {
        super(ObjectType.ENEMY, imgFileName, imageWidth, imageHeight, xPos, yPos, energy, gamePoints);

        this.enemyID = enemyID;

        this.velocity = velocity;
        this.focusPlayer = focusPlayer;

        this.playerPos = new Point(0, 0);

        this.projectiles = new ArrayList<>();
    }


    public void move() {

        // TODO set min dist to player

        double deltaX = Math.abs(playerPos.x - getX());
        double deltaY = Math.abs(playerPos.y - getY());

        double hyp = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

        //double angle = Math.abs(Math.tan(deltaX / deltaY));

        //double angle = Math.sin(deltaY / hyp);
        double angle = +Math.toDegrees(Math.asin(deltaY / hyp));

        //System.out.println("angle to before: "+Math.toDegrees(angle));
        // get random player pos and move in this direction
        this.xPos -= 1;

        //ddx = Math.abs(Math.cos(Math.toRadians(rotation)));
        //ddy = Math.abs(Math.sin(Math.toRadians(rotation)));

        //double xMovement = ddx * dMovement;
        //double yMovement = ddy * dMovement;

        // check which quadrant - pos player to enemy

        if(playerPos.y <= getY() && playerPos.x >= getX())
        {
            angle = Math.abs(angle - 90);
        }
        else if(playerPos.y >= getY() && playerPos.x >= getX())
        {
            angle += 90;
        }
        else if(playerPos.y > getY() && playerPos.x < getX())
        {
            angle = Math.abs(angle - 90) + 180;
        }
        else if(playerPos.y < getY() && playerPos.x < getX())
        {
            angle += 270;
        }

        //System.out.println("angle to player: "+angle);

        // shoot();
    }

    private void shoot()
    {
        double dx = -1;
        double dy = 0;


        projectiles.add(new Beam(ObjectType.ENEMY_BEAM, 0,"beam.png", BEAM_WIDTH, BEAM_HEIGHT,  getX(), getY(), dx, dy, 5));
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

    // rectangle for detection of meteorites
    public Rectangle scanNeighborhood()
    {
        return new Rectangle((int) xPos - getWidth(), (int) yPos - getHeight(), getWidth() + (getWidth() / 2), getHeight() + (getHeight() / 2));
    }
}
